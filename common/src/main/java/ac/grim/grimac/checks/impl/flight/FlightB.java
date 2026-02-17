package ac.grim.grimac.checks.impl.flight;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import ac.grim.grimac.utils.data.attribute.ValuedAttribute;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.attribute.Attributes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.GameMode;

import java.util.Optional;

@CheckData(name = "FlightB", setback = 0, description = "Flight speed check")
public class FlightB extends Check implements PacketCheck {

    private boolean enabled;

    public FlightB(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (!enabled || player.disableGrim) return;
        if (!player.isFlying || !player.wasFlying || player.inVehicle() ||
                player.getSetbackTeleportUtil().blockOffsets ||
                player.packetStateData.lastPacketWasTeleport) {
            return;
        }

        if (!(event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION
                || event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION)) return;

        double x = player.x;
        double y = player.y;
        double z = player.z;

        double dx = player.lastX - x;
        double dy = player.lastY - y;
        double dz = player.lastZ - z;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        double maxDistance = getMaxSpeed();
        if (distance > maxDistance) {
            if (flagAndAlert("speed=" + String.format("%.5f",distance) + "/" + String.format("%.5f",maxDistance))) {
                if (shouldSetback()) {
                    player.getSetbackTeleportUtil().teleportBack();
                }
            }
        } else {
            reward();
        }
    }

    private double getMaxSpeed() {
        Optional<ValuedAttribute> flyingAttr = player.compensatedEntities.self.getAttribute(Attributes.GENERIC_FLYING_SPEED);
        double attributeSpeed = (flyingAttr != null && flyingAttr.isPresent()) ? flyingAttr.get().get() : 0.0;
        double flySpeed = player.bukkiFlySpeed != Float.MAX_VALUE ? player.bukkiFlySpeed : player.flySpeed;
        if (player.flySpeed == Integer.MAX_VALUE) flySpeed = 1.0;
        double base = 1.2;
        if ((flyingAttr != null && flyingAttr.isPresent()) || player.gamemode.equals(GameMode.SPECTATOR)) {
            return Integer.MAX_VALUE;
        }
        return base * flySpeed * 10.0;
    }

    @Override
    public void onReload(ConfigManager config) {
        enabled = config.getBooleanElse(getConfigName() + ".enabled", true);
    }
}
