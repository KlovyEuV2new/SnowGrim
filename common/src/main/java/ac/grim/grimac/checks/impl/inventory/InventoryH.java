package ac.grim.grimac.checks.impl.inventory;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.InventoryCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;

import static com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client.CLICK_WINDOW;

@CheckData(name = "InventoryH", setback = 0, description = "Fast Click-Close Inventory")
public class InventoryH extends InventoryCheck {
    private Long lastClick = null;
    private Long lastClose = null;
    public long md;

    public InventoryH(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (player.disableGrim) return;
        super.onPacketReceive(event);

        long now = System.nanoTime();
        if (event.getPacketType() == CLICK_WINDOW) {
            lastClick = now;
        } else if (event.getPacketType() == PacketType.Play.Client.CLOSE_WINDOW) {
            if (lastClick != null && (now - lastClick) < md && (player.packetStateData.lastPacket != null)) {
                long diff = now - lastClick;
                flagAndAlert("diff=" + String.format("%.3f",(double) (diff / 1_000_000)) + "ms");
            } else reward();
            lastClose = now;
        }
    }

    @Override
    public void onReload(ConfigManager config) {
        md = (long) (config.getDoubleElse(getConfigName() + ".diff", 0.99) * 1_000_000L);
    }
}
