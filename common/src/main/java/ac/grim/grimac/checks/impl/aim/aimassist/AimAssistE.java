package ac.grim.grimac.checks.impl.aim.aimassist;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimAssistE",experimental = true,maxBuffer = 4)
public class AimAssistE extends Check implements RotationCheck {
    private double buffer;

    public AimAssistE(@NotNull GrimPlayer player) {
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
            final float deltaYaw = player.rotatationUpdateData.getDeltaYaw();
            final boolean invalid = deltaYaw > 0.0f && (deltaYaw % 0.25 == 0.0 || deltaYaw % 0.1 == 0.0);
            if (invalid) {
                if (++buffer > getMaxBuffer()) {
                    flagAndAlert("(2) deltaYaw=" + deltaYaw);
                }
            }
            else if (buffer > 0) {
                buffer = buffer - 0.5;
            }
        }
    }
}
