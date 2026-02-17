package ac.grim.grimac.checks.impl.aim.snap;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import ac.grim.grimac.utils.data.PlayerRotationData;
import org.jetbrains.annotations.NotNull;

/**
 * AUTHOR: Thelema
 */
@CheckData(name = "AimSnapLook", setback = -1, maxBuffer = 7)
public class AimSnapLook extends Check implements RotationCheck {
    private double buffer;

    public AimSnapLook(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.packetStateData.lastPacketWasTeleport ||
                System.currentTimeMillis() - player.joinTime < 5000 ||
                player.inVehicle() ||
                player.clickData.getCps() > 2) {
            return;
        }

        if (player.actionManager.hasAttackedSince(100L)) {
            PlayerRotationData rotationData = player.rotatationUpdateData;
            float dx = rotationData.getDeltaYaw();
            float dy = rotationData.getDeltaPitch();
            float sx = rotationData.getSmoothnessYaw();
            float sy = rotationData.getSmoothnessPitch();

            if (dx > 5.0F && sx < -5.0F && player.deltaXZ() > 0.05) {

                if (++buffer > getMaxBuffer()) {
                    final String info = String.format("dx=%.5f, dy=%.5f, sx=%.5f, sy=%.5f, buffer=%.2f", dx, dy, sx, sy, buffer);
                    flagAndAlert(info);
                }
            } else if (buffer > 0) {
                buffer = buffer - 0.25;
            }
        }
    }
}
