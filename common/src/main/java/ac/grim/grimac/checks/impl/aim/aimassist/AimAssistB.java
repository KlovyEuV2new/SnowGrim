package ac.grim.grimac.checks.impl.aim.aimassist;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimAssistB",experimental = true,maxBuffer = 5)
public class AimAssistB extends Check implements RotationCheck {

    private double buffer;
    private float yawChange;

    public AimAssistB(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.packetStateData.lastPacketWasTeleport ||
                System.currentTimeMillis() - player.joinTime < 5000 ||
                player.inVehicle()) {
            return;
        }

        if (player.hitticks < 2) {
            float yawRate = Math.abs(player.yaw - player.lastYaw);
            if (player.getClientVersion().getProtocolVersion() >= 755 && player.packetStateData.lastPacketWasOnePointSeventeenDuplicate || yawRate < 1) return;
            if (yawRate == yawChange) {
                if (++buffer > getMaxBuffer()) {
                    flagAndAlert("yawRate=" + yawRate + ", yawChange=" + yawChange);
                }
            }
            else if (buffer > 0) {
                buffer = buffer - 0.1;
            }
            yawChange = yawRate;
        }
    }
}
