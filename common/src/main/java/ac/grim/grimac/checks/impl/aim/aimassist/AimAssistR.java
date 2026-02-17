package ac.grim.grimac.checks.impl.aim.aimassist;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimAssistR",experimental = true,maxBuffer = 3)
public class AimAssistR extends Check implements RotationCheck {
    private double buffer;

    public AimAssistR(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.actionManager.hasAttackedSince(500) && player.lastAttackedRealTick > 15) {
            float dx = rotationUpdate.getDeltaXRotABS();
            float dy = rotationUpdate.getDeltaYRotABS();

            if (dy < -0.0f && dy % -0.5f == -0.0f || dy > 0.0f && dy % 0.5f == 0.0f) {

                final String info = String.format("(3) dx=%.5f, dy=%.5f, buffer=%.1f", dx, dy, buffer);
                if (++buffer > getMaxBuffer()) {
                    flagAndAlert(info);
                }
            } else if (buffer > 0){
               buffer = buffer - 0.25;
            }
        }
    }
}
