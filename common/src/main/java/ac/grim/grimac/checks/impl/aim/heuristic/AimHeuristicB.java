package ac.grim.grimac.checks.impl.aim.heuristic;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

@CheckData(name = "AimHeuristicB", experimental = true, maxBuffer = 4)
public class AimHeuristicB extends Check implements RotationCheck {

    private double buffer;
    private float lastDeltaYaw;
    private float lastDeltaPitch;
    private double lastAccelerationYaw;
    private double lastAccelerationPitch;

    private static final DecimalFormat SCI_FORMAT = new DecimalFormat("0.#####E0");

    public AimHeuristicB(@NotNull GrimPlayer player) {
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

        boolean lowVariance =
                (Math.abs(deltaYaw - lastDeltaYaw) < 1E-7 && deltaYaw > 0.01) &&
                        (Math.abs(deltaPitch - lastDeltaPitch) < 1E-7 && deltaPitch > 0.01);

        if (player.isCinematicRotation()) return;
        if (lowVariance) {
            if (++buffer > getMaxBuffer()) {
                flagAndAlert(String.format(
                        "dy=%s dp=%s ay=%s ap=%s b=%.1f",
                        SCI_FORMAT.format(Math.abs(deltaYaw - lastDeltaYaw)),
                        SCI_FORMAT.format(Math.abs(deltaPitch - lastDeltaPitch)),
                        SCI_FORMAT.format(accelerationYaw),
                        SCI_FORMAT.format(accelerationPitch),
                        buffer
                ));
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
