package ac.grim.grimac.checks.impl.aim;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.RotationCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.anticheat.update.RotationUpdate;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "AimA",setback = -1,description = "YRot delta =0")
public class AimA extends Check implements RotationCheck {
    private int maxbuffer;
    private int buffer;

    public AimA(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.actionManager.hasAttackedSince(120L) && player.lastAttackedRealTick < 32 && !player.packetStateData.lastPacketWasTeleport && !(player.compensatedEntities.self.getRiding() != null)) {
            double dy = Math.abs(rotationUpdate.getFrom().pitch() - rotationUpdate.getTo().pitch());
            double dx = Math.abs(rotationUpdate.getFrom().yaw() - rotationUpdate.getTo().yaw());
            if (dy == 0.0 || dy < 0.01  ) {

                if (++buffer > maxbuffer && dx > 5 &&! (Math.abs( rotationUpdate.getTo().pitch()) == 90f || rotationUpdate.getTo().pitch() == -90f)) {
                    String deltay = String.format("%.5f", dy);
                    String deltax = String.format("%.5f", dx);
                    flagAndAlert("dy: " + deltay + " dx: " + deltax+ " b: " + buffer);
                }
            } else if (buffer > 0) {
                buffer--;
            }
        }
    }

    @Override
    public void onReload(ConfigManager config) {
        this.maxbuffer = config.getIntElse("AimA.buffer",30);
    }
}
