package ac.grim.grimac.checks.impl.movement;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "OffsetElytra")
public class ElytraOffset extends Check implements PostPredictionCheck {
    public ElytraOffset(@NotNull GrimPlayer player) {
        super(player);
    }
}
