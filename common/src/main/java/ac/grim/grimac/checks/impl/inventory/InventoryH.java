package ac.grim.grimac.checks.impl.inventory;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.InventoryCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;


@CheckData(name = "InventoryH", description = "invalid close pattern")
public class InventoryH extends InventoryCheck {
    public PacketTypeCommon lastPacket = null;

    public InventoryH(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (player.disableGrim) return;
        super.onPacketReceive(event);

        PacketTypeCommon packetType = event.getPacketType();

        if (event.getPacketType() == PacketType.Play.Client.CLOSE_WINDOW) {

            if (lastPacket != null
                    && lastPacket == PacketType.Play.Client.CLICK_WINDOW) {
                flagAndAlert("last packet is window click.");
            }
        }

        lastPacket = packetType;
    }

    @Override
    public void onReload(ConfigManager config) {
    }
}
