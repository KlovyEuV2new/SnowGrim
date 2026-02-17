package ac.grim.grimac.utils.collisions;

import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.protocol.world.states.type.StateType;
import com.github.retrooper.packetevents.protocol.world.states.type.StateTypes;

import java.util.HashSet;
import java.util.Set;

public class CollideUtil {

    private static final Set<StateType> NON_SOLID_BLOCKS = new HashSet<>();

    static {
        NON_SOLID_BLOCKS.add(StateTypes.AIR);
    }

    public static boolean isNearSolidBlock(GrimPlayer player, double radius, double step) {
        double playerX = player.x;
        double playerY = player.y;
        double playerZ = player.z;

        for (double x = playerX - radius; x <= playerX + radius; x += step) {
            for (double y = playerY - radius; y <= playerY + radius; y += step) {
                for (double z = playerZ - radius; z <= playerZ + radius; z += step) {
                    int blockX = (int) Math.floor(x);
                    int blockY = (int) Math.floor(y);
                    int blockZ = (int) Math.floor(z);

                    if (!NON_SOLID_BLOCKS.contains(player.compensatedWorld.getBlockType(blockX, blockY, blockZ))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNearSolidBlockOrEntity(GrimPlayer player, double radius, double step) {
        double playerX = player.x;
        double playerY = player.y;
        double playerZ = player.z;

        for (double x = playerX - radius; x <= playerX + radius; x += step) {
            for (double y = playerY - radius; y <= playerY + radius; y += step) {
                for (double z = playerZ - radius; z <= playerZ + radius; z += step) {
                    int bx = (int) Math.floor(x);
                    int by = (int) Math.floor(y);
                    int bz = (int) Math.floor(z);

                    if (!NON_SOLID_BLOCKS.contains(player.compensatedWorld.getBlockType(bx, by, bz))) {
                        return true;
                    }
                }
            }
        }

        return player.compensatedWorld.isNearHardEntity(player.boundingBox);
    }

}
