package ac.grim.grimac.checks.impl.aim.aimassist;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimAssistA",experimental = true,maxBuffer = 5)
public class AimAssistA extends Check implements RotationCheck {
    private int tickSwitchedDirection;
    private float lastDeltaPitch;
    private double buffer;

    public AimAssistA(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.packetStateData.lastPacketWasTeleport ||
                System.currentTimeMillis() - player.joinTime < 5000 ||
                player.inVehicle() && player.lastAttackedRealTick > 15) { //по тестам проверка стабильна при обычном пвп но можно было фолсить в регионе(если специально так делать)
            return;
        }

        if (player.attacking(3)) {
            final float pitch = player.rotatationUpdateData.getPitch();
            final float lastPitch = player.rotatationUpdateData.getLastPitch();
            final float deltaPitch = pitch - lastPitch;
            if ((deltaPitch < 0.0f && lastDeltaPitch > 0.0f) || (deltaPitch > 0.0f && lastDeltaPitch < 0.0f)) {
                tickSwitchedDirection = 0;
            } else {
                ++tickSwitchedDirection;
            }
            final boolean invalid = tickSwitchedDirection == 0 && Math.abs(deltaPitch) > 5.0f;
            if (invalid) {
                if (++buffer > getMaxBuffer()) {
                    flagAndAlert("DY=" + deltaPitch);
                }
            }
            lastDeltaPitch = deltaPitch;
        } else if (buffer > 0) {
            buffer = buffer - 0.25;
        }
    }
}
