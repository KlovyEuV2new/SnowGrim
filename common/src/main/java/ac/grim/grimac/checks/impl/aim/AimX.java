package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;


@CheckData(name = "AimX", experimental = true, maxBuffer = 4.0)
public class AimX extends Check implements RotationCheck {

    public AimX(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(RotationUpdate rotationUpdate) {
    }
}
