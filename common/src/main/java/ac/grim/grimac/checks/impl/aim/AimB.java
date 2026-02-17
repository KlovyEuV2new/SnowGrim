package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimB",setback = -1,description = "invalid sensitivity")
public class AimB extends Check implements RotationCheck {
    public AimB(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.actionManager.hasAttackedSince(150L)) {
            final boolean tooLowSensitivity = player.rotatationUpdateData.hasTooLowSensitivity();
            final double finalSensitivity = player.rotatationUpdateData.getFinalSensitivity();
            final double deltaPitch = player.rotatationUpdateData.getDeltaPitch();
            String pitch = String.format("%.5f", deltaPitch);
      //      String sens = String.format("%.5f", deltaPitch);
            String info = "send: " + finalSensitivity + " pitch: " + pitch;
            if (player.rotatationUpdateData.UsingCinematicCamera()) return; // invalid
            if (!player.rotatationUpdateData.hasValidSensitivityNormalaized() && !tooLowSensitivity) {
                flagAndAlert(info);
            }
        }
    }
}
