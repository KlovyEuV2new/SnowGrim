package ac.grim.grimac.checks.impl.sprint;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;



import static com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client.ENTITY_ACTION;
import static com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client.INTERACT_ENTITY;
import static com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity.InteractAction.ATTACK;

@CheckData(name = "SprintI", description = "Attack with last packet is stopped sprinting", maxBuffer = 4)
public class SprintI extends Check implements PacketCheck {
    private boolean lastPacketIsStop;
    private double buffer;

    public SprintI(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onReload(ConfigManager config) {
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (player.disableGrim) return;

        if (event.getPacketType().equals(INTERACT_ENTITY)) {
            WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
            if (packet.getAction().equals(ATTACK)) {
                if (lastPacketIsStop) {

                    if (++buffer > getMaxBuffer()) {
                        flagAndAlert();
                    } else if (buffer > 0) {
                        buffer -= 0.25;
                    }
                }
            }
        }

        if (event.getPacketType().equals(ENTITY_ACTION)) {
            WrapperPlayClientEntityAction packet = new WrapperPlayClientEntityAction(event);
            lastPacketIsStop = packet.getAction().equals(WrapperPlayClientEntityAction.Action.STOP_SPRINTING);
        } else lastPacketIsStop = false;
    }
}
