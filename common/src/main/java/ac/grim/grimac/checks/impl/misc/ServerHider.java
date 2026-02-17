package ac.grim.grimac.checks.impl.misc;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.nbt.*;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateAttributes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateEntityNBT;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@CheckData(name = "ServerHider", description = "Hide data for for nametags hacks.")
public class ServerHider extends Check implements PacketCheck {
    private boolean playerinfo_enabled;
    private boolean gamemode_enabled;
    private boolean attribute_enabled;

    private boolean antibot_crasher;
    private NBTCType nbtcType;

    private ArrayList<ReplacementGamemode> gamemodes;

    public record ReplacementGamemode(GameMode from, GameMode to) {}

    public ServerHider(@NotNull GrimPlayer player) {
        super(player);
        this.gamemodes = new ArrayList<>();
    }

    public enum NBTCType {
        EQUIPMENT, CANCEL
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (player.disableGrim) return;
        if (event.getPacketType() == PacketType.Play.Server.UPDATE_ATTRIBUTES) {
            WrapperPlayServerUpdateAttributes packet = new WrapperPlayServerUpdateAttributes(event);
            if (packet.getEntityId() != player.entityID) {
                if (attribute_enabled) {
                    event.setCancelled(true);
                }
            }
        } else if (event.getPacketType() == PacketType.Play.Server.UPDATE_ENTITY_NBT) {
            // Nursultan Client AntiBot Crasher
            if (antibot_crasher) {
                WrapperPlayServerUpdateEntityNBT packet = new WrapperPlayServerUpdateEntityNBT(event);
                if (packet.getEntityId() != player.entityID) {
                    switch (nbtcType) {
                        case CANCEL -> {
                            event.setCancelled(true);
                            break;
                        }
                        case EQUIPMENT -> {
                            NBTCompound nbt = (NBTCompound) packet.getNBTCompound();
                            nbt.setTag("ArmorItems", new NBTCompound());
                            nbt.setTag("HandItems", new NBTCompound());
                            packet.setNBTCompound(nbt);
                            break;
                        }
                    }
                }
            }
        } else if (event.getPacketType() == PacketType.Play.Server.PLAYER_INFO_UPDATE) {
            if (playerinfo_enabled && gamemode_enabled && !gamemodes.isEmpty()) {
                WrapperPlayServerPlayerInfoUpdate packet = new WrapperPlayServerPlayerInfoUpdate(event);
                List<WrapperPlayServerPlayerInfoUpdate.PlayerInfo> entries = packet.getEntries();
                if (entries == null || entries.isEmpty()) return;

                List<WrapperPlayServerPlayerInfoUpdate.PlayerInfo> newEntries = new ArrayList<>(entries.size());
                boolean modified = false;

                for (WrapperPlayServerPlayerInfoUpdate.PlayerInfo originalEntry : entries) {
                    if (originalEntry.getGameProfile().getUUID().equals(player.getUniqueId())) {
                        newEntries.add(originalEntry);
                        continue;
                    }
                    GameMode newGameMode = findReplacement(originalEntry.getGameMode());
                    if (newGameMode != null) {
                        WrapperPlayServerPlayerInfoUpdate.PlayerInfo newEntry = new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                                originalEntry.getGameProfile(),
                                originalEntry.isListed(),
                                originalEntry.getLatency(),
                                newGameMode,
                                originalEntry.getDisplayName(),
                                originalEntry.getChatSession()
                        );
                        newEntries.add(newEntry);
                        modified = true;
                    } else {
                        newEntries.add(originalEntry);
                    }
                }

                if (modified) {
                    packet.setEntries(newEntries);
                    event.markForReEncode(true);
                }
            }
        } else if (event.getPacketType() == PacketType.Play.Server.PLAYER_INFO) {
            WrapperPlayServerPlayerInfo packet = new WrapperPlayServerPlayerInfo(event);
            List<WrapperPlayServerPlayerInfo.PlayerData> entries = packet.getPlayerDataList();
            if (entries.isEmpty()) return;

            List<WrapperPlayServerPlayerInfo.PlayerData> newEntries = new ArrayList<>(entries.size());
            boolean modified = false;

            for (WrapperPlayServerPlayerInfo.PlayerData originalEntry : entries) {
                if (originalEntry.getUserProfile() != null && originalEntry.getUserProfile().getUUID().equals(player.getUniqueId())) {
                    newEntries.add(originalEntry);
                    continue;
                }
                GameMode newGameMode = findReplacement(originalEntry.getGameMode());
                if (newGameMode != null) {
                    WrapperPlayServerPlayerInfo.PlayerData newEntry = new WrapperPlayServerPlayerInfo.PlayerData(
                            originalEntry.getDisplayName(),
                            originalEntry.getUserProfile(),
                            newGameMode,
                            originalEntry.getPing()
                    );
                    newEntries.add(newEntry);
                    modified = true;
                } else {
                    newEntries.add(originalEntry);
                }
            }

            if (modified) {
                packet.setPlayerDataList(newEntries);
                event.markForReEncode(true);
            }
        }
    }

    private GameMode findReplacement(GameMode current) {
        for (ReplacementGamemode replacement : gamemodes) {
            if (replacement.from == current) {
                return replacement.to;
            }
        }
        return null;
    }

    @Override
    public void onReload(ConfigManager config) {
        antibot_crasher = config.getBooleanElse(getConfigName() + ".nbt.enabled",true);
        nbtcType = NBTCType.valueOf(config.getStringElse(getConfigName() + ".nbt.type","EQUIPMENT").toUpperCase());
        playerinfo_enabled = config.getBooleanElse(getConfigName() + ".playerinfo.enabled", true);
        attribute_enabled = config.getBooleanElse(getConfigName() + ".attribute.enabled", true);
        gamemode_enabled = config.getBooleanElse(getConfigName() + ".playerinfo.gamemode.enabled", true);

        if (gamemodes == null) {
            gamemodes = new ArrayList<>();
        }

        gamemodes.clear();
        List<?> rawList = config.getListElse(getConfigName() + ".playerinfo.gamemode.replaces", new ArrayList<>());

        for (Object obj : rawList) {
            if (obj instanceof String) {
                String entry = (String) obj;
                String[] parts = entry.split(":");
                if (parts.length == 2) {
                    try {
                        GameMode from = GameMode.valueOf(parts[0].trim().toUpperCase());
                        GameMode to = GameMode.valueOf(parts[1].trim().toUpperCase());
                        gamemodes.add(new ReplacementGamemode(from, to));
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
        }
    }
}
