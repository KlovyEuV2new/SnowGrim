package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Deque;

@CheckData(name = "AimW", experimental = true, maxBuffer = 4.0)
public class AimW extends Check implements RotationCheck {

    private final Deque<Double> yawSamples = new ArrayDeque<>();
    private final Deque<Double> pitchSamples = new ArrayDeque<>();

    private double lastYaw;
    private double lastPitch;
    private double buffer;

    private static final int SAMPLE_SIZE = 25;
    private static final double MIN_DELTA = 0.001;
    private static final double GCD_EPSILON = 1E-4;

    public AimW(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(RotationUpdate rotationUpdate) {

        if (!player.actionManager.hasAttackedSince(4000)
                || player.packetStateData.lastPacketWasTeleport
                || player.vehicleData.wasVehicleSwitch
                || player.isCinematicRotation()) {

            yawSamples.clear();
            pitchSamples.clear();
            buffer = 0;
            lastYaw = rotationUpdate.getTo().yaw();
            lastPitch = rotationUpdate.getTo().pitch();
            return;
        }

        double currentYaw = rotationUpdate.getTo().yaw();
        double currentPitch = rotationUpdate.getTo().pitch();

        double deltaYaw = Math.abs(wrapTo180(currentYaw - lastYaw));
        double deltaPitch = Math.abs(currentPitch - lastPitch);

        lastYaw = currentYaw;
        lastPitch = currentPitch;

        if (deltaYaw > MIN_DELTA) {
            yawSamples.add(deltaYaw);
            if (yawSamples.size() > SAMPLE_SIZE) {
                yawSamples.removeFirst();
            }
        }

        if (deltaPitch > MIN_DELTA) {
            pitchSamples.add(deltaPitch);
            if (pitchSamples.size() > SAMPLE_SIZE) {
                pitchSamples.removeFirst();
            }
        }

        boolean yawInvalid = yawSamples.size() >= 8 && isGcdSuspicious(yawSamples);
        boolean pitchInvalid = pitchSamples.size() >= 8 && isGcdSuspicious(pitchSamples);

        if (yawInvalid || pitchInvalid) {
            buffer = Math.min(getMaxBuffer(), buffer + 1.0);
            if (buffer > getMaxBuffer()) {
                flagAndAlert(String.format("gcd-rot y=%b p=%b buffer=%.2f", yawInvalid, pitchInvalid, buffer));
            }
        } else {
            buffer = Math.max(0, buffer - 0.25);
        }
    }

    private boolean isGcdSuspicious(Deque<Double> samples) {
        Double[] arr = samples.toArray(new Double[0]);

        double gcd = arr[0];
        for (int i = 1; i < arr.length; i++) {
            gcd = gcd(gcd, arr[i]);
            if (gcd < GCD_EPSILON) {
                return false;
            }
        }

        if (gcd <= 0) return false;

        int consistent = 0;

        for (double v : arr) {
            double mod = v % gcd;
            if (mod < GCD_EPSILON || Math.abs(mod - gcd) < GCD_EPSILON) {
                consistent++;
            }
        }

        return consistent >= arr.length * 0.9;
    }

    private double gcd(double a, double b) {
        while (b > GCD_EPSILON) {
            double temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }

    private double wrapTo180(double value) {
        value %= 360.0;
        if (value >= 180.0) value -= 360.0;
        if (value < -180.0) value += 360.0;
        return value;
    }
}
