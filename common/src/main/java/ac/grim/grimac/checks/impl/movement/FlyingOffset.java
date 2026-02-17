package ac.grim.grimac.checks.impl.movement;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PostPredictionCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.PredictionComplete;
import ac.grim.grimac.utils.math.Vector3dm;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "FlyingOffset", experimental = true)
public class FlyingOffset extends Check implements PostPredictionCheck {
    private Vector3dm lastPos;

    public FlyingOffset(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPredictionComplete(final PredictionComplete predictionComplete) {
        if (player.isFlying && !(player.flySpeed > 0.1)) {

            if (lastPos == null || player.packetStateData.lastPacketWasTeleport || player.getSetbackTeleportUtil().isSendingSetback) {
                lastPos = new Vector3dm(player.x, player.y, player.z);
                return;
            }

            Vector3dm currentPos = new Vector3dm(player.x, player.y, player.z);

            double deltaY = currentPos.getY() - lastPos.getY();
            if (deltaY > 0.9) {
                flagAndAlert("y-motion=" + deltaY);
                player.getSetbackTeleportUtil().executeViolationSetback();
            } else if (deltaY < -0.9) {
                flagAndAlert("-y-motion=" + deltaY);
                player.getSetbackTeleportUtil().executeViolationSetback();
            }

            double deltaX = currentPos.getX() - lastPos.getX();
            double deltaZ = currentPos.getZ() - lastPos.getZ();
            double deltaXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

            if (deltaXZ > 2) {
                flagAndAlert("xz-motion=" + deltaXZ);
                player.getSetbackTeleportUtil().executeViolationSetback();
            }

            lastPos = currentPos;
        }
    }
}
