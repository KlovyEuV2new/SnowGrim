package ac.grim.grimac.checks.impl.movement;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.platform.api.Platform;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.math.GrimMath;
import ac.grim.grimac.utils.math.Location;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.potion.PotionTypes;
import com.github.retrooper.packetevents.protocol.world.states.type.StateTypes;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "WebOffset", description = "fix noweb", experimental = true,setback = 3)
public class WebOffset extends Check implements PacketCheck {
    private int bufferXZ;
    private int bufferY;
    private int bufferGround;
    public WebOffset(@NotNull GrimPlayer player) {
        super(player);
    }
    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
//        if (isTickPacket(event.getPacketType())) {
//            player.sendMessage("5" + player.packetStateData.isSlowedByUsingItem());
//            player.packetStateData.setSlowedByUsingItem(player.bukkitPlayer.isHandRaised());


//        },.
        if (event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION) {
            if (GrimAPI.INSTANCE.getPlatform() != Platform.BUKKIT) return; //нужно для обратной совместимости с фолия? fabric был удален по техническим причинам
            double deltaXZ = player.deltaXZ();
            double speed;
            speed = GrimMath.getPlayerSpeed(this.player);
            @NotNull Location loc = player.getLocation().clone();
            boolean inWeb = player.compensatedWorld.getBlock(loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()).getType() == StateTypes.COBWEB; //player.bukkitPlayer.getLocation().getBlock().getType() == Material.COBWEB;
            boolean tridentTicks = player.riptideSpinAttackTicks < -50;
            boolean exempt1 = !player.onGround && !player.isGliding && !player.isFlying && !player.predictedVelocity.isJump() && !player.predictedVelocity.isKnockback();
            boolean invalidXZ = inWeb && deltaXZ > 0.05F && speed > 0.3F;
            if (invalidXZ && exempt1 && tridentTicks) {
                if (bufferXZ++ > 1) {
                    flagAndAlert(String.format("o[1]: %.6f", deltaXZ));
                    if (shouldSetback()) player.getSetbackTeleportUtil().executeViolationSetbackDown();
                }
            } else {
                bufferXZ = 0;

            }

            double deltaY = player.actualMovement.getY();
            boolean invalidY = inWeb && deltaY > 0.005;
            boolean exempt2 = !player.compensatedEntities.self.hasPotionEffect(PotionTypes.LEVITATION) && !player.isGliding && !player.isFlying && !player.predictedVelocity.isJump() && !player.predictedVelocity.isKnockback();
            if (invalidY && exempt2 && tridentTicks) {
                if (bufferY++ > 1) {
                    flagAndAlert(String.format("o[y]: %.6f", deltaY));
                    if (shouldSetback()) player.getSetbackTeleportUtil().executeViolationSetbackDown();
                }
            } else {
                bufferY = 0;
            }

            double onGroundXZ = player.deltaXZ();
            boolean check = inWeb && onGroundXZ > 0.07;
            boolean data = player.onGround && !player.compensatedEntities.self.hasPotionEffect(PotionTypes.SPEED) && !player.isGliding && !player.isFlying && !player.predictedVelocity.isJump() && !player.predictedVelocity.isKnockback();
            if (check && data && tridentTicks) {
                if (bufferGround++ > 1) {
                    flagAndAlert(String.format("o[2]: %.6f", onGroundXZ));
                    if (shouldSetback()) player.getSetbackTeleportUtil().executeViolationSetbackDown();
                }
            } else {
                bufferGround = 0;
            }
        }
    }
}
