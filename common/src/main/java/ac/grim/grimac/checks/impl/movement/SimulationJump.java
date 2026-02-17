package ac.grim.grimac.checks.impl.movement;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PredictionComplete;
import org.jetbrains.annotations.NotNull;


@CheckData(
        name = "SimulationJump",
        setback = 0.0D,
        decay = 0.02D,
        description = "Jump Simulation"
)
public class SimulationJump extends Check implements PostPredictionCheck {
    private boolean wasFlagged = false;
    private boolean tpb;
    private double offset;
    private boolean isJumping;
    private String reason = "";

    public SimulationJump(@NotNull GrimPlayer player) {
        super(player);
    }

    public void onPredictionComplete(PredictionComplete predictionComplete) {
//        double offset = PredictionCheckJump.check(player).offset();
//
//        if (offset != Double.MIN_VALUE) {
//            alert(String.valueOf(player.deltaY()+ " " +offset));
//        }
    }

    private void giveOffsetLenienceNextTick(double offset) {
        double minimizedOffset = Math.min(offset, 1.0D);
        this.player.uncertaintyHandler.lastHorizontalOffset = minimizedOffset;
        this.player.uncertaintyHandler.lastVerticalOffset = minimizedOffset;
    }

    private void removeOffsetLenience() {
        this.player.uncertaintyHandler.lastHorizontalOffset = 0.0D;
        this.player.uncertaintyHandler.lastVerticalOffset = 0.0D;
    }

    public void onReload(ConfigManager config) {
        this.tpb = config.getBooleanElse(this.getConfigName() + ".setback", true);
        this.offset = config.getDoubleElse(this.getConfigName() + ".offset", 0.001D);
    }
}
