package ac.grim.grimac.checks.impl.misc;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@CheckData(name = "SetBackTimeListener", description = "Time' SetBack Move's Listener")
public class SetBackTimeListener extends Check implements PacketCheck {
    private static final long DEFAULT = Long.MIN_VALUE;
    private static final ConcurrentHashMap<UUID,Long> unBlockTime = new ConcurrentHashMap<>();
    public SetBackTimeListener(GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() == PacketType.Play.Server.OPEN_WINDOW) {
            if (player.packetStateData.lastPacket.getPacketType()
                    == PacketType.Play.Client.USE_ITEM) {
                player.isUseExemptGui = true;
            }
        } else
        if (event.getPacketType() == PacketType.Play.Server.CLOSE_WINDOW) {
            player.isUseExemptGui = false;
        }
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (player.disableGrim) return;

        if (!(event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION ||
                event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION)) {
            if (event.getPacketType() == PacketType.Play.Client.CLOSE_WINDOW) {
                player.isUseExemptGui = false;
            }
            return;
        }

        long now = System.nanoTime();
        long unblock = unBlockTime.getOrDefault(player.getUniqueId(),DEFAULT);
        if (unblock != DEFAULT && unblock - now > 0) {
            player.getSetbackTeleportUtil().executeNonSimulatingSetback();
        } else if (unblock != DEFAULT) unBlockTime.remove(player.getUniqueId());
    }
    public static boolean addSetBack(UUID uuid, long nanos) {
        long now = System.nanoTime();
        long block = now + nanos;
        long unblock = unBlockTime.getOrDefault(uuid,DEFAULT);
        if (unblock == DEFAULT || block > unblock) {
            unBlockTime.put(uuid,block);
            return true;
        }
        return false;
    }
}
