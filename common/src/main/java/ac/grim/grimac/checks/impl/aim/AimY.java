package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimY", experimental = true, maxBuffer = 4.0)
public class AimY extends Check implements RotationCheck {

    private double lastYaw;
    private double lastPitch;
    private double buffer;
    private double lastGcdYaw;
    private double lastGcdPitch;

    private static final double MIN_DELTA = 0.001;
    private static final double GCD_EPSILON = 1E-5;

    public AimY(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(RotationUpdate rotationUpdate) {
        if (!player.actionManager.hasAttackedSince(4000)
                || player.packetStateData.lastPacketWasTeleport
                || player.vehicleData.wasVehicleSwitch
                || player.isCinematicRotation()) {

            buffer = 0;
            lastYaw = rotationUpdate.getTo().yaw();
            lastPitch = rotationUpdate.getTo().pitch();
            lastGcdYaw = 0;
            lastGcdPitch = 0;
            return;
        }

        double currentYaw = rotationUpdate.getTo().yaw();
        double currentPitch = rotationUpdate.getTo().pitch();

        double deltaYaw = Math.abs(wrapTo180(currentYaw - lastYaw));
        double deltaPitch = Math.abs(currentPitch - lastPitch);

        lastYaw = currentYaw;
        lastPitch = currentPitch;

        if (deltaYaw > MIN_DELTA) {
            double gcdYaw = gcd(deltaYaw, lastGcdYaw);
            if (lastGcdYaw > 0 && Math.abs(gcdYaw - lastGcdYaw) > GCD_EPSILON) {
                if (++buffer > getMaxBuffer()) {
                    flagAndAlert("GCD bypass flaw detected.");
                }
            } else {
                buffer = Math.max(0, buffer - 0.25);
            }
            lastGcdYaw = gcdYaw;
        }

        if (deltaPitch > MIN_DELTA) {
            double gcdPitch = gcd(deltaPitch, lastGcdPitch);
            if (lastGcdPitch > 0 && Math.abs(gcdPitch - lastGcdPitch) > GCD_EPSILON) {
                if (++buffer > getMaxBuffer()) {
                    flagAndAlert("GCD bypass");
                }
            } else {
                buffer = Math.max(0, buffer - 0.25);
            }
            lastGcdPitch = gcdPitch;
        }
    }

    private double gcd(double a, double b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    private double wrapTo180(double value) {
        value %= 360.0;
        if (value >= 180.0) value -= 360.0;
        if (value < -180.0) value += 360.0;
        return value;
    }
}
