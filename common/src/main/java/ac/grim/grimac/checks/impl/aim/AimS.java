package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

/**
 AUTHOR: @D3zzl1
 */
@CheckData(name = "AimS", setback = -1, maxBuffer = 3,description = "zero X,Y acel factor")
public class AimS extends Check implements RotationCheck {
    private double lastdy;
    private double lastdx;
    private boolean hasLast;
    private double buffer;

    public AimS(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (!player.actionManager.hasAttackedSince(500)) {
            hasLast = false;
            buffer = 0;
            return;
        }

        double dy = Math.abs(rotationUpdate.getFrom().pitch() - rotationUpdate.getTo().pitch());
        double dx = Math.abs(rotationUpdate.getFrom().yaw() - rotationUpdate.getTo().yaw());

        if (hasLast) {
            if (Math.abs(dy) > 0.1 && Math.abs(dx) > 0.1 &&
                    Math.abs(lastdy) > 0.1 && Math.abs(lastdx) > 0.1) {

                double changeYaw = dy - lastdy;
                double changePitch = dx - lastdx;
                double diff = Math.abs(changeYaw - changePitch);

                if (diff < 0.01) {
                    buffer += 1;
                } else {
                    buffer = Math.max(0, buffer - 0.5);
                }

                if (buffer > getMaxBuffer()) {
                    String difference = String.format("%.5f", diff);
                    flagAndAlert("diff=" + difference + " buffer=" + buffer);
                }
            }
        }

        lastdy = dy;
        lastdx = dx;
        hasLast = true;
    }
}
