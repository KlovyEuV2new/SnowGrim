package ac.grim.grimac.checks.impl.aim.aimassist;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimAssistT", experimental = true, setback = -1, maxBuffer = 11)
public class AimAssistT extends Check implements RotationCheck {
    private double buffer;
    private double maxbuffer;
    private double lastDeltaYaw;

    public AimAssistT(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (!shouldCheck()) return;

        double deltaYaw = rotationUpdate.getDeltaYRot();
        double dxa = Math.abs(deltaYaw - lastDeltaYaw);
        lastDeltaYaw = deltaYaw;

        if (dxa <= 0.1 && Math.abs(deltaYaw) >= 0.5 && Math.abs(deltaYaw) <= 100.0) {
            if (++buffer > getMaxBuffer()) {
                flagAndAlert(String.format("dxa=%.1f, dx=%.1f", dxa, deltaYaw));
                buffer = Math.max(0, buffer - 2);
            }
        } else {
            buffer = Math.max(0, buffer - 0.5);
        }
    }

    private boolean shouldCheck() {
        return player.actionManager.hasAttackedSince(1000L) &&
                !player.packetStateData.lastPacketWasTeleport &&
                !player.inVehicle() &&
                Math.abs(player.rotatationUpdateData.getDeltaYaw()) > 0.5;
    }

//    @Override
//    public void onReload(ConfigManager config) {
//       // this.maxbuffer = config.getDoubleElse("AimE.buffer", 8.0);
//    }
}
