package ac.grim.grimac.checks.impl.elytra;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.util.Vector3d;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@CheckData(name = "ElytraJ", setback = 5, decay = 0.025, description = "Elytra speed check")
public class ElytraJ extends Check implements PacketCheck {

    private final ConcurrentHashMap<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();
    private double baseMaxSpeed;
    private double speedMultiplierFactor;
    private boolean enabled;

    private static class PlayerData {
        double lastPitch;

        PlayerData(double initialPitch) {
            this.lastPitch = initialPitch;
        }
    }

    public ElytraJ(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (!enabled || player.disableGrim) return;

        if (!player.isGliding || !player.wasGliding || player.inVehicle() ||
                player.getSetbackTeleportUtil().blockOffsets ||
                player.packetStateData.lastPacketWasTeleport) {
            return;
        }

        if (!(event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION
                || event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION)) {
            return;
        }

        handleEssentials();
    }

    private void handleEssentials() {
        Vector3d from = new Vector3d(player.lastX, player.lastY, player.lastZ);
        Vector3d to = new Vector3d(player.x, player.y, player.z);

        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) {
            return;
        }

        Vector3d velocity = to.subtract(from);
        double horizontalDistance = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ());

        double pitch = player.pitch;
        PlayerData data = playerDataMap.computeIfAbsent(player.getUniqueId(), k -> new PlayerData(pitch));

        double lastPitch = data.lastPitch;
        data.lastPitch = pitch;
        double deltaPitch = pitch - lastPitch;

        boolean isNearHorizontal = lastPitch > -30.0;
        boolean isSharpDive = deltaPitch < -15.0;
        boolean isAngleSuspicious = isNearHorizontal && isSharpDive;

        double speedMultiplier = pitch < 0 ? 1.0 - (pitch / speedMultiplierFactor) : 1.0;
        double dynamicMaxSpeed = baseMaxSpeed * speedMultiplier;

        if (horizontalDistance > dynamicMaxSpeed && !isAngleSuspicious) {
            if (flagAndAlert(String.format("Speed=%.5f/%.5f, Ping=%d", horizontalDistance, dynamicMaxSpeed, player.getTransactionPing()))) {
                if (shouldSetback()) {
                    player.getSetbackTeleportUtil().teleportBack();
                }
            }
        } else {
            reward();
        }
    }

    @Override
    public void onReload(ConfigManager config) {
        enabled = config.getBooleanElse(getConfigName() + ".enabled", true);
        baseMaxSpeed = config.getDoubleElse(getConfigName() + ".baseMaxSpeed", 1.89);
        speedMultiplierFactor = config.getDoubleElse(getConfigName() + ".speedMultiplierFactor", 50.0);
    }
}
