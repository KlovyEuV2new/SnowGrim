package ac.grim.grimac.api.npcs.name;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.api.npcs.enums.RandomMode;
import ac.grim.grimac.player.GrimPlayer;

import java.util.*;

public class NPCNameManager {

    private final ConfigManager config;
    private final Random random = new Random();
    private List<String> configNames;
    private List<ChanceMode> chances = new ArrayList<>();
    public static int max_name = 16;

    public static class ChanceMode {
        public RandomMode mode;
        public double chance;
        public ChanceMode(RandomMode mode, double chance) {
            this.mode = mode;
            this.chance = chance;
        }
    }

    public NPCNameManager(ConfigManager config) {
        this.config = config;
        loadConfigNames();
    }

    private void loadConfigNames() {
        max_name = config.getIntElse("npc.random.max-name", 16);

        chances.clear();
        chances.add(new ChanceMode(RandomMode.TAB, config.getDoubleElse("npc.random.chance.TAB", 50.0)));
        chances.add(new ChanceMode(RandomMode.CONFIG, config.getDoubleElse("npc.random.chance.CONFIG", 20.0)));
        chances.add(new ChanceMode(RandomMode.RANDOM, config.getDoubleElse("npc.random.chance.RANDOM", 30.0)));

        String namesString = config.getStringElse("npc.names",
                "akvi4;MrDomer;MrZenyYT;KondrMs;bro9i;FlugerNew;grimac;grim;matrix;stint;t2x2");

        configNames = new ArrayList<>();

        if (!namesString.isEmpty()) {
            for (String name : namesString.split(";")) {
                name = name.trim();
                if (!name.isEmpty()) configNames.add(name);
            }
        }

        if (configNames.isEmpty()) configNames.add("akvi4");
    }

    public String getRandomName(GrimPlayer player, RandomMode mode) {

        if (mode == RandomMode.CONFIG) {
            return trimName(getRandomNameFromConfig(player.user.getProfile().getName()));
        }

        if (mode == RandomMode.TAB) {
            GrimPlayer rnd = getRandomOnlinePlayer(player);
            if (rnd != null) return trimName(rnd.user.getProfile().getName());

            return trimName(getRandomNameFromConfig(player.user.getProfile().getName()));
        }

        if (mode == RandomMode.RANDOM) {
            double roll = random.nextDouble() * 100.0;
            double cumulative = 0.0;

            for (ChanceMode c : chances) {
                cumulative += c.chance;
                if (roll <= cumulative) {
                    if (c.mode == RandomMode.TAB) {
                        GrimPlayer rnd = getRandomOnlinePlayer(player);
                        if (rnd != null)
                            return trimName(rnd.user.getProfile().getName());
                        continue;
                    }

                    if (c.mode == RandomMode.CONFIG) {
                        return trimName(getRandomNameFromConfig(player.user.getProfile().getName()));
                    }

                    if (c.mode == RandomMode.RANDOM) {
                        return trimName(generateRandomName());
                    }
                }
            }

            return trimName(generateRandomName());
        }

        return trimName(getRandomNameFromConfig(player.user.getProfile().getName()));
    }


    private String generateRandomName() {
        int id = random.nextInt(9000) + 1000;

        if (!configNames.isEmpty()) {
            return configNames.get(random.nextInt(configNames.size())) + id;
        }

        return "GrimAC_" + id;
    }

    private String trimName(String name) {
        if (name.length() > max_name)
            return name.substring(0, max_name);
        return name;
    }

    private String getRandomNameFromConfig(String exclude) {
        List<String> list = new ArrayList<>();

        for (String s : configNames) {
            if (!s.equalsIgnoreCase(exclude))
                list.add(s);
        }

        if (list.isEmpty()) list = new ArrayList<>(configNames);

        return list.get(random.nextInt(list.size()));
    }

    private GrimPlayer getRandomOnlinePlayer(GrimPlayer excluded) {
        List<GrimPlayer> list = new ArrayList<>();

        for (GrimPlayer p : GrimAPI.INSTANCE.getPlayerDataManager().getEntries()) {
            if (p != null && !p.equals(excluded)) {
                list.add(p);
            }
        }

        if (list.isEmpty()) return null;

        return list.get(random.nextInt(list.size()));
    }

    public void reloadConfigNames() {
        loadConfigNames();
    }

    public List<String> getConfigNames() {
        return new ArrayList<>(configNames);
    }
}
