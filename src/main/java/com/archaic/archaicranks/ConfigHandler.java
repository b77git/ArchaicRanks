package com.archaic.archaicranks;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigHandler {

    public enum RankCategory {
        NOV(0, "gregtech:machine:986", "&7[&8NoV&7]&r"),
        LV(1, "gregtech:machine:987", "&7[Lv]&r"),
        MV(2, "gregtech:machine:988", "&7[&bMv&7]&r"),
        HV(3, "gregtech:machine:989", "&7[&eHv&7]&r"),
        EV(4, "gregtech:machine:990", "&7[&5Ev&7]&r"),
        IV(5, "gregtech:machine:991", "&7[&9Iv&7]&r"),
        LUV(6, "gregtech:machine:992", "&7[&dLuV&7]&r"),
        ZPM(7, "gregtech:machine:993", "&7[&cZPM&7]&r"),
        UV(8, "gregtech:machine:994", "&7[&bUV&7]&r"),
        UHV(9, "gregtech:machine:1669", "&7[&4UHV&7]&r"),
        CREATIVE_TANK(10, "storagedrawers:upgrade_creative:1", "&7[&6Tank&7]&r"),
        VENDING(11, "", "&7[&aVending&7]&r");

        private final int index;
        private final String defaultItemRequired;
        private final String defaultPrefix;

        RankCategory(int index, String defaultItemRequired, String defaultPrefix) {
            this.index = index;
            this.defaultItemRequired = defaultItemRequired;
            this.defaultPrefix = defaultPrefix;
        }

        public int getIndex() {
            return index;
        }

        public String getDefaultItemRequired() {
            return defaultItemRequired;
        }

        public String getDefaultPrefix() {
            return defaultPrefix;
        }

        public static RankCategory getByIndex(int index) {
            for (RankCategory category : values()) {
                if (category.index == index) {
                    return category;
                }
            }
            throw new IllegalArgumentException("Invalid index: " + index);
        }
    }

    public static class RankConfig {
        public boolean enabled;
        public String prefix;
        public String itemRequired;
    }

    public static Map<RankCategory, RankConfig> RANK_CONFIGS = new HashMap<>();
    private static Configuration config;

    public void init(File file, Logger logger) {
        config = new Configuration(file);
        syncConfig();
    }

    private static void syncConfig() {
        for (RankCategory category : RankCategory.values()) {
            String categoryName = category.name();
            RankConfig rankConfig = new RankConfig();

            rankConfig.enabled = config.get(categoryName, "Enabled", true, "Enable " + categoryName + " tier").getBoolean(true);
            rankConfig.itemRequired = config.get(categoryName, "Item", category.getDefaultItemRequired(), "Item required to advance to the next tier").getString();
            rankConfig.prefix = config.get(categoryName, "Prefix", category.getDefaultPrefix(), "Rank prefix for " + categoryName + " tier").getString();

            RANK_CONFIGS.put(category, rankConfig);
        }

        if (config.hasChanged()) {
            config.save();
        }
    }
}
