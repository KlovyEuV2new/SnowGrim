package ac.grim.grimac.api.npcs.util;

import ac.grim.grimac.api.npcs.NpcManager;
import ac.grim.grimac.player.GrimPlayer;

public class NpcUtil {
    public static boolean spawnNpc(GrimPlayer player, String npcName, long liveTime) {
        if (player == null || npcName == null) return false;
        NpcManager.spawnNpcFor(player,npcName,UUIDUtil.getOfflineUUIDSimple(npcName), liveTime);
        return true;
    }
    public static boolean spawnNpc(GrimPlayer player, String npcName) {
        if (player == null || npcName == null) return false;
        NpcManager.spawnNpcFor(player,npcName,UUIDUtil.getOfflineUUIDSimple(npcName), NpcManager.getTIMEOUT_MS());
        return true;
    }
    public static boolean spawnNpc(GrimPlayer player) {
        if (player == null) return false;
        String npcName = NpcManager.nameManager.getRandomName(player,NpcManager.randomMode);
        NpcManager.spawnNpcFor(player,npcName,UUIDUtil.getOfflineUUIDSimple(npcName), NpcManager.getTIMEOUT_MS());
        return true;
    }
}
