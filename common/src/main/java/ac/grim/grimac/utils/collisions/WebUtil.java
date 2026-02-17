package ac.grim.grimac.utils.collisions;

import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.protocol.world.states.type.StateType;
import com.github.retrooper.packetevents.protocol.world.states.type.StateTypes;

public class WebUtil {

    public static boolean isInWeb(GrimPlayer player) {
        if (player.isSwimming) {
            return checkSwimmingPlayer(player);
        }
        return checkRegularPlayer(player);
    }

    private static boolean checkSwimmingPlayer(GrimPlayer player) {
        int x = (int) Math.floor(player.x);
        int y = (int) Math.floor(player.y) + 1;
        int z = (int) Math.floor(player.z);

        return isWebOrSolid(player.compensatedWorld.getBlockType(x, y, z)) ||
                isWebOrSolid(player.compensatedWorld.getBlockType(x + 1, y, z)) ||
                isWebOrSolid(player.compensatedWorld.getBlockType(x - 1, y, z)) ||
                isWebOrSolid(player.compensatedWorld.getBlockType(x, y, z + 1)) ||
                isWebOrSolid(player.compensatedWorld.getBlockType(x, y, z - 1));
    }

    private static boolean checkRegularPlayer(GrimPlayer player) {
        return checkHeadInWeb(player) || checkFeetInWeb(player);
    }

    private static boolean checkHeadInWeb(GrimPlayer player) {
        int eyeX = (int) Math.floor(player.x);
        int eyeY = (int) Math.floor(player.y + player.getEyeHeight());
        int eyeZ = (int) Math.floor(player.z);

        for (int xOff = -1; xOff <= 1; xOff++) {
            for (int yOff = -1; yOff <= 0; yOff++) {
                for (int zOff = -1; zOff <= 1; zOff++) {
                    if (isWebOrSolid(player.compensatedWorld.getBlockType(
                            eyeX + xOff, eyeY + yOff, eyeZ + zOff))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkFeetInWeb(GrimPlayer player) {
        int x = (int) Math.floor(player.x);
        int y = (int) Math.floor(player.y);
        int z = (int) Math.floor(player.z);

        for (int xOff = -1; xOff <= 1; xOff++) {
            for (int zOff = -1; zOff <= 1; zOff++) {
                if (isWebOrSolid(player.compensatedWorld.getBlockType(
                        x + xOff, y, z + zOff))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isWebOrSolid(StateType type) {
        return type == StateTypes.COBWEB ||
                type == StateTypes.SLIME_BLOCK ||
                type == StateTypes.LADDER ||
                type == StateTypes.VINE ||
                type == StateTypes.SCAFFOLDING ||
                type == StateTypes.WEEPING_VINES ||
                type == StateTypes.TWISTING_VINES ||
                type == StateTypes.WEEPING_VINES_PLANT ||
                type == StateTypes.TWISTING_VINES_PLANT;
    }
}
