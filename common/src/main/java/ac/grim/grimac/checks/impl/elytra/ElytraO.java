package ac.grim.grimac.checks.impl.elytra;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PredictionComplete;

// Чек на ElytraFly (grim bypass) ну крч от такого дерьма.
// Ложных мало, но чуть есть. (Основные которые прям мешают пофиксил)
@CheckData(name = "ElytraO")
public class ElytraO extends Check implements PostPredictionCheck {
    private int tick;

    public ElytraO(GrimPlayer player) {
        super(player);
    }

    public void onPredictionComplete(final PredictionComplete predictionComplete) {
        if (!player.isGliding) {
            tick = 0;
            return;
        }

        if (player.deltaXZ() == 0 && player.deltaY() != 0
                && Math.abs(player.pitch) != 90 && Math.abs(player.lastPitch) != 90) {
            // Формула от ложных (убирает 90% ложных при JumpStart)
            double py = player.lastDeltaY - (0.000017 * (player.pitch * player.pitch) + 0.020852);
            double offset = Math.abs(player.deltaY() - py);
            if (tick > 0 && (tick != 1 || offset > 0.0001))
                flagAndAlertWithSetback(String.format("pitch=%.3f, lpitch=%.3f, dyaw=%.5f, ldyaw=%.5f, offset=%.4f, tick=%d", player.pitch, player.lastPitch, player.deltaY(), player.lastDeltaY, offset, tick));
        }
        tick++;
    }
}
