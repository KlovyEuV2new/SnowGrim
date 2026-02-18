package ac.grim.grimac.checks.impl.badpackets;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "BadPacketsAB")
public class BadPacketsAB extends Check implements PacketCheck {
    public BadPacketsAB(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        PacketTypeCommon packetType = event.getPacketType();
        if (packetType == PacketType.Play.Client.CLICK_WINDOW ||
                packetType == PacketType.Play.Client.CLOSE_WINDOW) {
            if (player.packetStateData.isSlowedByUsingItem() && !player.isUseExemptGui) {
                if (shouldModifyPackets()) {
                    event.setCancelled(true);
                    player.onPacketCancel();
                }
                flagAndAlert();
            }
        }
    }
}
