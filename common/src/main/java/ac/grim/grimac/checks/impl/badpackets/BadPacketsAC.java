package ac.grim.grimac.checks.impl.badpackets;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "BadPacketsAC")
public class BadPacketsAC extends Check implements PacketCheck {
    public BadPacketsAC(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
    }
}
