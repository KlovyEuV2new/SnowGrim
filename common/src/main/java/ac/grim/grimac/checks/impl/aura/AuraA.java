package ac.grim.grimac.checks.impl.aura;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.data.packetentity.PacketEntity;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity.InteractAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;

@CheckData(name = "AuraA", description = "Post UseEntity packets.")
public class AuraA extends Check implements PacketCheck {
    private long lastFlying = 0L;
    private boolean sent = false;
    public AuraA(GrimPlayer player) {
        super(player);
    }

    public void onPacketReceive(PacketReceiveEvent event) {
        if (WrapperPlayClientPlayerFlying.isFlying(event.getPacketType())) {
            long delay = System.currentTimeMillis() - this.lastFlying;
            if (this.sent) {
                boolean exempt = this.player.gamemode == GameMode.SPECTATOR || player.clickData.getCps() > 2;
                if (delay > 40L && delay < 100L && !exempt) {
                    this.flagAndAlertWithSetback("delay=" + delay);
                }

                this.sent = false;
            }

            this.lastFlying = System.currentTimeMillis();
        }

        if (event.getPacketType() == Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity action = new WrapperPlayClientInteractEntity(event);
            if (action.getAction() != InteractAction.ATTACK) {
                return;
            }

            PacketEntity target = this.player.compensatedEntities.entityMap.get(action.getEntityId());
            if (target == null || target.type != EntityTypes.PLAYER) {
                return;
            }

            long delay2 = System.currentTimeMillis() - this.lastFlying;
            if (delay2 < 10L) {
                this.sent = true;
            }
        }
    }
}
