package ac.grim.grimac.checks.impl.elytra;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.potion.PotionTypes;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "ElytraP")
public class ElytraP extends Check implements PacketCheck {
    private boolean setback;

    public ElytraP(@NotNull GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (WrapperPlayClientPlayerFlying.isFlying(event.getPacketType())) {
            if (setback) {
                setback = false;
                player.getSetbackTeleportUtil().executeViolationSetback();
            }
        } else
        if (event.getPacketType() == PacketType.Play.Client.ENTITY_ACTION) {
            WrapperPlayClientEntityAction packet = new WrapperPlayClientEntityAction(event);
            if (packet.getAction().equals(WrapperPlayClientEntityAction.Action.START_FLYING_WITH_ELYTRA)) {
                if (!tryToStartFallFlying()) {
                    setback = true;
                    player.stopGliding();
                    flagAndAlert("invalid start fall fly action");
                }
            }
        }
    }

    public boolean tryToStartFallFlying() {
        if (!player.onGround && !player.isFlying && (!player.wasTouchingWater || !player.getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_15)) && !player.isOnLadder()
                && !player.inVehicle() && !player.compensatedEntities.self.hasPotionEffect(PotionTypes.LEVITATION)) {
            ItemStack itemstack = player.inventory.getChestplate();
            return itemstack.getType() == ItemTypes.ELYTRA && isUsable(itemstack);
        }

        return false;
    }

    public static boolean isUsable(ItemStack stack)
    {
        return stack.getDamageValue() < stack.getMaxDamage() - 1;
    }
}
