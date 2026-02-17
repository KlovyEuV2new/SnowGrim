package ac.grim.grimac.api.npcs.checks.aim;

import ac.grim.grimac.api.npcs.NpcManager;
import ac.grim.grimac.api.npcs.util.NpcUtil;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


@CheckData(name = "NpcAura", description = "Npc' ActionCheck")
public class NpcAura extends Check implements PacketCheck {
    public NpcAura(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
            UUID uuid = player.getUniqueId();
            WrapperPlayClientInteractEntity.InteractAction action = packet.getAction();

            long now = System.currentTimeMillis();
            if (action == WrapperPlayClientInteractEntity.InteractAction.ATTACK) {
                NpcManager.TrackedNpc npc = NpcManager.npcMap.getOrDefault(uuid,null);
                if (npc == null) return;
                boolean attackedNpc = packet.getEntityId() == npc.entityId;
                if (attackedNpc && flagAndAlert("type=" + action.name() + ", npc=" + npc.entityId)) {
                    NpcUtil.spawnNpc(player);
                    npc.lastAttack = now;
                }
            }
        }
    }
}
