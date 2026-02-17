package ac.grim.grimac.api.npcs.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UUIDUtil {
    public static UUID getOfflineUUIDSimple(String playerName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(StandardCharsets.UTF_8));
    }
}
