package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimV", experimental = true, maxBuffer = 3)
public class AimV extends Check implements RotationCheck {
    private double buffer;

    public AimV(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(final RotationUpdate rotationUpdate) {

        float fromYaw = rotationUpdate.getFrom().yaw();
        float toYaw = rotationUpdate.getTo().yaw();
        float fromPitch = rotationUpdate.getFrom().pitch();
        float toPitch = rotationUpdate.getTo().pitch();

        if (!Float.isFinite(toYaw) || !Float.isFinite(toPitch)) {
            if (++buffer > getMaxBuffer()) {
                flagAndAlert("non-finite rotation");
            }
            return;
        }

        if (toPitch > 90.0F || toPitch < -90.0F) {
            if (++buffer > getMaxBuffer()) {
                flagAndAlert(String.format("invalid pitch %.3f", toPitch));
            }
            return;
        }

        float deltaYaw = wrapTo180(toYaw - fromYaw);
        float deltaPitch = toPitch - fromPitch;

        if (Math.abs(deltaYaw) > 180.0F || Math.abs(deltaPitch) > 90.0F) {
            if (++buffer > getMaxBuffer()) {
                flagAndAlert(String.format("impossible delta y=%.3f p=%.3f", deltaYaw, deltaPitch));
            }
        } else {
            buffer = Math.max(0, buffer - 0.25);
        }
    }

    private float wrapTo180(float value) {
        value %= 360.0F;
        if (value >= 180.0F) value -= 360.0F;
        if (value < -180.0F) value += 360.0F;
        return value;
    }
}
