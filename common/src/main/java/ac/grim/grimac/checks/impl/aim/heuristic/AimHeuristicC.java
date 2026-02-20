package ac.grim.grimac.checks.impl.aim.heuristic;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

@CheckData(name = "AimHeuristicC", experimental = true, maxBuffer = 4)
public class AimHeuristicC extends Check implements RotationCheck {

    private double buffer;
    private float lastDeltaYaw;
    private float lastDeltaPitch;
    private double lastAccelerationYaw;
    private double lastAccelerationPitch;

    private static final DecimalFormat SCI_FORMAT = new DecimalFormat("0.#####E0");

    public AimHeuristicC(@NotNull GrimPlayer player) {
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

        boolean invalidPattern = (lastDeltaPitch > 0.01 && lastDeltaYaw > 0.01
                && (lastDeltaPitch == deltaYaw || lastDeltaYaw == deltaPitch))
                || (lastAccelerationPitch > 0.01 && lastAccelerationYaw > 0.01
                && (lastAccelerationPitch == accelerationYaw || lastAccelerationYaw == accelerationPitch)
        );

        if (player.isCinematicRotation()) return;
        if (invalidPattern) {
            if (++buffer > getMaxBuffer()) {
                flagAndAlert(String.format(
                        "dy=%s dp=%s ay=%s ap=%s b=%.1f",
                        SCI_FORMAT.format(deltaYaw),
                        SCI_FORMAT.format(deltaPitch),
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
