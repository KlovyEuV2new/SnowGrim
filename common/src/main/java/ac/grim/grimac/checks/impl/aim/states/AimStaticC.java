package ac.grim.grimac.checks.impl.aim.states;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;


// $author klimushkayt & KlovyEuV2
@CheckData(name = "AimStaticC",experimental = true,maxBuffer = 3)
public class AimStaticC extends Check implements RotationCheck {
    private double buffer;
    private float lastDx, lastDy;

    public AimStaticC(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.actionManager.hasAttackedSince(500) && player.lastAttackedRealTick > 15) {
            float dx = rotationUpdate.getDeltaXRotABS();
            float dy = rotationUpdate.getDeltaYRotABS();

            float ax = Math.abs(lastDx - dx), ay = Math.abs(lastDy - dy);
            float axy = Math.abs(ax - ay);

            if (player.isCinematicRotation()) return;
            if (ax > 0f && dx > 0f && (axy == 0f || axy % 0.5f == 0f)) {


                final String info = String.format("(3) ax=%.5f, ay=%.5f, buffer=%.1f", ax, ay, buffer);
                if (++buffer > getMaxBuffer()) {
                    flagAndAlert(info);
                }
            } else if (buffer > 0){
                buffer = buffer - 0.25;
            }

            lastDx = dx;
            lastDy = dy;
        }
    }
}
