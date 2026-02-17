package ac.grim.grimac.utils.protocol;

import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerPositionAndLook;

import java.util.concurrent.atomic.AtomicInteger;

public class ProtocolUtil {

    private static final AtomicInteger teleportIdCounter = new AtomicInteger(0);

    public static class GLocation {
        private final double x, y, z;
        private final float yaw, pitch;

        public GLocation(double x, double y, double z, float yaw, float pitch) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public double getX() { return x; }
        public double getY() { return y; }
        public double getZ() { return z; }
        public float getYaw() { return yaw; }
        public float getPitch() { return pitch; }
    }

    public static boolean teleport(GrimPlayer player, GLocation location) {
        if (player == null || location == null) return false;

        try {
            int teleportId = teleportIdCounter.incrementAndGet();

            WrapperPlayServerPlayerPositionAndLook packet =
                    new WrapperPlayServerPlayerPositionAndLook(
                            location.getX(),
                            location.getY(),
                            location.getZ(),
                            location.getYaw(),
                            location.getPitch(),
                            (byte) 0,
                            teleportId,
                            false
                    );

            PacketEvents.getAPI()
                    .getProtocolManager()
                    .sendPacketSilently(player.user.getChannel(), packet);

            player.sendTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
