package ac.grim.grimac.utils.collisions;

import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.protocol.world.states.type.StateType;
import com.github.retrooper.packetevents.protocol.world.states.type.StateTypes;

public class IceUtil {
    public static boolean isOnIce(GrimPlayer player) {
        int x = (int) Math.floor(player.x);
        int y = (int) Math.floor(player.y) - 1;
        int z = (int) Math.floor(player.z);

        StateType blockBelow = player.compensatedWorld.getBlockType(x, y, z);

        StateType blockFront = player.compensatedWorld.getBlockType(x, y, z + 1);
        StateType blockBack = player.compensatedWorld.getBlockType(x, y, z - 1);
        StateType blockLeft = player.compensatedWorld.getBlockType(x - 1, y, z);
        StateType blockRight = player.compensatedWorld.getBlockType(x + 1, y, z);

        return isIce(blockBelow) || isIce(blockFront) || isIce(blockBack) ||
                isIce(blockLeft) || isIce(blockRight);
    }

    private static boolean isIce(StateType stateType) {
        return stateType == StateTypes.ICE ||
                stateType == StateTypes.PACKED_ICE ||
                stateType == StateTypes.BLUE_ICE ||
                stateType == StateTypes.FROSTED_ICE;
    }
}
