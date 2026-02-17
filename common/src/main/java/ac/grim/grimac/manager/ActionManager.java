package ac.grim.grimac.manager;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.BlockFace;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;

import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import lombok.Getter;

@Getter
public class ActionManager extends Check implements PacketCheck {
    private boolean attacking = false;
    private long lastAttack = 0;
    public GrimPlayer target;
    public ActionManager(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity action = new WrapperPlayClientInteractEntity(event);
            if (action.getAction() == WrapperPlayClientInteractEntity.InteractAction.ATTACK) {
                player.totalFlyingPacketsSent = 0;
                attacking = true;
                lastAttack = System.currentTimeMillis();
                player.hitticks = 0;
            }
        } else if (isTickPacketIncludingNonMovement(event.getPacketType())) {
            player.totalFlyingPacketsSent++;
            attacking = false;
        }if (event.getPacketType() == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {
            WrapperPlayClientPlayerBlockPlacement playClientPlayerBlockPlacement = new WrapperPlayClientPlayerBlockPlacement(event);
            if (playClientPlayerBlockPlacement.getFace() != BlockFace.OTHER) {

                player.placeTicks = 0;
            }
        }
        if (isTickPacket(event.getPacketType())) {
            player.placeTicks++;
            player.hitticks++;
            player.lastAttackedRealTick++;
            // }
        }
    }

    public boolean hasAttackedSince(long time) {
        return System.currentTimeMillis() - lastAttack < time;
    }
    public long getPassedAttackSince() {
        return System.currentTimeMillis() - lastAttack;
    }
}
