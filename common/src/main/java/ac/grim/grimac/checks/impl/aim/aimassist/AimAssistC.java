package ac.grim.grimac.checks.impl.aim.aimassist;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import ac.grim.grimac.utils.data.PlayerRotationData;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimAssistC",experimental = true)
public class AimAssistC extends Check implements RotationCheck {
    private double buffer;

    public AimAssistC(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.packetStateData.lastPacketWasTeleport ||
                System.currentTimeMillis() - player.joinTime < 5000 ||
                player.inVehicle()) {
            return;
        }

        if (player.hitticks < 3) {
            PlayerRotationData rotation = player.rotatationUpdateData;
            if (rotation.getDdy() > 0.0 && rotation.getDdy() < 0.99 && rotation.getDdx() > 5.0) {
                double moving = player.deltaXZ();
                if (++buffer > 10.0 && moving > 0.08) {

                    final String info = String.format(
                            "buffer=%.1f, ddx=%.5f, ddy=%.5f", buffer, rotation.getDdx(), rotation.getDdy()
                    );
                    flagAndAlert(info);
                }

            } else {
                buffer = Math.max(Math.min(buffer - 0.2, 10.0), 0.0);
            }
        }
    }
}
