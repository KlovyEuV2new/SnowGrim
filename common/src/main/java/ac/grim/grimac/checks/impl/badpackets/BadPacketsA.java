package ac.grim.grimac.checks.impl.badpackets;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientHeldItemChange;

@CheckData(name = "BadPacketsA", description = "invalid held slot change pattern")
public class BadPacketsA extends Check implements PacketCheck {

    private int lastSlot = -1;
    private PacketTypeCommon lastPacket, wasPacket;

    public BadPacketsA(final GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.HELD_ITEM_CHANGE) {

            WrapperPlayClientHeldItemChange wrapper =
                    new WrapperPlayClientHeldItemChange(event);
            int newSlot = wrapper.getSlot();

            if (lastPacket == PacketType.Play.Client.HELD_ITEM_CHANGE
                    || (wasPacket == PacketType.Play.Client.USE_ITEM
                    && lastPacket == PacketType.Play.Client.ANIMATION)) {

                if (shouldModifyPackets()) {
                    event.setCancelled(true);
                    player.onPacketCancel();
                }

                flagAndAlert("(C) last packet is slot change.");
            }

            lastSlot = newSlot;
        }

        wasPacket = lastPacket;
        lastPacket = event.getPacketType();
    }

    @Override
    public void onReload(ConfigManager config) {
    }
}
