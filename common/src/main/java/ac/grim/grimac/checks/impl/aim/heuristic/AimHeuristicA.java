package ac.grim.grimac.checks.impl.aim.heuristic;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimHeuristicA", experimental = true, maxBuffer = 4)
public class AimHeuristicA extends Check implements RotationCheck {

    private double buffer;
    private float lastDeltaYaw;
    private float lastDeltaPitch;
    private double lastAccelerationYaw;
    private double lastAccelerationPitch;

    public AimHeuristicA(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (!player.actionManager.hasAttackedSince(500)) {
            return;
        }

        float fromYaw = rotationUpdate.getFrom().yaw();
        float toYaw = rotationUpdate.getTo().yaw();
        float fromPitch = rotationUpdate.getFrom().pitch();
        float toPitch = rotationUpdate.getTo().pitch();

        float deltaYaw = Math.abs(toYaw - fromYaw);
        float deltaPitch = Math.abs(toPitch - fromPitch);

        if (deltaYaw == 0.0f && deltaPitch == 0.0f) {
            return;
        }

        double accelerationYaw = Math.abs(deltaYaw - lastDeltaYaw);
        double accelerationPitch = Math.abs(deltaPitch - lastDeltaPitch);

        boolean invalidPattern =
                (accelerationYaw == lastAccelerationYaw && accelerationYaw > 0.001) ||
                        (accelerationPitch == lastAccelerationPitch && accelerationPitch > 0.001);

        boolean lowVariance =
                Math.abs(deltaYaw - lastDeltaYaw) < 1E-4 &&
                        Math.abs(deltaPitch - lastDeltaPitch) < 1E-4 &&
                        deltaYaw > 0.01;

        if (invalidPattern || lowVariance) {
            if (++buffer > getMaxBuffer()) {
                flagAndAlert(String.format("dy=%.5f dp=%.5f ay=%.5f ap=%.5f b=%.1f",
                        deltaYaw, deltaPitch, accelerationYaw, accelerationPitch, buffer));
            }
        } else {
            buffer = Math.max(0, buffer - 0.5);
        }

        lastDeltaYaw = deltaYaw;
        lastDeltaPitch = deltaPitch;
        lastAccelerationYaw = accelerationYaw;
        lastAccelerationPitch = accelerationPitch;
    }
}
