package ac.grim.grimac.checks.impl.elytra;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.raytrace.RayTraceResult;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.InteractionHand;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUseItem;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "ElytraQ")
public class ElytraQ extends Check implements PacketCheck {
    private boolean interactThisTick;

    public ElytraQ(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (WrapperPlayClientPlayerFlying.isFlying(event.getPacketType())) {
            interactThisTick = false;
        } else
        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
            if (packet.getAction() != WrapperPlayClientInteractEntity.InteractAction.ATTACK) interactThisTick = true;
        } else
        if (event.getPacketType() == PacketType.Play.Client.USE_ITEM) {
            WrapperPlayClientUseItem packet = new WrapperPlayClientUseItem(event);

            InteractionHand hand = packet.getHand();
            ItemStack item = player.inventory.getItemInHand(hand);

            if (item.getType().equals(ItemTypes.FIREWORK_ROCKET)) {
                if (!interactThisTick && player.objectMouseOver != null && player.objectMouseOver.getType() != null && player.objectMouseOver.getType().equals(RayTraceResult.Type.BLOCK)) {
                    WrappedBlockState block = player.objectMouseOver.getState();
                    if (shouldModifyPackets()) {
                        player.onPacketCancel();
                        event.setCancelled(true);
                    }
                    flagAndAlert(String.format("block=%s", block.getType().getName()));
                }

            }
        }
    }
}
