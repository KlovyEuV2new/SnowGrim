package ac.grim.grimac.utils.raytrace;

import com.github.retrooper.packetevents.protocol.world.states.type.StateType;
import com.github.retrooper.packetevents.protocol.world.states.type.StateTypes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class StateBoxes {

    private static final Map<StateType, Box> BOXES = new ConcurrentHashMap<>();
    private static final Box FULL = new Box(0, 0, 0, 1, 1, 1);
    private static final Box EMPTY = new Box(0, 0, 0, 0, 0, 0);

    static {
        BOXES.put(StateTypes.AIR, EMPTY);
        BOXES.put(StateTypes.CAVE_AIR, EMPTY);
        BOXES.put(StateTypes.VOID_AIR, EMPTY);

        BOXES.put(StateTypes.STONE, FULL);
        BOXES.put(StateTypes.GRANITE, FULL);
        BOXES.put(StateTypes.POLISHED_GRANITE, FULL);
        BOXES.put(StateTypes.DIORITE, FULL);
        BOXES.put(StateTypes.POLISHED_DIORITE, FULL);
        BOXES.put(StateTypes.ANDESITE, FULL);
        BOXES.put(StateTypes.POLISHED_ANDESITE, FULL);
        BOXES.put(StateTypes.DEEPSLATE, FULL);
        BOXES.put(StateTypes.COBBLED_DEEPSLATE, FULL);
        BOXES.put(StateTypes.POLISHED_DEEPSLATE, FULL);
        BOXES.put(StateTypes.CALCITE, FULL);
        BOXES.put(StateTypes.TUFF, FULL);
        BOXES.put(StateTypes.DRIPSTONE_BLOCK, FULL);
        BOXES.put(StateTypes.GRASS_BLOCK, FULL);
        BOXES.put(StateTypes.DIRT, FULL);
        BOXES.put(StateTypes.COARSE_DIRT, FULL);
        BOXES.put(StateTypes.PODZOL, FULL);
        BOXES.put(StateTypes.ROOTED_DIRT, FULL);
        BOXES.put(StateTypes.MUD, FULL);
        BOXES.put(StateTypes.CRIMSON_NYLIUM, FULL);
        BOXES.put(StateTypes.WARPED_NYLIUM, FULL);
        BOXES.put(StateTypes.COBBLESTONE, FULL);
        BOXES.put(StateTypes.MOSSY_COBBLESTONE, FULL);
        BOXES.put(StateTypes.OAK_PLANKS, FULL);
        BOXES.put(StateTypes.SPRUCE_PLANKS, FULL);
        BOXES.put(StateTypes.BIRCH_PLANKS, FULL);
        BOXES.put(StateTypes.JUNGLE_PLANKS, FULL);
        BOXES.put(StateTypes.ACACIA_PLANKS, FULL);
        BOXES.put(StateTypes.CHERRY_PLANKS, FULL);
        BOXES.put(StateTypes.DARK_OAK_PLANKS, FULL);
        BOXES.put(StateTypes.MANGROVE_PLANKS, FULL);
        BOXES.put(StateTypes.BAMBOO_PLANKS, FULL);
        BOXES.put(StateTypes.PALE_OAK_PLANKS, FULL);
        BOXES.put(StateTypes.CRIMSON_PLANKS, FULL);
        BOXES.put(StateTypes.WARPED_PLANKS, FULL);
        BOXES.put(StateTypes.BAMBOO_MOSAIC, FULL);

        BOXES.put(StateTypes.COAL_ORE, FULL);
        BOXES.put(StateTypes.DEEPSLATE_COAL_ORE, FULL);
        BOXES.put(StateTypes.IRON_ORE, FULL);
        BOXES.put(StateTypes.DEEPSLATE_IRON_ORE, FULL);
        BOXES.put(StateTypes.COPPER_ORE, FULL);
        BOXES.put(StateTypes.DEEPSLATE_COPPER_ORE, FULL);
        BOXES.put(StateTypes.GOLD_ORE, FULL);
        BOXES.put(StateTypes.DEEPSLATE_GOLD_ORE, FULL);
        BOXES.put(StateTypes.REDSTONE_ORE, FULL);
        BOXES.put(StateTypes.DEEPSLATE_REDSTONE_ORE, FULL);
        BOXES.put(StateTypes.EMERALD_ORE, FULL);
        BOXES.put(StateTypes.DEEPSLATE_EMERALD_ORE, FULL);
        BOXES.put(StateTypes.LAPIS_ORE, FULL);
        BOXES.put(StateTypes.DEEPSLATE_LAPIS_ORE, FULL);
        BOXES.put(StateTypes.DIAMOND_ORE, FULL);
        BOXES.put(StateTypes.DEEPSLATE_DIAMOND_ORE, FULL);
        BOXES.put(StateTypes.NETHER_GOLD_ORE, FULL);
        BOXES.put(StateTypes.NETHER_QUARTZ_ORE, FULL);
        BOXES.put(StateTypes.ANCIENT_DEBRIS, FULL);

        BOXES.put(StateTypes.COAL_BLOCK, FULL);
        BOXES.put(StateTypes.RAW_IRON_BLOCK, FULL);
        BOXES.put(StateTypes.RAW_COPPER_BLOCK, FULL);
        BOXES.put(StateTypes.RAW_GOLD_BLOCK, FULL);
        BOXES.put(StateTypes.AMETHYST_BLOCK, FULL);
        BOXES.put(StateTypes.BUDDING_AMETHYST, FULL);
        BOXES.put(StateTypes.IRON_BLOCK, FULL);
        BOXES.put(StateTypes.COPPER_BLOCK, FULL);
        BOXES.put(StateTypes.GOLD_BLOCK, FULL);
        BOXES.put(StateTypes.DIAMOND_BLOCK, FULL);
        BOXES.put(StateTypes.NETHERITE_BLOCK, FULL);
        BOXES.put(StateTypes.EMERALD_BLOCK, FULL);
        BOXES.put(StateTypes.LAPIS_BLOCK, FULL);
        BOXES.put(StateTypes.REDSTONE_BLOCK, FULL);

        BOXES.put(StateTypes.EXPOSED_COPPER, FULL);
        BOXES.put(StateTypes.WEATHERED_COPPER, FULL);
        BOXES.put(StateTypes.OXIDIZED_COPPER, FULL);
        BOXES.put(StateTypes.CUT_COPPER, FULL);
        BOXES.put(StateTypes.EXPOSED_CUT_COPPER, FULL);
        BOXES.put(StateTypes.WEATHERED_CUT_COPPER, FULL);
        BOXES.put(StateTypes.OXIDIZED_CUT_COPPER, FULL);
        BOXES.put(StateTypes.WAXED_COPPER_BLOCK, FULL);
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER, FULL);
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER, FULL);
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER, FULL);
        BOXES.put(StateTypes.WAXED_CUT_COPPER, FULL);
        BOXES.put(StateTypes.WAXED_EXPOSED_CUT_COPPER, FULL);
        BOXES.put(StateTypes.WAXED_WEATHERED_CUT_COPPER, FULL);
        BOXES.put(StateTypes.WAXED_OXIDIZED_CUT_COPPER, FULL);

        BOXES.put(StateTypes.OAK_LOG, FULL);
        BOXES.put(StateTypes.SPRUCE_LOG, FULL);
        BOXES.put(StateTypes.BIRCH_LOG, FULL);
        BOXES.put(StateTypes.JUNGLE_LOG, FULL);
        BOXES.put(StateTypes.ACACIA_LOG, FULL);
        BOXES.put(StateTypes.CHERRY_LOG, FULL);
        BOXES.put(StateTypes.DARK_OAK_LOG, FULL);
        BOXES.put(StateTypes.MANGROVE_LOG, FULL);
        BOXES.put(StateTypes.PALE_OAK_LOG, FULL);
        BOXES.put(StateTypes.CRIMSON_STEM, FULL);
        BOXES.put(StateTypes.WARPED_STEM, FULL);
        BOXES.put(StateTypes.BAMBOO_BLOCK, FULL);

        BOXES.put(StateTypes.STRIPPED_OAK_LOG, FULL);
        BOXES.put(StateTypes.STRIPPED_SPRUCE_LOG, FULL);
        BOXES.put(StateTypes.STRIPPED_BIRCH_LOG, FULL);
        BOXES.put(StateTypes.STRIPPED_JUNGLE_LOG, FULL);
        BOXES.put(StateTypes.STRIPPED_ACACIA_LOG, FULL);
        BOXES.put(StateTypes.STRIPPED_CHERRY_LOG, FULL);
        BOXES.put(StateTypes.STRIPPED_DARK_OAK_LOG, FULL);
        BOXES.put(StateTypes.STRIPPED_MANGROVE_LOG, FULL);
        BOXES.put(StateTypes.STRIPPED_PALE_OAK_LOG, FULL);
        BOXES.put(StateTypes.STRIPPED_CRIMSON_STEM, FULL);
        BOXES.put(StateTypes.STRIPPED_WARPED_STEM, FULL);
        BOXES.put(StateTypes.STRIPPED_BAMBOO_BLOCK, FULL);

        BOXES.put(StateTypes.OAK_WOOD, FULL);
        BOXES.put(StateTypes.SPRUCE_WOOD, FULL);
        BOXES.put(StateTypes.BIRCH_WOOD, FULL);
        BOXES.put(StateTypes.JUNGLE_WOOD, FULL);
        BOXES.put(StateTypes.ACACIA_WOOD, FULL);
        BOXES.put(StateTypes.CHERRY_WOOD, FULL);
        BOXES.put(StateTypes.DARK_OAK_WOOD, FULL);
        BOXES.put(StateTypes.MANGROVE_WOOD, FULL);
        BOXES.put(StateTypes.PALE_OAK_WOOD, FULL);
        BOXES.put(StateTypes.CRIMSON_HYPHAE, FULL);
        BOXES.put(StateTypes.WARPED_HYPHAE, FULL);

        BOXES.put(StateTypes.STRIPPED_OAK_WOOD, FULL);
        BOXES.put(StateTypes.STRIPPED_SPRUCE_WOOD, FULL);
        BOXES.put(StateTypes.STRIPPED_BIRCH_WOOD, FULL);
        BOXES.put(StateTypes.STRIPPED_JUNGLE_WOOD, FULL);
        BOXES.put(StateTypes.STRIPPED_ACACIA_WOOD, FULL);
        BOXES.put(StateTypes.STRIPPED_CHERRY_WOOD, FULL);
        BOXES.put(StateTypes.STRIPPED_DARK_OAK_WOOD, FULL);
        BOXES.put(StateTypes.STRIPPED_MANGROVE_WOOD, FULL);
        BOXES.put(StateTypes.STRIPPED_PALE_OAK_WOOD, FULL);
        BOXES.put(StateTypes.STRIPPED_CRIMSON_HYPHAE, FULL);
        BOXES.put(StateTypes.STRIPPED_WARPED_HYPHAE, FULL);

        BOXES.put(StateTypes.SPONGE, FULL);
        BOXES.put(StateTypes.WET_SPONGE, FULL);
        BOXES.put(StateTypes.GLASS, FULL);
        BOXES.put(StateTypes.TINTED_GLASS, FULL);
        BOXES.put(StateTypes.SANDSTONE, FULL);
        BOXES.put(StateTypes.CHISELED_SANDSTONE, FULL);
        BOXES.put(StateTypes.CUT_SANDSTONE, FULL);
        BOXES.put(StateTypes.SMOOTH_SANDSTONE, FULL);

        BOXES.put(StateTypes.WHITE_WOOL, FULL);
        BOXES.put(StateTypes.ORANGE_WOOL, FULL);
        BOXES.put(StateTypes.MAGENTA_WOOL, FULL);
        BOXES.put(StateTypes.LIGHT_BLUE_WOOL, FULL);
        BOXES.put(StateTypes.YELLOW_WOOL, FULL);
        BOXES.put(StateTypes.LIME_WOOL, FULL);
        BOXES.put(StateTypes.PINK_WOOL, FULL);
        BOXES.put(StateTypes.GRAY_WOOL, FULL);
        BOXES.put(StateTypes.LIGHT_GRAY_WOOL, FULL);
        BOXES.put(StateTypes.CYAN_WOOL, FULL);
        BOXES.put(StateTypes.PURPLE_WOOL, FULL);
        BOXES.put(StateTypes.BLUE_WOOL, FULL);
        BOXES.put(StateTypes.BROWN_WOOL, FULL);
        BOXES.put(StateTypes.GREEN_WOOL, FULL);
        BOXES.put(StateTypes.RED_WOOL, FULL);
        BOXES.put(StateTypes.BLACK_WOOL, FULL);

        BOXES.put(StateTypes.BRICKS, FULL);
        BOXES.put(StateTypes.BOOKSHELF, FULL);
        BOXES.put(StateTypes.MOSSY_COBBLESTONE, FULL);
        BOXES.put(StateTypes.OBSIDIAN, FULL);
        BOXES.put(StateTypes.CRYING_OBSIDIAN, FULL);
        BOXES.put(StateTypes.PURPUR_BLOCK, FULL);
        BOXES.put(StateTypes.PURPUR_PILLAR, FULL);
        BOXES.put(StateTypes.SPAWNER, FULL);
        BOXES.put(StateTypes.CRAFTING_TABLE, FULL);
        BOXES.put(StateTypes.FARMLAND, FULL);
        BOXES.put(StateTypes.FURNACE, FULL);
        BOXES.put(StateTypes.BLAST_FURNACE, FULL);
        BOXES.put(StateTypes.SMOKER, FULL);
        BOXES.put(StateTypes.SNOW_BLOCK, FULL);
        BOXES.put(StateTypes.ICE, FULL);
        BOXES.put(StateTypes.PACKED_ICE, FULL);
        BOXES.put(StateTypes.BLUE_ICE, FULL);
        BOXES.put(StateTypes.CLAY, FULL);
        BOXES.put(StateTypes.JUKEBOX, FULL);
        BOXES.put(StateTypes.PUMPKIN, FULL);
        BOXES.put(StateTypes.CARVED_PUMPKIN, FULL);
        BOXES.put(StateTypes.JACK_O_LANTERN, FULL);
        BOXES.put(StateTypes.NETHERRACK, FULL);
        BOXES.put(StateTypes.SOUL_SAND, FULL);
        BOXES.put(StateTypes.SOUL_SOIL, FULL);
        BOXES.put(StateTypes.BASALT, FULL);
        BOXES.put(StateTypes.POLISHED_BASALT, FULL);
        BOXES.put(StateTypes.SMOOTH_BASALT, FULL);
        BOXES.put(StateTypes.GLOWSTONE, FULL);
        BOXES.put(StateTypes.STONE_BRICKS, FULL);
        BOXES.put(StateTypes.MOSSY_STONE_BRICKS, FULL);
        BOXES.put(StateTypes.CRACKED_STONE_BRICKS, FULL);
        BOXES.put(StateTypes.CHISELED_STONE_BRICKS, FULL);
        BOXES.put(StateTypes.PACKED_MUD, FULL);
        BOXES.put(StateTypes.MUD_BRICKS, FULL);
        BOXES.put(StateTypes.DEEPSLATE_BRICKS, FULL);
        BOXES.put(StateTypes.CRACKED_DEEPSLATE_BRICKS, FULL);
        BOXES.put(StateTypes.DEEPSLATE_TILES, FULL);
        BOXES.put(StateTypes.CRACKED_DEEPSLATE_TILES, FULL);
        BOXES.put(StateTypes.CHISELED_DEEPSLATE, FULL);
        BOXES.put(StateTypes.REINFORCED_DEEPSLATE, FULL);
        BOXES.put(StateTypes.BROWN_MUSHROOM_BLOCK, FULL);
        BOXES.put(StateTypes.RED_MUSHROOM_BLOCK, FULL);
        BOXES.put(StateTypes.MUSHROOM_STEM, FULL);
        BOXES.put(StateTypes.MELON, FULL);
        BOXES.put(StateTypes.MYCELIUM, FULL);
        BOXES.put(StateTypes.NETHER_BRICKS, FULL);
        BOXES.put(StateTypes.CRACKED_NETHER_BRICKS, FULL);
        BOXES.put(StateTypes.CHISELED_NETHER_BRICKS, FULL);
        BOXES.put(StateTypes.SCULK, FULL);
        BOXES.put(StateTypes.SCULK_CATALYST, FULL);
        BOXES.put(StateTypes.SCULK_SHRIEKER, FULL);
        BOXES.put(StateTypes.ENCHANTING_TABLE, FULL);
        BOXES.put(StateTypes.END_PORTAL_FRAME, FULL);
        BOXES.put(StateTypes.END_STONE, FULL);
        BOXES.put(StateTypes.END_STONE_BRICKS, FULL);
        BOXES.put(StateTypes.DRAGON_EGG, FULL);
        BOXES.put(StateTypes.ENDER_CHEST, FULL);
        BOXES.put(StateTypes.COMMAND_BLOCK, FULL);
        BOXES.put(StateTypes.REPEATING_COMMAND_BLOCK, FULL);
        BOXES.put(StateTypes.CHAIN_COMMAND_BLOCK, FULL);
        BOXES.put(StateTypes.BEACON, FULL);
        BOXES.put(StateTypes.ANVIL, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.CHIPPED_ANVIL, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.DAMAGED_ANVIL, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));

        BOXES.put(StateTypes.CHISELED_QUARTZ_BLOCK, FULL);
        BOXES.put(StateTypes.QUARTZ_BLOCK, FULL);
        BOXES.put(StateTypes.QUARTZ_BRICKS, FULL);
        BOXES.put(StateTypes.QUARTZ_PILLAR, FULL);
        BOXES.put(StateTypes.SMOOTH_QUARTZ, FULL);

        BOXES.put(StateTypes.WHITE_TERRACOTTA, FULL);
        BOXES.put(StateTypes.ORANGE_TERRACOTTA, FULL);
        BOXES.put(StateTypes.MAGENTA_TERRACOTTA, FULL);
        BOXES.put(StateTypes.LIGHT_BLUE_TERRACOTTA, FULL);
        BOXES.put(StateTypes.YELLOW_TERRACOTTA, FULL);
        BOXES.put(StateTypes.LIME_TERRACOTTA, FULL);
        BOXES.put(StateTypes.PINK_TERRACOTTA, FULL);
        BOXES.put(StateTypes.GRAY_TERRACOTTA, FULL);
        BOXES.put(StateTypes.LIGHT_GRAY_TERRACOTTA, FULL);
        BOXES.put(StateTypes.CYAN_TERRACOTTA, FULL);
        BOXES.put(StateTypes.PURPLE_TERRACOTTA, FULL);
        BOXES.put(StateTypes.BLUE_TERRACOTTA, FULL);
        BOXES.put(StateTypes.BROWN_TERRACOTTA, FULL);
        BOXES.put(StateTypes.GREEN_TERRACOTTA, FULL);
        BOXES.put(StateTypes.RED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.BLACK_TERRACOTTA, FULL);
        BOXES.put(StateTypes.TERRACOTTA, FULL);

        BOXES.put(StateTypes.WHITE_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.ORANGE_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.MAGENTA_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.LIGHT_BLUE_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.YELLOW_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.LIME_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.PINK_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.GRAY_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.LIGHT_GRAY_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.CYAN_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.PURPLE_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.BLUE_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.BROWN_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.GREEN_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.RED_GLAZED_TERRACOTTA, FULL);
        BOXES.put(StateTypes.BLACK_GLAZED_TERRACOTTA, FULL);

        BOXES.put(StateTypes.WHITE_CONCRETE, FULL);
        BOXES.put(StateTypes.ORANGE_CONCRETE, FULL);
        BOXES.put(StateTypes.MAGENTA_CONCRETE, FULL);
        BOXES.put(StateTypes.LIGHT_BLUE_CONCRETE, FULL);
        BOXES.put(StateTypes.YELLOW_CONCRETE, FULL);
        BOXES.put(StateTypes.LIME_CONCRETE, FULL);
        BOXES.put(StateTypes.PINK_CONCRETE, FULL);
        BOXES.put(StateTypes.GRAY_CONCRETE, FULL);
        BOXES.put(StateTypes.LIGHT_GRAY_CONCRETE, FULL);
        BOXES.put(StateTypes.CYAN_CONCRETE, FULL);
        BOXES.put(StateTypes.PURPLE_CONCRETE, FULL);
        BOXES.put(StateTypes.BLUE_CONCRETE, FULL);
        BOXES.put(StateTypes.BROWN_CONCRETE, FULL);
        BOXES.put(StateTypes.GREEN_CONCRETE, FULL);
        BOXES.put(StateTypes.RED_CONCRETE, FULL);
        BOXES.put(StateTypes.BLACK_CONCRETE, FULL);

        BOXES.put(StateTypes.WHITE_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.ORANGE_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.MAGENTA_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.LIGHT_BLUE_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.YELLOW_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.LIME_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.PINK_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.GRAY_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.LIGHT_GRAY_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.CYAN_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.PURPLE_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.BLUE_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.BROWN_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.GREEN_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.RED_CONCRETE_POWDER, FULL);
        BOXES.put(StateTypes.BLACK_CONCRETE_POWDER, FULL);

        BOXES.put(StateTypes.DEAD_TUBE_CORAL_BLOCK, FULL);
        BOXES.put(StateTypes.DEAD_BRAIN_CORAL_BLOCK, FULL);
        BOXES.put(StateTypes.DEAD_BUBBLE_CORAL_BLOCK, FULL);
        BOXES.put(StateTypes.DEAD_FIRE_CORAL_BLOCK, FULL);
        BOXES.put(StateTypes.DEAD_HORN_CORAL_BLOCK, FULL);
        BOXES.put(StateTypes.TUBE_CORAL_BLOCK, FULL);
        BOXES.put(StateTypes.BRAIN_CORAL_BLOCK, FULL);
        BOXES.put(StateTypes.BUBBLE_CORAL_BLOCK, FULL);
        BOXES.put(StateTypes.FIRE_CORAL_BLOCK, FULL);
        BOXES.put(StateTypes.HORN_CORAL_BLOCK, FULL);

        BOXES.put(StateTypes.SAND, FULL);
        BOXES.put(StateTypes.RED_SAND, FULL);
        BOXES.put(StateTypes.GRAVEL, FULL);
        BOXES.put(StateTypes.SUSPICIOUS_SAND, FULL);
        BOXES.put(StateTypes.SUSPICIOUS_GRAVEL, FULL);

        BOXES.put(StateTypes.SLIME_BLOCK, FULL);
        BOXES.put(StateTypes.HONEY_BLOCK, FULL);
        BOXES.put(StateTypes.OBSERVER, FULL);
        BOXES.put(StateTypes.TARGET, FULL);
        BOXES.put(StateTypes.DAYLIGHT_DETECTOR, FULL);
        BOXES.put(StateTypes.SCULK_SENSOR, FULL);
        BOXES.put(StateTypes.CALIBRATED_SCULK_SENSOR, FULL);
        BOXES.put(StateTypes.NOTE_BLOCK, FULL);
        BOXES.put(StateTypes.BARRIER, FULL);
        BOXES.put(StateTypes.LIGHT, FULL);
        BOXES.put(StateTypes.HAY_BLOCK, FULL);
        BOXES.put(StateTypes.DRIED_KELP_BLOCK, FULL);
        BOXES.put(StateTypes.SHROOMLIGHT, FULL);
        BOXES.put(StateTypes.BEE_NEST, FULL);
        BOXES.put(StateTypes.BEEHIVE, FULL);
        BOXES.put(StateTypes.HONEYCOMB_BLOCK, FULL);
        BOXES.put(StateTypes.LODESTONE, FULL);
        BOXES.put(StateTypes.BLACKSTONE, FULL);
        BOXES.put(StateTypes.GILDED_BLACKSTONE, FULL);
        BOXES.put(StateTypes.POLISHED_BLACKSTONE, FULL);
        BOXES.put(StateTypes.CHISELED_POLISHED_BLACKSTONE, FULL);
        BOXES.put(StateTypes.POLISHED_BLACKSTONE_BRICKS, FULL);
        BOXES.put(StateTypes.CRACKED_POLISHED_BLACKSTONE_BRICKS, FULL);
        BOXES.put(StateTypes.RESPAWN_ANCHOR, FULL);
        BOXES.put(StateTypes.MOSS_BLOCK, FULL);
        BOXES.put(StateTypes.PALE_MOSS_BLOCK, FULL);

        BOXES.put(StateTypes.OCHRE_FROGLIGHT, FULL);
        BOXES.put(StateTypes.VERDANT_FROGLIGHT, FULL);
        BOXES.put(StateTypes.PEARLESCENT_FROGLIGHT, FULL);
        BOXES.put(StateTypes.COPPER_BULB, FULL);
        BOXES.put(StateTypes.EXPOSED_COPPER_BULB, FULL);
        BOXES.put(StateTypes.WEATHERED_COPPER_BULB, FULL);
        BOXES.put(StateTypes.OXIDIZED_COPPER_BULB, FULL);
        BOXES.put(StateTypes.WAXED_COPPER_BULB, FULL);
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER_BULB, FULL);
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER_BULB, FULL);
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER_BULB, FULL);

        BOXES.put(StateTypes.CRAFTER, FULL);
        BOXES.put(StateTypes.TRIAL_SPAWNER, FULL);
        BOXES.put(StateTypes.VAULT, FULL);
        BOXES.put(StateTypes.HEAVY_CORE, FULL);
        BOXES.put(StateTypes.RESIN_BLOCK, FULL);
        BOXES.put(StateTypes.RESIN_BRICKS, FULL);
        BOXES.put(StateTypes.CHISELED_RESIN_BRICKS, FULL);
        BOXES.put(StateTypes.CREAKING_HEART, FULL);

        BOXES.put(StateTypes.CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));
        BOXES.put(StateTypes.TRAPPED_CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));
        BOXES.put(StateTypes.ENDER_CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));

        BOXES.put(StateTypes.SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.WHITE_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.ORANGE_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.MAGENTA_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.LIGHT_BLUE_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.YELLOW_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.LIME_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.PINK_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.GRAY_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.LIGHT_GRAY_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.CYAN_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.PURPLE_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.BLUE_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.BROWN_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.GREEN_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.RED_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.BLACK_SHULKER_BOX, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));

        BOXES.put(StateTypes.BARREL, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.LECTERN, new Box(0, 0, 0, 1, 0.75, 1));

        BOXES.put(StateTypes.CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));

        BOXES.put(StateTypes.OAK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SPRUCE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.BIRCH_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.JUNGLE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.ACACIA_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.CHERRY_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DARK_OAK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.MANGROVE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.BAMBOO_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.PALE_OAK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.BAMBOO_MOSAIC_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.CRIMSON_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.WARPED_SLAB, new Box(0, 0, 0, 1, 0.5, 1));

        BOXES.put(StateTypes.STONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SMOOTH_STONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SANDSTONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.CUT_SANDSTONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.PETRIFIED_OAK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.COBBLESTONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.STONE_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.MUD_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.NETHER_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.QUARTZ_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.RED_SANDSTONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.CUT_RED_SANDSTONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.PURPUR_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.PRISMARINE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.PRISMARINE_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DARK_PRISMARINE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SMOOTH_QUARTZ, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SMOOTH_RED_SANDSTONE, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SMOOTH_SANDSTONE, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SMOOTH_STONE, new Box(0, 0, 0, 1, 0.5, 1));

        BOXES.put(StateTypes.BLACKSTONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.POLISHED_BLACKSTONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.POLISHED_BLACKSTONE_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.COBBLED_DEEPSLATE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.POLISHED_DEEPSLATE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEEPSLATE_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEEPSLATE_TILE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.POLISHED_GRANITE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SMOOTH_RED_SANDSTONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.MOSSY_STONE_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.POLISHED_DIORITE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.MOSSY_COBBLESTONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.END_STONE_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SMOOTH_SANDSTONE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SMOOTH_QUARTZ_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.GRANITE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.ANDESITE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.RED_NETHER_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.POLISHED_ANDESITE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DIORITE_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.TUFF_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.POLISHED_TUFF_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.TUFF_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.RESIN_BRICK_SLAB, new Box(0, 0, 0, 1, 0.5, 1));

        BOXES.put(StateTypes.CUT_COPPER_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.EXPOSED_CUT_COPPER_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.WEATHERED_CUT_COPPER_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.OXIDIZED_CUT_COPPER_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.WAXED_CUT_COPPER_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.WAXED_EXPOSED_CUT_COPPER_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.WAXED_WEATHERED_CUT_COPPER_SLAB, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.WAXED_OXIDIZED_CUT_COPPER_SLAB, new Box(0, 0, 0, 1, 0.5, 1));

        BOXES.put(StateTypes.OAK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SPRUCE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BIRCH_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.JUNGLE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ACACIA_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CHERRY_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DARK_OAK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MANGROVE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PALE_OAK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BAMBOO_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BAMBOO_MOSAIC_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CRIMSON_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WARPED_STAIRS, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.COBBLESTONE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.STONE_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MUD_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.NETHER_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.QUARTZ_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SANDSTONE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.RED_SANDSTONE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PURPUR_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PRISMARINE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PRISMARINE_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DARK_PRISMARINE_STAIRS, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.POLISHED_GRANITE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SMOOTH_RED_SANDSTONE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MOSSY_STONE_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.POLISHED_DIORITE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MOSSY_COBBLESTONE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.END_STONE_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.STONE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SMOOTH_SANDSTONE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SMOOTH_QUARTZ_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.GRANITE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ANDESITE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.RED_NETHER_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.POLISHED_ANDESITE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DIORITE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BLACKSTONE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.POLISHED_BLACKSTONE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.POLISHED_BLACKSTONE_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.COBBLED_DEEPSLATE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.POLISHED_DEEPSLATE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DEEPSLATE_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DEEPSLATE_TILE_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CUT_COPPER_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.EXPOSED_CUT_COPPER_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WEATHERED_CUT_COPPER_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.OXIDIZED_CUT_COPPER_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_CUT_COPPER_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_EXPOSED_CUT_COPPER_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_WEATHERED_CUT_COPPER_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_OXIDIZED_CUT_COPPER_STAIRS, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.TUFF_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.POLISHED_TUFF_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.TUFF_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.RESIN_BRICK_STAIRS, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.OAK_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SPRUCE_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BIRCH_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.JUNGLE_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ACACIA_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CHERRY_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DARK_OAK_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MANGROVE_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PALE_OAK_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BAMBOO_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CRIMSON_FENCE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WARPED_FENCE, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.NETHER_BRICK_FENCE, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.COBBLESTONE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.MOSSY_COBBLESTONE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.PRISMARINE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.RED_SANDSTONE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.MOSSY_STONE_BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.GRANITE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.STONE_BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.MUD_BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.NETHER_BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.ANDESITE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.RED_NETHER_BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.SANDSTONE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.END_STONE_BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.DIORITE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.BLACKSTONE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.POLISHED_BLACKSTONE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.POLISHED_BLACKSTONE_BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.COBBLED_DEEPSLATE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.POLISHED_DEEPSLATE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.DEEPSLATE_BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.DEEPSLATE_TILE_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.TUFF_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.POLISHED_TUFF_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.TUFF_BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));
        BOXES.put(StateTypes.RESIN_BRICK_WALL, new Box(0, 0, 0, 1, 1.5, 1));

        BOXES.put(StateTypes.IRON_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.EXPOSED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WEATHERED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.OXIDIZED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WHITE_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ORANGE_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MAGENTA_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LIGHT_BLUE_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.YELLOW_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LIME_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PINK_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.GRAY_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LIGHT_GRAY_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CYAN_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PURPLE_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BLUE_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BROWN_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.GREEN_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.RED_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BLACK_STAINED_GLASS_PANE, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.IRON_CHAIN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.COPPER_CHAIN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.EXPOSED_COPPER_CHAIN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WEATHERED_COPPER_CHAIN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.OXIDIZED_COPPER_CHAIN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_COPPER_CHAIN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER_CHAIN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER_CHAIN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER_CHAIN, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.LADDER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SCAFFOLDING, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.LEVER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.STONE_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.POLISHED_BLACKSTONE_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.OAK_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SPRUCE_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BIRCH_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.JUNGLE_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ACACIA_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CHERRY_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DARK_OAK_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MANGROVE_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PALE_OAK_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BAMBOO_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CRIMSON_BUTTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WARPED_BUTTON, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.STONE_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.POLISHED_BLACKSTONE_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.LIGHT_WEIGHTED_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.HEAVY_WEIGHTED_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.OAK_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.SPRUCE_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.BIRCH_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.JUNGLE_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.ACACIA_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.CHERRY_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.DARK_OAK_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.MANGROVE_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.PALE_OAK_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.BAMBOO_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.CRIMSON_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.WARPED_PRESSURE_PLATE, new Box(0, 0, 0, 1, 0.0625, 1));

        BOXES.put(StateTypes.WHITE_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.ORANGE_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.MAGENTA_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.LIGHT_BLUE_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.YELLOW_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.LIME_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.PINK_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.GRAY_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.LIGHT_GRAY_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.CYAN_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.PURPLE_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.BLUE_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.BROWN_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.GREEN_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.RED_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.BLACK_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));

        BOXES.put(StateTypes.MOSS_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.PALE_MOSS_CARPET, new Box(0, 0, 0, 1, 0.0625, 1));

        BOXES.put(StateTypes.SNOW, new Box(0, 0, 0, 1, 0.125, 1));

        BOXES.put(StateTypes.TORCH, new Box(0.4, 0, 0.4, 0.6, 0.6, 0.6));
        BOXES.put(StateTypes.WALL_TORCH, new Box(0.4, 0, 0.4, 0.6, 0.6, 0.6));
        BOXES.put(StateTypes.SOUL_TORCH, new Box(0.4, 0, 0.4, 0.6, 0.6, 0.6));
        BOXES.put(StateTypes.SOUL_WALL_TORCH, new Box(0.4, 0, 0.4, 0.6, 0.6, 0.6));
        BOXES.put(StateTypes.REDSTONE_TORCH, new Box(0.4, 0, 0.4, 0.6, 0.6, 0.6));
        BOXES.put(StateTypes.REDSTONE_WALL_TORCH, new Box(0.4, 0, 0.4, 0.6, 0.6, 0.6));
        BOXES.put(StateTypes.COPPER_TORCH, new Box(0.4, 0, 0.4, 0.6, 0.6, 0.6));
        BOXES.put(StateTypes.COPPER_WALL_TORCH, new Box(0.4, 0, 0.4, 0.6, 0.6, 0.6));

        BOXES.put(StateTypes.LANTERN, new Box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125));
        BOXES.put(StateTypes.SOUL_LANTERN, new Box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125));
        BOXES.put(StateTypes.COPPER_LANTERN, new Box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125));
        BOXES.put(StateTypes.EXPOSED_COPPER_LANTERN, new Box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125));
        BOXES.put(StateTypes.WEATHERED_COPPER_LANTERN, new Box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125));
        BOXES.put(StateTypes.OXIDIZED_COPPER_LANTERN, new Box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125));
        BOXES.put(StateTypes.WAXED_COPPER_LANTERN, new Box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125));
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER_LANTERN, new Box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125));
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER_LANTERN, new Box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125));
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER_LANTERN, new Box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125));

        BOXES.put(StateTypes.CAMPFIRE, new Box(0, 0, 0, 1, 0.4375, 1));
        BOXES.put(StateTypes.SOUL_CAMPFIRE, new Box(0, 0, 0, 1, 0.4375, 1));

        BOXES.put(StateTypes.END_ROD, new Box(0.375, 0, 0.375, 0.625, 1, 0.625));
        BOXES.put(StateTypes.LIGHTNING_ROD, new Box(0.3125, 0, 0.3125, 0.6875, 1, 0.6875));
        BOXES.put(StateTypes.EXPOSED_LIGHTNING_ROD, new Box(0.3125, 0, 0.3125, 0.6875, 1, 0.6875));
        BOXES.put(StateTypes.WEATHERED_LIGHTNING_ROD, new Box(0.3125, 0, 0.3125, 0.6875, 1, 0.6875));
        BOXES.put(StateTypes.OXIDIZED_LIGHTNING_ROD, new Box(0.3125, 0, 0.3125, 0.6875, 1, 0.6875));
        BOXES.put(StateTypes.WAXED_LIGHTNING_ROD, new Box(0.3125, 0, 0.3125, 0.6875, 1, 0.6875));
        BOXES.put(StateTypes.WAXED_EXPOSED_LIGHTNING_ROD, new Box(0.3125, 0, 0.3125, 0.6875, 1, 0.6875));
        BOXES.put(StateTypes.WAXED_WEATHERED_LIGHTNING_ROD, new Box(0.3125, 0, 0.3125, 0.6875, 1, 0.6875));
        BOXES.put(StateTypes.WAXED_OXIDIZED_LIGHTNING_ROD, new Box(0.3125, 0, 0.3125, 0.6875, 1, 0.6875));

        BOXES.put(StateTypes.BELL, new Box(0.125, 0, 0.125, 0.875, 0.875, 0.875));

        BOXES.put(StateTypes.BREWING_STAND, new Box(0.125, 0, 0.125, 0.875, 0.5, 0.875));

        BOXES.put(StateTypes.CAULDRON, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.WATER_CAULDRON, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.LAVA_CAULDRON, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.POWDER_SNOW_CAULDRON, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));

        BOXES.put(StateTypes.COMPOSTER, new Box(0, 0, 0, 1, 0.5625, 1));

        BOXES.put(StateTypes.GRINDSTONE, new Box(0, 0, 0, 1, 0.625, 1));

        BOXES.put(StateTypes.STONECUTTER, new Box(0, 0, 0, 1, 0.5625, 1));

        BOXES.put(StateTypes.HOPPER, new Box(0, 0, 0, 1, 0.625, 1));

        BOXES.put(StateTypes.PISTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.STICKY_PISTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PISTON_HEAD, new Box(0, 0, 0, 1, 0.75, 1));
        BOXES.put(StateTypes.MOVING_PISTON, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.DISPENSER, FULL);
        BOXES.put(StateTypes.DROPPER, FULL);

        BOXES.put(StateTypes.IRON_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.OAK_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.SPRUCE_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.BIRCH_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.JUNGLE_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.ACACIA_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.CHERRY_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.DARK_OAK_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.MANGROVE_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.PALE_OAK_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.BAMBOO_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.CRIMSON_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.WARPED_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.COPPER_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.EXPOSED_COPPER_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.OXIDIZED_COPPER_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.WEATHERED_COPPER_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.WAXED_COPPER_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER_DOOR, new Box(0, 0, 0, 1, 1, 0.1875));

        BOXES.put(StateTypes.IRON_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.OAK_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.SPRUCE_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.BIRCH_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.JUNGLE_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.ACACIA_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.CHERRY_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.DARK_OAK_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.MANGROVE_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.PALE_OAK_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.BAMBOO_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.CRIMSON_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.WARPED_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.COPPER_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.EXPOSED_COPPER_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.OXIDIZED_COPPER_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.WEATHERED_COPPER_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.WAXED_COPPER_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER_TRAPDOOR, new Box(0, 0, 0, 1, 0.1875, 1));

        BOXES.put(StateTypes.OAK_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SPRUCE_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BIRCH_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.JUNGLE_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ACACIA_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CHERRY_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DARK_OAK_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MANGROVE_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PALE_OAK_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BAMBOO_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CRIMSON_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WARPED_FENCE_GATE, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.WHITE_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.ORANGE_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.MAGENTA_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.LIGHT_BLUE_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.YELLOW_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.LIME_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.PINK_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.GRAY_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.LIGHT_GRAY_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.CYAN_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.PURPLE_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.BLUE_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.BROWN_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.GREEN_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.RED_BED, new Box(0, 0, 0, 1, 0.5625, 1));
        BOXES.put(StateTypes.BLACK_BED, new Box(0, 0, 0, 1, 0.5625, 1));

        BOXES.put(StateTypes.WHITE_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ORANGE_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MAGENTA_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LIGHT_BLUE_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.YELLOW_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LIME_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PINK_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.GRAY_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LIGHT_GRAY_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CYAN_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PURPLE_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BLUE_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BROWN_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.GREEN_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.RED_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BLACK_BANNER, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.WHITE_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ORANGE_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MAGENTA_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LIGHT_BLUE_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.YELLOW_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LIME_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PINK_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.GRAY_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LIGHT_GRAY_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CYAN_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PURPLE_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BLUE_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BROWN_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.GREEN_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.RED_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BLACK_WALL_BANNER, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.SKELETON_SKULL, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.WITHER_SKELETON_SKULL, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.PLAYER_HEAD, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.ZOMBIE_HEAD, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.CREEPER_HEAD, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.DRAGON_HEAD, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.PIGLIN_HEAD, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));

        BOXES.put(StateTypes.SKELETON_WALL_SKULL, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.WITHER_SKELETON_WALL_SKULL, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.PLAYER_WALL_HEAD, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.ZOMBIE_WALL_HEAD, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.CREEPER_WALL_HEAD, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.DRAGON_WALL_HEAD, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.PIGLIN_WALL_HEAD, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));

        BOXES.put(StateTypes.FLOWER_POT, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));

        BOXES.put(StateTypes.POTTED_OAK_SAPLING, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_SPRUCE_SAPLING, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_BIRCH_SAPLING, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_JUNGLE_SAPLING, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_ACACIA_SAPLING, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_CHERRY_SAPLING, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_DARK_OAK_SAPLING, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_PALE_OAK_SAPLING, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_MANGROVE_PROPAGULE, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));

        BOXES.put(StateTypes.POTTED_FERN, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_DANDELION, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_POPPY, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_BLUE_ORCHID, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_ALLIUM, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_AZURE_BLUET, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_RED_TULIP, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_ORANGE_TULIP, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_WHITE_TULIP, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_PINK_TULIP, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_OXEYE_DAISY, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_CORNFLOWER, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_LILY_OF_THE_VALLEY, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_WITHER_ROSE, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_RED_MUSHROOM, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_BROWN_MUSHROOM, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_DEAD_BUSH, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_CACTUS, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_BAMBOO, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_CRIMSON_FUNGUS, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_WARPED_FUNGUS, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_CRIMSON_ROOTS, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_WARPED_ROOTS, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_AZALEA_BUSH, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_FLOWERING_AZALEA_BUSH, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_TORCHFLOWER, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_OPEN_EYEBLOSSOM, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_CLOSED_EYEBLOSSOM, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));
        BOXES.put(StateTypes.POTTED_GOLDEN_DANDELION, new Box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875));

        BOXES.put(StateTypes.CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.WHITE_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.ORANGE_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.MAGENTA_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.LIGHT_BLUE_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.YELLOW_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.LIME_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.PINK_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.GRAY_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.LIGHT_GRAY_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.CYAN_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.PURPLE_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.BLUE_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.BROWN_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.GREEN_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.RED_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
        BOXES.put(StateTypes.BLACK_CANDLE, new Box(0.375, 0, 0.375, 0.625, 0.5, 0.625));

        BOXES.put(StateTypes.CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.WHITE_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.ORANGE_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.MAGENTA_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.LIGHT_BLUE_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.YELLOW_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.LIME_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.PINK_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.GRAY_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.LIGHT_GRAY_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.CYAN_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.PURPLE_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.BLUE_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.BROWN_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.GREEN_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.RED_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.BLACK_CANDLE_CAKE, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));

        BOXES.put(StateTypes.SMALL_AMETHYST_BUD, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));
        BOXES.put(StateTypes.MEDIUM_AMETHYST_BUD, new Box(0.25, 0, 0.25, 0.75, 0.625, 0.75));
        BOXES.put(StateTypes.LARGE_AMETHYST_BUD, new Box(0.1875, 0, 0.1875, 0.8125, 0.75, 0.8125));
        BOXES.put(StateTypes.AMETHYST_CLUSTER, new Box(0.1875, 0, 0.1875, 0.8125, 1, 0.8125));

        BOXES.put(StateTypes.POINTED_DRIPSTONE, new Box(0.3125, 0, 0.3125, 0.6875, 1, 0.6875));

        BOXES.put(StateTypes.CHORUS_PLANT, new Box(0.3125, 0, 0.3125, 0.6875, 1, 0.6875));
        BOXES.put(StateTypes.CHORUS_FLOWER, new Box(0.3125, 0, 0.3125, 0.6875, 0.625, 0.6875));

        BOXES.put(StateTypes.COBWEB, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.VINE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.GLOW_LICHEN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SCULK_VEIN, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.OAK_LEAVES, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SPRUCE_LEAVES, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BIRCH_LEAVES, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.JUNGLE_LEAVES, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ACACIA_LEAVES, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CHERRY_LEAVES, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DARK_OAK_LEAVES, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MANGROVE_LEAVES, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.AZALEA_LEAVES, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.FLOWERING_AZALEA_LEAVES, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PALE_OAK_LEAVES, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.SHORT_GRASS, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.FERN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_BUSH, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.BUSH, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.SHORT_DRY_GRASS, new Box(0, 0, 0, 1, 0.5, 1));

        BOXES.put(StateTypes.TALL_GRASS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LARGE_FERN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.TALL_DRY_GRASS, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.SUNFLOWER, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.LILAC, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ROSE_BUSH, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PEONY, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.SEAGRASS, new Box(0, 0, 0, 1, 0.625, 1));
        BOXES.put(StateTypes.TALL_SEAGRASS, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.KELP, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.KELP_PLANT, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.SEA_PICKLE, new Box(0.375, 0, 0.375, 0.625, 0.625, 0.625));

        BOXES.put(StateTypes.TUBE_CORAL, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));
        BOXES.put(StateTypes.BRAIN_CORAL, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));
        BOXES.put(StateTypes.BUBBLE_CORAL, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));
        BOXES.put(StateTypes.FIRE_CORAL, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));
        BOXES.put(StateTypes.HORN_CORAL, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));
        BOXES.put(StateTypes.DEAD_TUBE_CORAL, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));
        BOXES.put(StateTypes.DEAD_BRAIN_CORAL, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));
        BOXES.put(StateTypes.DEAD_BUBBLE_CORAL, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));
        BOXES.put(StateTypes.DEAD_FIRE_CORAL, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));
        BOXES.put(StateTypes.DEAD_HORN_CORAL, new Box(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875));

        BOXES.put(StateTypes.TUBE_CORAL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.BRAIN_CORAL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.BUBBLE_CORAL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.FIRE_CORAL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.HORN_CORAL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_TUBE_CORAL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_BRAIN_CORAL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_BUBBLE_CORAL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_FIRE_CORAL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_HORN_CORAL_FAN, new Box(0, 0, 0, 1, 0.5, 1));

        BOXES.put(StateTypes.TUBE_CORAL_WALL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.BRAIN_CORAL_WALL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.BUBBLE_CORAL_WALL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.FIRE_CORAL_WALL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.HORN_CORAL_WALL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_TUBE_CORAL_WALL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_BRAIN_CORAL_WALL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_BUBBLE_CORAL_WALL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_FIRE_CORAL_WALL_FAN, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.DEAD_HORN_CORAL_WALL_FAN, new Box(0, 0, 0, 1, 0.5, 1));

        BOXES.put(StateTypes.WEEPING_VINES, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.WEEPING_VINES_PLANT, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.TWISTING_VINES, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.TWISTING_VINES_PLANT, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));

        BOXES.put(StateTypes.CAVE_VINES, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.CAVE_VINES_PLANT, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));
        BOXES.put(StateTypes.PALE_HANGING_MOSS, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));

        BOXES.put(StateTypes.NETHER_SPROUTS, new Box(0, 0, 0, 1, 0.25, 1));

        BOXES.put(StateTypes.CRIMSON_ROOTS, new Box(0, 0, 0, 1, 0.25, 1));
        BOXES.put(StateTypes.WARPED_ROOTS, new Box(0, 0, 0, 1, 0.25, 1));

        BOXES.put(StateTypes.WATER, EMPTY);
        BOXES.put(StateTypes.LAVA, EMPTY);
        BOXES.put(StateTypes.FIRE, EMPTY);
        BOXES.put(StateTypes.SOUL_FIRE, EMPTY);
        BOXES.put(StateTypes.BUBBLE_COLUMN, EMPTY);

        BOXES.put(StateTypes.NETHER_PORTAL, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.END_PORTAL, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.END_GATEWAY, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.PISTON, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.STICKY_PISTON, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.POWERED_RAIL, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.DETECTOR_RAIL, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.RAIL, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.ACTIVATOR_RAIL, new Box(0, 0, 0, 1, 0.125, 1));

        BOXES.put(StateTypes.REDSTONE_WIRE, new Box(0, 0, 0, 1, 0.0625, 1));

        BOXES.put(StateTypes.TRIPWIRE, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.TRIPWIRE_HOOK, new Box(0, 0, 0, 1, 0.5, 1));

        BOXES.put(StateTypes.REPEATER, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.COMPARATOR, new Box(0, 0, 0, 1, 0.125, 1));

        BOXES.put(StateTypes.WHEAT, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.CARROTS, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.POTATOES, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.BEETROOTS, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.TORCHFLOWER_CROP, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.PITCHER_CROP, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.NETHER_WART, new Box(0, 0, 0, 1, 0.25, 1));
        BOXES.put(StateTypes.SWEET_BERRY_BUSH, new Box(0, 0, 0, 1, 0.25, 1));

        BOXES.put(StateTypes.PUMPKIN_STEM, new Box(0, 0, 0, 1, 0.25, 1));
        BOXES.put(StateTypes.MELON_STEM, new Box(0, 0, 0, 1, 0.25, 1));
        BOXES.put(StateTypes.ATTACHED_PUMPKIN_STEM, new Box(0, 0, 0, 1, 0.25, 1));
        BOXES.put(StateTypes.ATTACHED_MELON_STEM, new Box(0, 0, 0, 1, 0.25, 1));

        BOXES.put(StateTypes.COCOA, new Box(0.25, 0, 0.25, 0.75, 0.625, 0.75));

        BOXES.put(StateTypes.LILY_PAD, new Box(0, 0, 0, 1, 0.015625, 1));

        BOXES.put(StateTypes.AZALEA, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.FLOWERING_AZALEA, new Box(0, 0, 0, 1, 0.5, 1));

        BOXES.put(StateTypes.SPORE_BLOSSOM, new Box(0.125, 0, 0.125, 0.875, 0.5, 0.875));

        BOXES.put(StateTypes.BIG_DRIPLEAF, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BIG_DRIPLEAF_STEM, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SMALL_DRIPLEAF, new Box(0, 0, 0, 1, 0.5, 1));

        BOXES.put(StateTypes.HANGING_ROOTS, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.PINK_PETALS, new Box(0, 0, 0, 1, 0.0625, 1));
        BOXES.put(StateTypes.LEAF_LITTER, new Box(0, 0, 0, 1, 0.0625, 1));

        BOXES.put(StateTypes.WILDFLOWERS, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.FIREFLY_BUSH, new Box(0, 0, 0, 1, 0.5, 1));
        BOXES.put(StateTypes.CACTUS_FLOWER, new Box(0.25, 0, 0.25, 0.75, 0.25, 0.75));

        BOXES.put(StateTypes.TURTLE_EGG, new Box(0.3125, 0, 0.3125, 0.6875, 0.25, 0.6875));
        BOXES.put(StateTypes.SNIFFER_EGG, new Box(0.0625, 0, 0.0625, 0.9375, 0.5, 0.9375));
        BOXES.put(StateTypes.FROGSPAWN, new Box(0, 0, 0, 1, 0.0625, 1));

        BOXES.put(StateTypes.DECORATED_POT, new Box(0.1875, 0, 0.1875, 0.8125, 0.75, 0.8125));

        BOXES.put(StateTypes.SUGAR_CANE, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.BAMBOO, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.BAMBOO_SAPLING, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));

        BOXES.put(StateTypes.CACTUS, new Box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375));

        BOXES.put(StateTypes.OAK_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.SPRUCE_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.BIRCH_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.JUNGLE_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.ACACIA_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.CHERRY_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.DARK_OAK_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.MANGROVE_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.PALE_OAK_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.BAMBOO_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.CRIMSON_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.WARPED_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));

        BOXES.put(StateTypes.OAK_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.SPRUCE_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.BIRCH_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.JUNGLE_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.ACACIA_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.CHERRY_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.DARK_OAK_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.MANGROVE_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.PALE_OAK_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.BAMBOO_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.CRIMSON_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));
        BOXES.put(StateTypes.WARPED_WALL_SIGN, new Box(0.125, 0, 0.125, 0.875, 1, 0.875));

        BOXES.put(StateTypes.OAK_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SPRUCE_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BIRCH_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.JUNGLE_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ACACIA_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CHERRY_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DARK_OAK_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MANGROVE_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PALE_OAK_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BAMBOO_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CRIMSON_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WARPED_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.OAK_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.SPRUCE_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BIRCH_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.JUNGLE_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.ACACIA_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CHERRY_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.DARK_OAK_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.MANGROVE_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.PALE_OAK_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.BAMBOO_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.CRIMSON_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WARPED_WALL_HANGING_SIGN, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.OAK_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.SPRUCE_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.BIRCH_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.JUNGLE_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.ACACIA_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.CHERRY_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.DARK_OAK_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.MANGROVE_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.PALE_OAK_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.BAMBOO_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.CRIMSON_SHELF, new Box(0, 0, 0, 1, 0.125, 1));
        BOXES.put(StateTypes.WARPED_SHELF, new Box(0, 0, 0, 1, 0.125, 1));

        BOXES.put(StateTypes.CHISELED_BOOKSHELF, new Box(0, 0, 0, 1, 0.75, 1));

        BOXES.put(StateTypes.RESIN_CLUMP, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.DRIED_GHAST, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));

        BOXES.put(StateTypes.TNT, FULL);
        BOXES.put(StateTypes.STRUCTURE_BLOCK, FULL);
        BOXES.put(StateTypes.JIGSAW, FULL);
        BOXES.put(StateTypes.STRUCTURE_VOID, EMPTY);
        BOXES.put(StateTypes.POWDER_SNOW, new Box(0, 0, 0, 1, 0.875, 1));

        BOXES.put(StateTypes.BEDROCK, FULL);
        BOXES.put(StateTypes.LIGHT, FULL);

        BOXES.put(StateTypes.INFESTED_STONE, FULL);
        BOXES.put(StateTypes.INFESTED_COBBLESTONE, FULL);
        BOXES.put(StateTypes.INFESTED_STONE_BRICKS, FULL);
        BOXES.put(StateTypes.INFESTED_MOSSY_STONE_BRICKS, FULL);
        BOXES.put(StateTypes.INFESTED_CRACKED_STONE_BRICKS, FULL);
        BOXES.put(StateTypes.INFESTED_CHISELED_STONE_BRICKS, FULL);
        BOXES.put(StateTypes.INFESTED_DEEPSLATE, FULL);

        BOXES.put(StateTypes.WHITE_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.ORANGE_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.MAGENTA_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.LIGHT_BLUE_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.YELLOW_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.LIME_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.PINK_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.GRAY_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.LIGHT_GRAY_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.CYAN_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.PURPLE_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.BLUE_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.BROWN_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.GREEN_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.RED_STAINED_GLASS, FULL);
        BOXES.put(StateTypes.BLACK_STAINED_GLASS, FULL);

        BOXES.put(StateTypes.SEA_LANTERN, FULL);
        BOXES.put(StateTypes.PRISMARINE, FULL);
        BOXES.put(StateTypes.PRISMARINE_BRICKS, FULL);
        BOXES.put(StateTypes.DARK_PRISMARINE, FULL);

        BOXES.put(StateTypes.MAGMA_BLOCK, FULL);
        BOXES.put(StateTypes.NETHER_WART_BLOCK, FULL);
        BOXES.put(StateTypes.WARPED_WART_BLOCK, FULL);
        BOXES.put(StateTypes.RED_NETHER_BRICKS, FULL);
        BOXES.put(StateTypes.BONE_BLOCK, FULL);

        BOXES.put(StateTypes.LOOM, FULL);
        BOXES.put(StateTypes.CARTOGRAPHY_TABLE, FULL);
        BOXES.put(StateTypes.FLETCHING_TABLE, FULL);
        BOXES.put(StateTypes.SMITHING_TABLE, FULL);
        BOXES.put(StateTypes.COMPOSTER, new Box(0, 0, 0, 1, 0.5625, 1));

        BOXES.put(StateTypes.CONDUIT, new Box(0.1875, 0, 0.1875, 0.8125, 0.8125, 0.8125));

        BOXES.put(StateTypes.OAK_SAPLING, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.SPRUCE_SAPLING, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.BIRCH_SAPLING, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.JUNGLE_SAPLING, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.ACACIA_SAPLING, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.CHERRY_SAPLING, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.DARK_OAK_SAPLING, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.PALE_OAK_SAPLING, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.MANGROVE_PROPAGULE, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));

        BOXES.put(StateTypes.DANDELION, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.POPPY, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.BLUE_ORCHID, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.ALLIUM, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.AZURE_BLUET, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.RED_TULIP, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.ORANGE_TULIP, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.WHITE_TULIP, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.PINK_TULIP, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.OXEYE_DAISY, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.CORNFLOWER, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.LILY_OF_THE_VALLEY, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.WITHER_ROSE, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.TORCHFLOWER, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.PITCHER_PLANT, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.OPEN_EYEBLOSSOM, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.CLOSED_EYEBLOSSOM, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.GOLDEN_DANDELION, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));

        BOXES.put(StateTypes.BROWN_MUSHROOM, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.RED_MUSHROOM, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.CRIMSON_FUNGUS, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));
        BOXES.put(StateTypes.WARPED_FUNGUS, new Box(0.25, 0, 0.25, 0.75, 0.5, 0.75));

        BOXES.put(StateTypes.COPPER_GRATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.EXPOSED_COPPER_GRATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WEATHERED_COPPER_GRATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.OXIDIZED_COPPER_GRATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_COPPER_GRATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER_GRATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER_GRATE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER_GRATE, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.COPPER_CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));
        BOXES.put(StateTypes.EXPOSED_COPPER_CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));
        BOXES.put(StateTypes.WEATHERED_COPPER_CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));
        BOXES.put(StateTypes.OXIDIZED_COPPER_CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));
        BOXES.put(StateTypes.WAXED_COPPER_CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER_CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER_CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER_CHEST, new Box(0.0625, 0, 0.0625, 0.9375, 0.875, 0.9375));

        BOXES.put(StateTypes.COPPER_GOLEM_STATUE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.EXPOSED_COPPER_GOLEM_STATUE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WEATHERED_COPPER_GOLEM_STATUE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.OXIDIZED_COPPER_GOLEM_STATUE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_COPPER_GOLEM_STATUE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER_GOLEM_STATUE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER_GOLEM_STATUE, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER_GOLEM_STATUE, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.EXPOSED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WEATHERED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.OXIDIZED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_EXPOSED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_WEATHERED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));
        BOXES.put(StateTypes.WAXED_OXIDIZED_COPPER_BARS, new Box(0, 0, 0, 1, 1, 1));

        BOXES.put(StateTypes.TEST_BLOCK, FULL);
        BOXES.put(StateTypes.TEST_INSTANCE_BLOCK, FULL);
    }

    public static Box getBox(StateType state) {
        return BOXES.getOrDefault(state, FULL);
    }

    public static boolean isFullCube(StateType state) {
        Box box = getBox(state);
        return box.minX == 0 && box.minY == 0 && box.minZ == 0 &&
                box.maxX == 1 && box.maxY == 1 && box.maxZ == 1;
    }

    public static boolean isEmpty(StateType state) {
        Box box = getBox(state);
        return box.minX == box.maxX || box.minY == box.maxY || box.minZ == box.maxZ;
    }

    public static class Box {
        public final double minX, minY, minZ;
        public final double maxX, maxY, maxZ;

        public Box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        public Box offset(double x, double y, double z) {
            return new Box(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z);
        }

        public boolean intersects(Box other) {
            return this.minX < other.maxX && this.maxX > other.minX &&
                    this.minY < other.maxY && this.maxY > other.minY &&
                    this.minZ < other.maxZ && this.maxZ > other.minZ;
        }

        public boolean contains(double x, double y, double z) {
            return x >= minX && x <= maxX &&
                    y >= minY && y <= maxY &&
                    z >= minZ && z <= maxZ;
        }

        @Override
        public String toString() {
            return String.format("Box[%.2f,%.2f,%.2f -> %.2f,%.2f,%.2f]",
                    minX, minY, minZ, maxX, maxY, maxZ);
        }
    }
}
