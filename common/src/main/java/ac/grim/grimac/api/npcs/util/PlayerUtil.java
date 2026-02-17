package ac.grim.grimac.api.npcs.util;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.player.GrimPlayer;

public class PlayerUtil {
    public static GrimPlayer getPlayer(int entityId) {
        for (GrimPlayer gp : GrimAPI.INSTANCE.getPlayerDataManager().getEntries()) {
            if (gp.entityID == entityId) return gp;
        }
        return null;
    }
}
