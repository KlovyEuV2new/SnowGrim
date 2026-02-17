package ac.grim.grimac.utils.collisions;

import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.protocol.world.states.type.StateType;
import com.github.retrooper.packetevents.protocol.world.states.type.StateTypes;

public class FluidUtil {

    public static boolean isInFluid(GrimPlayer player) {
        int x = (int) Math.floor(player.x);
        int y = (int) Math.floor(player.y);
        int z = (int) Math.floor(player.z);

        StateType current = player.compensatedWorld.getBlockType(x, y, z);
        StateType above = player.compensatedWorld.getBlockType(x, y + 1, z);

        return isFluid(current) || isFluid(above);
    }

    public static boolean isInFluidPrecise(GrimPlayer player) {
        int x = (int) Math.floor(player.x);
        int y = (int) Math.floor(player.y);
        int z = (int) Math.floor(player.z);

        return isFluid(player.compensatedWorld.getBlockType(x, y, z)) ||
                isFluid(player.compensatedWorld.getBlockType(x, y + 1, z)) ||
                isFluid(player.compensatedWorld.getBlockType(x + 1, y, z)) ||
                isFluid(player.compensatedWorld.getBlockType(x - 1, y, z)) ||
                isFluid(player.compensatedWorld.getBlockType(x, y, z + 1)) ||
                isFluid(player.compensatedWorld.getBlockType(x, y, z - 1));
    }

    public static boolean isInWater(GrimPlayer player) {
        int x = (int) Math.floor(player.x);
        int y = (int) Math.floor(player.y);
        int z = (int) Math.floor(player.z);

        StateType current = player.compensatedWorld.getBlockType(x, y, z);
        StateType above = player.compensatedWorld.getBlockType(x, y + 1, z);

        return isWater(current) || isWater(above);
    }

    public static boolean isInLava(GrimPlayer player) {
        int x = (int) Math.floor(player.x);
        int y = (int) Math.floor(player.y);
        int z = (int) Math.floor(player.z);

        StateType current = player.compensatedWorld.getBlockType(x, y, z);
        StateType above = player.compensatedWorld.getBlockType(x, y + 1, z);

        return isLava(current) || isLava(above);
    }

    private static boolean isFluid(StateType type) {
        return isWater(type) || isLava(type);
    }

    private static boolean isWater(StateType type) {
        return type == StateTypes.WATER;
    }

    private static boolean isLava(StateType type) {
        return type == StateTypes.LAVA;
    }
}
