package ac.grim.grimac.checks.impl.aim.states;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimRepeatA", setback = -1, maxBuffer = 4, description = "Repeated identical accel")
public class AimRepeatB extends Check implements RotationCheck {

    private float lastDx, lastDy;
    private float lastAx, lastAy;
    private double buffer;

    public AimRepeatB(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(RotationUpdate rotationUpdate) {
        if (!player.actionManager.hasAttackedSince(500) || player.isCinematicRotation()) {
            buffer = 0;
            lastDx = 0;
            lastDy = 0;
            return;
        }

        float dx = rotationUpdate.getDeltaXRotABS();
        float dy = rotationUpdate.getDeltaYRotABS();

        float ax = Math.abs(lastDx - dx), ay = Math.abs(lastDy - dy);

        if (player.isCinematicRotation()) return;
        if ((dx >= 1.0f && lastDx >= 1.0f && ax == lastAx) || (dy >= 1.0f && lastDy >= 1.0f && ay == lastAy)) {
            if (++buffer > getMaxBuffer()) {
                flagAndAlert("ay=" + ay + " ax=" + ax + " buffer=" + buffer);
            }

        } else {
            buffer = Math.max(0, buffer - 0.25);
        }

        lastDx = dx;
        lastDy = dy;
        lastAx = ax;
        lastAy = ay;
    }
}
