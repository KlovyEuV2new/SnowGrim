package ac.grim.grimac.checks.impl.inventory;

import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.InventoryCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.data.SampleList;
import ac.grim.grimac.utils.math.GrimMath;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "InventoryX", setback = 3, description = "low std values")
public class InventoryX extends InventoryCheck {
    private SampleList<Integer> clicks = new SampleList<>(5);
    private ItemStack lastItem;
    private long lastClickTime = 0;
    private int lastSlot = 0;

    public InventoryX(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.CLICK_WINDOW) {
            return;
        }

        WrapperPlayClientClickWindow wrapper = new WrapperPlayClientClickWindow(event);
        long currentTime = System.currentTimeMillis();

        if (lastClickTime > 0L) {
            long timeDiff = currentTime - lastClickTime;

            if (timeDiff > 0L && timeDiff < 100L) {
                int slot = wrapper.getSlot();
                clicks.add(slot);

                if (clicks.isCollected()) {
                    double standardDeviation = GrimMath.getStandardDeviation(clicks);
                    if (standardDeviation < 5.0) {
                        flagAndAlert(String.format("Std: %.5f", standardDeviation));
                        if (shouldModifyPackets()) event.setCancelled(true);
                    }
                }
            }
        }

        lastClickTime = currentTime;
    }
}
