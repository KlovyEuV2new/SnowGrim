package ac.grim.grimac.api.npcs;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.api.npcs.armor.PlayerArmor;
import ac.grim.grimac.api.npcs.armor.RandomArmorGenerator;
import ac.grim.grimac.api.npcs.enums.AimMode;
import ac.grim.grimac.api.npcs.enums.RandomMode;
import ac.grim.grimac.api.npcs.enums.RotationMode;
import ac.grim.grimac.api.npcs.name.NPCNameManager;
import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.impl.misc.MetadataIndex;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.*;
import com.github.retrooper.packetevents.protocol.player.Equipment;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.protocol.world.Location;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static ac.grim.grimac.api.npcs.armor.PlayerArmor.toNBT;

public class NpcManager {

    public static NPCNameManager nameManager;

    private static final List<Check> remoteChecks = new CopyOnWriteArrayList<>();
    public static final ConcurrentHashMap<UUID, TrackedNpc> npcMap = new ConcurrentHashMap<>();
    @Getter
    private static long TIMEOUT_MS = 5000;

    private static boolean enabled = true;

    private static ServerVersion version;
    private static boolean isModernVersion;
    private static boolean versionDetected = false;

    public static boolean strafe = false;
    public static RotationMode rotationMode = RotationMode.DEFAULT;
    public static RotationMode spawnMode = RotationMode.DEFAULT;
    public static AimMode aimMode = AimMode.OLD;
    public static int lookPattern = 9;
    public static int smoothPattern = -1;
    public static int defaultPattern = 5;
    public static RandomMode randomMode = RandomMode.TAB;
    public static List<String> npcNames = new CopyOnWriteArrayList<>();
    public static int armorChance = 50; // Off

    public static int updateTicks = 2;

    public static boolean smoothAim_Enabled = true;
    public static int smoothAim_Duration = 2;

    public static boolean handShake_Enabled = true;
    public static long handShake_diff = 580;
    public static int handShake_add = 50;

    public static boolean armorVirtualization_Enabled = true;

    private static double MIN_DISTANCE = 1.5;
    private static double MAX_DISTANCE = 3.5;
    private static double DEFAULT_DISTANCE = 2.5;

    private static double MIN_ANGLE_DEGREES = -45;
    private static double MAX_ANGLE_DEGREES = 45;

    private static final long MAX_TIMEOUT_MS = 60000;

    private static double POSITION_LERP_FACTOR = 0.25;
    private static double ROTATION_LERP_FACTOR = 0.15;

    public static SmoothMode smoothMode = SmoothMode.NEW;

    public NpcManager(ConfigManager config) {
        loadSettings(config);
        nameManager = new NPCNameManager(config);
    }

    public static void loadSettings(@NotNull ConfigManager config) {
        enabled = config.getBooleanElse("npc.enabled", true);
        if (!enabled) return;

        String smoothModeStr = config.getStringElse("npc.smooth-mode", "old").toLowerCase();
        switch (smoothModeStr) {
            case "new":
                smoothMode = SmoothMode.NEW;
                break;
            default:
                smoothMode = SmoothMode.OLD;
                break;
        }

        handShake_Enabled = config.getBooleanElse("npc.hand-shake.enabled", true);
        handShake_diff = config.getLongElse("npc.hand-shake.min-diff", 580L);
        handShake_add = config.getIntElse("npc.hand-shake.random-add", 50);
        smoothAim_Enabled = config.getBooleanElse("npc.smooth-aim.enabled", true);
        smoothAim_Duration = config.getIntElse("npc.smooth-aim.per-ticks", 2);
        armorVirtualization_Enabled = config.getBooleanElse("npc.armor-virtualization", true);
        strafe = config.getBooleanElse("npc.strafe", false);
        String rotMode = config.getStringElse("npc.rotation-mode", "default").toLowerCase();
        rotationMode = rotMode.equals("smart") ? RotationMode.SMART : RotationMode.DEFAULT;
        String randMode = config.getStringElse("npc.random.mode", "TAB").toUpperCase();
        try {
            randomMode = RandomMode.valueOf(randMode);
        } catch (IllegalArgumentException e) {
            randomMode = RandomMode.TAB;
        }
        String namesStr = config.getStringElse("npc.names", "akvi4;MrDomer;MrZenyYT;KondrMs;bro9i;FlugerNew;grimac;grim;matrix;stint;t2x2");
        if (!namesStr.isEmpty()) {
            npcNames = new CopyOnWriteArrayList<>(Arrays.asList(namesStr.split(";")));
        } else {
            npcNames = new CopyOnWriteArrayList<>(Arrays.asList("akvi4", "IIuoner", "Error404"));
        }
        TIMEOUT_MS = config.getLongElse("npc.timeout", 5000L);
        updateTicks = config.getIntElse("npc.rotation-update", 2);
        armorChance = config.getIntElse("npc.armor-chance", 50);
        String spawnModeStr = config.getStringElse("npc.spawn-mode", "SMART").toUpperCase();
        try {
            spawnMode = RotationMode.valueOf(spawnModeStr);
        } catch (IllegalArgumentException e) {
            spawnMode = RotationMode.SMART;
        }
        MIN_ANGLE_DEGREES = config.getDoubleElse("npc.angle.min", -45.0);
        MAX_ANGLE_DEGREES = config.getDoubleElse("npc.angle.max", 45.0);
        MAX_DISTANCE = config.getDoubleElse("npc.distance.max", 3.5);
        DEFAULT_DISTANCE = config.getDoubleElse("npc.distance.default", 2.5);
        MIN_DISTANCE = config.getDoubleElse("npc.distance.min", 1.5);

        POSITION_LERP_FACTOR = config.getDoubleElse("npc.new-mode.speed", 0.35);
        ROTATION_LERP_FACTOR = config.getDoubleElse("npc.rotation.speed", 0.25);
        MIN_DISTANCE = config.getDoubleElse("npc.new-mode.min-distance", 1.5);
    }

    public static boolean addRemote(Check check) {
        remoteChecks.add(check);
        return true;
    }

    private static void detectVersion() {
        if (versionDetected) return;
        try {
            version = PacketEvents.getAPI().getServerManager().getVersion();
            isModernVersion = version.isNewerThanOrEquals(ServerVersion.V_1_19_3);
            versionDetected = true;
        } catch (Exception e) {
            versionDetected = false;
        }
    }

    private static boolean isPlayerOnline(String name) {
        try {
            for (GrimPlayer gp : GrimAPI.INSTANCE.getPlayerDataManager().getEntries()) {
                if (gp.user.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static void spawnNpcFor(@NotNull GrimPlayer player, String name, UUID uuid, long livetime) {
        if (!enabled) return;

        if (!versionDetected) {
            detectVersion();
        }

        UUID playerId = player.user.getUUID();
        long now = System.currentTimeMillis();

        if (npcMap.containsKey(playerId)) {
            npcMap.get(playerId).lastUsed = now;
            return;
        }

        int entityId = generateUniqueEntityId();
        Vector3d initialPos = getBehindPlayerPosition(player);

        try {
            GrimPlayer rgp = PlayerArmor.getRandomPlayer();
            boolean shouldManageTab = !isPlayerOnline(name);

            if (shouldManageTab) {
                addPlayerToTab(player, uuid, name);
            }

            TrackedNpc trackedNpc = new TrackedNpc(entityId, uuid, name, now, livetime, shouldManageTab);
            trackedNpc.owner = player;
            trackedNpc.copying = rgp;

            trackedNpc.currentPosition = initialPos.clone();

            trackedNpc.lastRelX = trackedNpc.currentPosition.x;
            trackedNpc.lastRelY = trackedNpc.currentPosition.y;
            trackedNpc.lastRelZ = trackedNpc.currentPosition.z;

            trackedNpc.targetPosition = initialPos.clone();
            trackedNpc.currentYaw = player.yaw;
            trackedNpc.targetYaw = player.yaw;
            trackedNpc.spawnTaskPending = true;
            npcMap.put(playerId, trackedNpc);

        } catch (Exception e) {
        }
    }

    public static void handleMovementTick(GrimPlayer player) {
        if (!enabled) return;

        UUID playerId = player.user.getUUID();
        TrackedNpc npc = npcMap.get(playerId);
        if (npc == null) return;

        if (npc.spawnTaskPending) {
            Location spawnLoc = new Location(npc.currentPosition.x, npc.currentPosition.y, npc.currentPosition.z, npc.currentYaw, 0);
            spawnPlayerEntity(player, npc.entityId, npc.uuid, spawnLoc);
            rotateHead(player, npc.entityId, spawnLoc);
            sendNpcMetadata(player, npc, npc.entityId, 20.0f);
            sendVirtualizedArmor(player, npc, npc.entityId);
            npc.spawnTaskPending = false;
            return;
        }

        if (npc.ticksSinceLastUpdate >= updateTicks) {
            long now = System.currentTimeMillis();
            if (now - npc.lastUsed > npc.liveTime) {
                destroyNpc(player, npc);
                npcMap.remove(playerId);
                return;
            }

            updateNpcState(player, npc);
            npc.ticksSinceLastUpdate = 0;
        } else {
            npc.ticksSinceLastUpdate++;
        }
    }

    private static void sendAttributes(GrimPlayer player, TrackedNpc npc) {
        try {
            GrimPlayer sourcePlayer = npc.copying;
            if (sourcePlayer != null && sourcePlayer.lastAttributes != null) {
                WrapperPlayServerUpdateAttributes packet = new WrapperPlayServerUpdateAttributes(
                        npc.entityId,
                        sourcePlayer.lastAttributes
                );
                sendPacketSafely(player, packet);
            }
        } catch (Exception e) {
        }
    }

    private static void sendVirtualizedArmor(@NotNull GrimPlayer player, TrackedNpc npc, int entityId) {
        try {
            List<Equipment> equipment = PlayerArmor.copyRandomArmor(npc);

            WrapperPlayServerEntityEquipment packet = new WrapperPlayServerEntityEquipment(entityId, equipment);
            sendPacketSafely(player, packet);

            WrapperPlayServerUpdateEntityNBT packet1 = new WrapperPlayServerUpdateEntityNBT(entityId, toNBT(equipment));
            sendPacketSafely(player, packet1);
        } catch (Exception e) {
            try {
                RandomArmorGenerator.sendRandomArmor(player, entityId);
            } catch (Exception ignored) {
            }
        }
    }

    private static ItemStack createVirtualizedArmor(com.github.retrooper.packetevents.protocol.item.type.ItemType material, Random rand) {
        ItemStack item = ItemStack.builder().type(material).amount(1).build();
        try {
            NBTCompound nbt = new NBTCompound();
            nbt.setTag("RepairCost", new NBTInt(rand.nextInt(10) + 1));
            if (rand.nextInt(100) < 20) nbt.setTag("HideFlags", new NBTInt(rand.nextInt(127) + 1));
            if (rand.nextInt(100) < 25) nbt.setTag("Unbreakable", new NBTByte((byte) 1));
            NBTList<NBTCompound> enchantments = new NBTList<>(NBTType.COMPOUND);
            if (rand.nextInt(100) < 75)
                enchantments.addTag(createEnchantment("minecraft:protection", rand.nextInt(4) + 1));
            if (rand.nextInt(100) < 55)
                enchantments.addTag(createEnchantment("minecraft:unbreaking", rand.nextInt(3) + 1));
            if (rand.nextInt(100) < 45)
                enchantments.addTag(createEnchantment("minecraft:mending", 1));
            if (!enchantments.getTags().isEmpty()) nbt.setTag("Enchantments", enchantments);
            item.setNBT(nbt);
        } catch (Exception ignored) {
        }
        return item;
    }

    private static ItemStack createVirtualizedWeapon(com.github.retrooper.packetevents.protocol.item.type.ItemType material, Random rand) {
        ItemStack item = ItemStack.builder().type(material).amount(1).build();
        try {
            NBTCompound nbt = new NBTCompound();
            nbt.setTag("RepairCost", new NBTInt(rand.nextInt(10) + 1));
            if (rand.nextInt(100) < 20) nbt.setTag("HideFlags", new NBTInt(rand.nextInt(127) + 1));
            if (rand.nextInt(100) < 25) nbt.setTag("Unbreakable", new NBTByte((byte) 1));
            NBTList<NBTCompound> enchantments = new NBTList<>(NBTType.COMPOUND);
            if (rand.nextInt(100) < 70)
                enchantments.addTag(createEnchantment("minecraft:sharpness", rand.nextInt(5) + 1));
            if (rand.nextInt(100) < 50)
                enchantments.addTag(createEnchantment("minecraft:unbreaking", rand.nextInt(3) + 1));
            if (rand.nextInt(100) < 40)
                enchantments.addTag(createEnchantment("minecraft:mending", 1));
            if (!enchantments.getTags().isEmpty()) nbt.setTag("Enchantments", enchantments);
            item.setNBT(nbt);
        } catch (Exception ignored) {
        }
        return item;
    }

    private static NBTCompound createEnchantment(String id, int level) {
        NBTCompound enchant = new NBTCompound();
        enchant.setTag("id", new NBTString(id));
        enchant.setTag("lvl", new NBTShort((short) level));
        return enchant;
    }

    private static void sendNpcMetadata(@NotNull GrimPlayer player, TrackedNpc npc, int entityId, float health) {
        try {
            List<EntityData<?>> metadata = new ArrayList<>();

            int healthIdx = MetadataIndex.HEALTH;
            int skinIdx = isModernVersion ? 17 : 16;

            metadata.add(new EntityData<>(0, EntityDataTypes.BYTE, (byte) 0));
            metadata.add(new EntityData<>(healthIdx, EntityDataTypes.FLOAT, health));
            metadata.add(new EntityData<>(skinIdx, EntityDataTypes.BYTE, (byte) 127));

            WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(entityId, metadata);
            sendPacketSafely(player, packet);
        } catch (Exception ignored) {}
    }

    private static void addPlayerToTab(@NotNull GrimPlayer player, UUID uuid, String name) {
        UserProfile profile = new UserProfile(uuid, name);

        if (isModernVersion) {
            EnumSet<WrapperPlayServerPlayerInfoUpdate.Action> actions = EnumSet.of(
                    WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER,
                    WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LISTED,
                    WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LATENCY,
                    WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_DISPLAY_NAME
            );

            WrapperPlayServerPlayerInfoUpdate.PlayerInfo data = new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                    profile,
                    true,
                    50,
                    GameMode.SURVIVAL,
                    Component.text(name),
                    null
            );

            sendPacketSafely(player, new WrapperPlayServerPlayerInfoUpdate(actions, Collections.singletonList(data)));
        } else {
            WrapperPlayServerPlayerInfo.PlayerData data = new WrapperPlayServerPlayerInfo.PlayerData(
                    Component.text(name), profile, GameMode.SURVIVAL, 50);
            sendPacketSafely(player, new WrapperPlayServerPlayerInfo(WrapperPlayServerPlayerInfo.Action.ADD_PLAYER, data));
        }
    }

    private static void removeNpcFromTab(@NotNull GrimPlayer player, @NotNull TrackedNpc npc) {
        if (isModernVersion) {
            sendPacketSafely(player, new WrapperPlayServerPlayerInfoRemove(npc.uuid));
        } else {
            UserProfile profile = new UserProfile(npc.uuid, npc.name);
            WrapperPlayServerPlayerInfo.PlayerData data = new WrapperPlayServerPlayerInfo.PlayerData(Component.text(npc.name), profile, GameMode.SURVIVAL, 0);
            sendPacketSafely(player, new WrapperPlayServerPlayerInfo(WrapperPlayServerPlayerInfo.Action.REMOVE_PLAYER, data));
        }
    }

    private static void spawnPlayerEntity(@NotNull GrimPlayer player, int entityId, UUID uuid, Location loc) {
        detectVersion();
        if (version.isNewerThanOrEquals(ServerVersion.V_1_20_2)) {
            WrapperPlayServerSpawnEntity spawn = new WrapperPlayServerSpawnEntity(
                    entityId,
                    Optional.of(uuid),
                    com.github.retrooper.packetevents.protocol.entity.type.EntityTypes.PLAYER,
                    loc.getPosition(),
                    loc.getPitch(),
                    loc.getYaw(),
                    loc.getYaw(),
                    0,
                    Optional.empty()
            );
            sendPacketSafely(player, spawn);
        } else {
            WrapperPlayServerSpawnPlayer spawn = new WrapperPlayServerSpawnPlayer(
                    entityId,
                    uuid,
                    loc
            );
            sendPacketSafely(player, spawn);
        }
    }

    private static void rotateHead(@NotNull GrimPlayer player, int entityId, Location loc) {
        WrapperPlayServerEntityHeadLook head = new WrapperPlayServerEntityHeadLook(
                entityId,
                loc.getYaw()
        );
        sendPacketSafely(player, head);
    }

    private static int generateUniqueEntityId() {
        int entityId;
        do {
            entityId = ThreadLocalRandom.current().nextInt(900000) + 100000;
        } while (isEntityIdInUse(entityId));
        return entityId;
    }

    private static boolean isEntityIdInUse(int entityId) {
        return npcMap.values().stream().anyMatch(npc -> npc.entityId == entityId);
    }

    public static void destroyNpc(@NotNull GrimPlayer player, @NotNull TrackedNpc npc) {
        try {
            if (npc.shouldManageTab) {
                removeNpcFromTab(player, npc);
            }
            WrapperPlayServerDestroyEntities destroy = new WrapperPlayServerDestroyEntities(npc.entityId);
            sendPacketSafely(player, destroy);
        } catch (Exception ignored) {
        }
    }

    private static void handShake(@NotNull GrimPlayer player, int entityId, int animationType) {
        try {
            WrapperPlayServerEntityAnimation.EntityAnimationType type =
                    WrapperPlayServerEntityAnimation.EntityAnimationType.values()[animationType];
            WrapperPlayServerEntityAnimation packet = new WrapperPlayServerEntityAnimation(entityId, type);
            sendPacketSafely(player, packet);
        } catch (Exception ignored) {
        }
    }

    private static void updateNpcState(@NotNull GrimPlayer player, @NotNull TrackedNpc npc) {
        if (smoothMode == SmoothMode.NEW) {
            updateNpcStateNew(player, npc);
        } else {
            updateNpcStateOld(player, npc);
        }
    }

    private static void updateNpcStateNew(@NotNull GrimPlayer player, @NotNull TrackedNpc npc) {
        Vector3d playerEyePos = new Vector3d(player.x, player.y + 1.6, player.z);
        npc.targetPosition = getBehindPlayerPosition(player);
        npc.targetYaw = calculateYaw(npc.currentPosition, playerEyePos);

        npc.currentPosition = lerpVector(npc.currentPosition, npc.targetPosition, POSITION_LERP_FACTOR);
        npc.currentYaw = lerpRotation(npc.currentYaw, npc.targetYaw, ROTATION_LERP_FACTOR);

        long now = System.currentTimeMillis();
        if (handShake_Enabled) {
            long random = ThreadLocalRandom.current().nextInt(handShake_add);
            if (now - npc.handShake > (handShake_diff + random)) {
                handShake(player, npc.entityId, 0);
                npc.handShake = now;
            }
        }

        Location currentLoc = new Location(npc.currentPosition.x, npc.currentPosition.y, npc.currentPosition.z, npc.currentYaw, 0);
        sendRelativeMovePacket(player, npc, currentLoc);
        rotateHead(player, npc.entityId, currentLoc);
    }

    private static void updateNpcStateOld(@NotNull GrimPlayer player, @NotNull TrackedNpc npc) {
        Location newLoc = getBehindPlayerLocation(player);
        Location oldLoc = new Location(npc.currentPosition.x, npc.currentPosition.y, npc.currentPosition.z, npc.currentYaw, 0);
        long now = System.currentTimeMillis();

        if (shouldUpdateLocation(oldLoc, newLoc)) {
            if (handShake_Enabled) {
                long random = ThreadLocalRandom.current().nextInt(handShake_add);
                if (now - npc.handShake > (handShake_diff + random)) {
                    handShake(player, npc.entityId, 0);
                    npc.handShake = now;
                }
            }
            sendTeleportPacket(player, npc, newLoc);
            npc.currentPosition.x = newLoc.getX();
            npc.currentPosition.y = newLoc.getY();
            npc.currentPosition.z = newLoc.getZ();
            npc.currentYaw = newLoc.getYaw();
        }
    }

    private static float lerpRotation(float current, float target, double factor) {
        float diff = target - current;
        if (diff > 180) diff -= 360;
        if (diff < -180) diff += 360;
        return current + (float) (diff * factor);
    }

    private static Vector3d lerpVector(Vector3d current, Vector3d target, double factor) {
        double x = current.x + (target.x - current.x) * factor;
        double y = current.y + (target.y - current.y) * factor;
        double z = current.z + (target.z - current.z) * factor;
        return new Vector3d(x, y, z);
    }

    private static float calculateYaw(Vector3d from, Vector3d to) {
        double deltaX = to.x - from.x;
        double deltaZ = to.z - from.z;
        return (float) Math.toDegrees(Math.atan2(deltaX, -deltaZ));
    }

    public static void sendPacketSafely(@NotNull GrimPlayer player, PacketWrapper<?> packet) {
        if (player.user.getChannel() == null) return;
        try {
            player.user.sendPacket(packet);
        } catch (Exception e) {
        }
    }

    private static Location getBehindPlayerLocation(@NotNull GrimPlayer player) {
        return getBehindPlayerLocation(player, DEFAULT_DISTANCE);
    }

    private static Location getBehindPlayerLocation(@NotNull GrimPlayer player, double distance) {
        double x = player.x;
        double y = player.y;
        double z = player.z;
        float yaw = player.yaw;

        double angle = MIN_ANGLE_DEGREES + (MAX_ANGLE_DEGREES - MIN_ANGLE_DEGREES) / 2;
        double radianYaw = Math.toRadians(yaw + 180 + angle);

        double offsetX = -Math.sin(radianYaw) * distance;
        double offsetZ = Math.cos(radianYaw) * distance;

        if (strafe) {
            offsetX += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.5;
            y += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.75;
            offsetZ += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.5;
        } else {
            y += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.75;
        }

        return new Location(x + offsetX, y, z + offsetZ, yaw, 0);
    }

    private static Vector3d getBehindPlayerPosition(@NotNull GrimPlayer player) {
        return getBehindPlayerPosition(player, DEFAULT_DISTANCE);
    }

    private static Vector3d getBehindPlayerPosition(@NotNull GrimPlayer player, double distance) {
        double x = player.x;
        double y = player.y;
        double z = player.z;
        float yaw = player.yaw;

        double angle = MIN_ANGLE_DEGREES + (MAX_ANGLE_DEGREES - MIN_ANGLE_DEGREES) / 2;
        double radianYaw = Math.toRadians(yaw + 180 + angle);

        double offsetX = -Math.sin(radianYaw) * distance;
        double offsetZ = Math.cos(radianYaw) * distance;

        if (strafe) {
            offsetX += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.5;
            y += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.75;
            offsetZ += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.5;
        } else {
            y += (ThreadLocalRandom.current().nextDouble() - 0.5) * 0.75;
        }

        return new Vector3d(x + offsetX, y, z + offsetZ);
    }

    private static boolean shouldUpdateLocation(Location oldLoc, Location newLoc) {
        if (oldLoc == null) return true;
        return Math.abs(newLoc.getX() - oldLoc.getX()) > 0.15 ||
                Math.abs(newLoc.getY() - oldLoc.getY()) > 0.15 ||
                Math.abs(newLoc.getZ() - oldLoc.getZ()) > 0.15 ||
                Math.abs(angleDifference(newLoc.getYaw(), oldLoc.getYaw())) > 10;
    }

    private static float angleDifference(float angle1, float angle2) {
        float diff = Math.abs(angle1 - angle2);
        return Math.min(diff, 360 - diff);
    }

    public static void removeNpcFor(@NotNull GrimPlayer player) {
        UUID playerId = player.user.getUUID();
        TrackedNpc npc = npcMap.remove(playerId);
        if (npc != null) {
            destroyNpc(player, npc);
        }
    }

    public static boolean hasActiveNpc(@NotNull GrimPlayer player) {
        return npcMap.containsKey(player.user.getUUID());
    }

    public static void refreshNpc(@NotNull GrimPlayer player) {
        if (hasActiveNpc(player)) {
            npcMap.get(player.user.getUUID()).lastUsed = System.currentTimeMillis();
        }
    }

    public static void clearAllNpcs() {
        for (Map.Entry<UUID, TrackedNpc> entry : npcMap.entrySet()) {
            GrimPlayer player = GrimAPI.INSTANCE.getPlayerDataManager().getPlayer(entry.getKey());
            if (player != null && player.user.getChannel() != null) {
                destroyNpc(player, entry.getValue());
            }
        }
        npcMap.clear();
    }

    public static boolean isNpc(@NotNull GrimPlayer player, int entityId) {
        TrackedNpc npc = npcMap.get(player.user.getUUID());
        return npc != null && npc.entityId == entityId;
    }

    private static void sendRelativeMovePacket(@NotNull GrimPlayer player, @NotNull TrackedNpc npc, Location loc) {
        double deltaX = (loc.getX() - npc.lastRelX);
        double deltaY = (loc.getY() - npc.lastRelY);
        double deltaZ = (loc.getZ() - npc.lastRelZ);

        boolean onGround = player.lastOnGround;

        if (Math.abs(deltaX) < Short.MAX_VALUE && Math.abs(deltaY) < Short.MAX_VALUE && Math.abs(deltaZ) < Short.MAX_VALUE) {
            WrapperPlayServerEntityRelativeMove move = new WrapperPlayServerEntityRelativeMove(npc.entityId, deltaX, deltaY, deltaZ, onGround);
            sendPacketSafely(player, move);
        } else {
            WrapperPlayServerEntityTeleport tp = new WrapperPlayServerEntityTeleport(
                    npc.entityId,
                    loc.getPosition(),
                    loc.getYaw(),
                    loc.getPitch(),
                    onGround
            );
            sendPacketSafely(player, tp);
        }

        npc.lastRelX = loc.getX();
        npc.lastRelY = loc.getY();
        npc.lastRelZ = loc.getZ();
    }

    private static void sendTeleportPacket(@NotNull GrimPlayer player, @NotNull TrackedNpc npc, Location loc) {
        WrapperPlayServerEntityTeleport tp = new WrapperPlayServerEntityTeleport(
                npc.entityId,
                loc.getPosition(),
                loc.getYaw(),
                loc.getPitch(),
                false
        );
        sendPacketSafely(player, tp);
        rotateHead(player, npc.entityId, loc);
    }

    public static class TrackedNpc {
        public final int entityId;
        public final UUID uuid;
        public final String name;
        public volatile long lastUsed;
        public Vector3d currentPosition;
        public Vector3d targetPosition;
        public float currentYaw;
        public float targetYaw;
        public long handShake = 0;
        public final long liveTime;
        public boolean spawnTaskPending = false;
        public int ticksSinceLastUpdate = 0;
        public final boolean shouldManageTab;
        public double lastRelX = 0;
        public double lastRelY = 0;
        public double lastRelZ = 0;
        public float health = 20.0f;
        public long lastAttack = Long.MAX_VALUE;
        public long lastAttackDiff = Long.MAX_VALUE;
        public GrimPlayer owner, copying;

        public TrackedNpc(int entityId, UUID uuid, String name, long lastUsed, long liveTime, boolean shouldManageTab) {
            this.entityId = entityId;
            this.uuid = uuid;
            this.name = name;
            this.lastUsed = lastUsed;
            this.shouldManageTab = shouldManageTab;
            boolean validLiveTimeDown = liveTime > 0;
            boolean validLiveTimeUp = liveTime <= MAX_TIMEOUT_MS;
            if (!validLiveTimeDown) {
                this.liveTime = TIMEOUT_MS;
            } else if (!validLiveTimeUp) {
                this.liveTime = MAX_TIMEOUT_MS;
            } else {
                this.liveTime = liveTime;
            }
        }
    }

    public static class Vector3d {
        public double x, y, z;

        public Vector3d(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Vector3d clone() {
            return new Vector3d(x, y, z);
        }
    }

    public enum SmoothMode {
        OLD, NEW
    }
}
