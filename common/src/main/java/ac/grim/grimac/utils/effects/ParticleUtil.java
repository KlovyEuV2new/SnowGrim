package ac.grim.grimac.utils.effects;

import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.particle.Particle;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleType;
import com.github.retrooper.packetevents.protocol.sound.Sound;
import com.github.retrooper.packetevents.protocol.sound.SoundCategory;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerParticle;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSoundEffect;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3f;

import java.util.Collection;

public class ParticleUtil {

    public static void spawn(User user, ParticleType type, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        if (user == null) return;

        Particle<?> particle = new Particle<>(type, null);
        WrapperPlayServerParticle packet = new WrapperPlayServerParticle(
                particle,
                true,
                new Vector3d(x, y, z),
                new Vector3f(offsetX, offsetY, offsetZ),
                speed,
                count
        );
        user.sendPacket(packet);
    }

    public static void spawn(Collection<User> users, ParticleType type, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count) {
        if (users == null || users.isEmpty()) return;

        Particle<?> particle = new Particle<>(type, null);
        WrapperPlayServerParticle packet = new WrapperPlayServerParticle(
                particle,
                true,
                new Vector3d(x, y, z),
                new Vector3f(offsetX, offsetY, offsetZ),
                speed,
                count
        );

        for (User user : users) {
            user.sendPacket(packet);
        }
    }

    public static void play(User user, Sound sound, double x, double y, double z, float volume, float pitch) {
        if (user == null) return;

        WrapperPlayServerSoundEffect packet = new WrapperPlayServerSoundEffect(
                sound,
                SoundCategory.MASTER,
                new Vector3i((int) x, (int) y, (int) z),
                volume,
                pitch
        );
        user.sendPacket(packet);
    }

    public static void play(Collection<User> users, Sound sound, double x, double y, double z, float volume, float pitch) {
        if (users == null || users.isEmpty()) return;

        WrapperPlayServerSoundEffect packet = new WrapperPlayServerSoundEffect(
                sound,
                SoundCategory.MASTER,
                new Vector3i((int) x, (int) y, (int) z),
                volume,
                pitch
        );

        for (User user : users) {
            user.sendPacket(packet);
        }
    }
}
