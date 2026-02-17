package ac.grim.grimac.checks.impl.elytra;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PredictionComplete;

@CheckData(name = "ElytraN", description = "Invalid acceleration on fireworks", experimental = true)
public class ElytraN extends Check implements PostPredictionCheck {

    private int lastTime;
    private int tickExempt;

    public ElytraN(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPredictionComplete(final PredictionComplete predictionComplete) {

        if (System.currentTimeMillis() - player.joinTime < 1000) {
            return;
        }

        tickExempt++;

        if (player.predictedVelocity.isJump() ||
                player.predictedVelocity.isExplosion() ||
                player.isRiptidePose ||
                player.onGround) {
            tickExempt = 0;
        }

        lastTime++;

        if (player.fireworks.getMaxFireworksAppliedPossible() > 0) {
            lastTime = 0;
        }

        double deltaXZ = player.deltaXZ();
        double actualMovementY = player.actualMovement.getY();
        boolean exempt = player.pitch >= 30.0;
        if (player.isGliding && lastTime == 0 && tickExempt > 3 && player.riptideSpinAttackTicks < -50 && !exempt) {
//            player.sendMessage("xz=" + deltaXZ + " y=" + actualMovementY);
            if (deltaXZ > 1.73 && deltaXZ < 1.8 || actualMovementY > 1.62 && actualMovementY < 1.7) {
                flagAndAlert(String.format("xz: %.3f, y: %.3f", deltaXZ, actualMovementY));
                if (shouldSetback()) player.getSetbackTeleportUtil().executeViolationSetbackDown();
            }
        }
    }
}
