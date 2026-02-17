package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;

@CheckData(name = "AimU", description = "Checks for snappy rotations.", maxBuffer = 4)
public class AimU extends Check implements RotationCheck {

    private double buffer;

    public AimU(GrimPlayer playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.packetStateData.lastPacketWasTeleport ||
                System.currentTimeMillis() - player.joinTime < 5000 ||
                player.inVehicle() || player.lastAttackedRealTick > 15) {
            return;
        }

        if (player.hitticks < 1) {
            double moving = player.deltaXZ();
            float dy = Math.abs(player.pitch - player.lastPitch);
            float dx = Math.abs(player.yaw - player.lastYaw);
            if (dx > 40 && dx < 180 && moving > 0.08 && ++buffer > getMaxBuffer()) {
                final String info = String.format(
                        "dx=%.5f, dy=%.5f, buffer=%.1f", dx, dy, buffer
                );
                flagAndAlert(info);
            }  else if (buffer > 0) {
                buffer = buffer - 0.2;
            }
        }
    }
}
