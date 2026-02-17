package ac.grim.grimac.utils.anticheat;

import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;

public class TicksUtil {
    public static long getTick(GrimPlayer player, PacketTypeCommon type, WrapperPlayClientEntityAction.Action action) {
        String key = type.getName() + ";" + action.name();
        return player.ticks.getOrDefault(key, -1L);
    }

    public static long getTick(GrimPlayer player, PacketTypeCommon type, WrapperPlayClientInteractEntity.InteractAction action) {
        String key = type.getName() + ";" + action.name();
        return player.ticks.getOrDefault(key, -1L);
    }
}
