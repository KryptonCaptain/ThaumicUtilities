package com.saegusa.thu.settings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler
{
    public static Configuration config;
    public static Configuration tConfig;
    public static final String CATEGORY_GUI = "general.render";
    public static final String CATEGORY_TWEAKS = "general.tweaks";
    public static final String CATEGORY_WORLDGEN = "worldgen";
    
    public static void init(final File configFile, final File thaumcraftConfig) {
        if (ConfigurationHandler.config == null) {
            ConfigurationHandler.config = new Configuration(configFile, true);
            loadConfig();
        }
        if (ConfigurationHandler.tConfig == null) {
            ConfigurationHandler.tConfig = new Configuration(thaumcraftConfig);
            loadTcConfig();
        }
    }
    
    public static void loadConfig() {
        Settings.displayVis = ConfigurationHandler.config.getBoolean("displayVisHUD", "general.render", true, "Display Vis Storage HUD on screen when wearing a vis storage amulet.");
        Settings.displayVisOnShiftDown = ConfigurationHandler.config.getBoolean("displayVisHUD_ShiftKey", "general.render", false, "Only display Vis Storage HUD when Shift key is pressed.");
        Settings.useOldTextHUD = ConfigurationHandler.config.getBoolean("useOldHUD", "general.render", false, "Display old vis storage HUD (text only)");
        Settings.tweakVisAmuletTooltip = ConfigurationHandler.config.getBoolean("visAmuletTooltipTweak", "general.tweaks", true, "Tweak the amulet tooltip to be like the wand");
        Settings.generateOresInNether = ConfigurationHandler.config.getBoolean("generateOresInNether", "worldgen", true, "Generate Thaumcraft ores in the Nether");
        Settings.generateInfusedOresInNether = ConfigurationHandler.config.getBoolean("generateInfusedOresInNether", "worldgen", true, "Generate Infused Netherrack ores in the Nether");
        Settings.generateOtherOresInNether = ConfigurationHandler.config.getBoolean("generateOtherOresInNether", "worldgen", true, "Generate Amber and Cinnabar ores in the Nether");
        Settings.netherOreRarity = ConfigurationHandler.config.getInt("netherOreRarity", "worldgen", 4, 0, 12, "Ore generation rarity in the nether");
        if (ConfigurationHandler.config.hasChanged()) {
            ConfigurationHandler.config.save();
        }
    }
    
    public static void loadTcConfig() {
        ThaumcraftSettings.wandDialBottom = ConfigurationHandler.tConfig.get("general", "wand_dial_bottom", false).getBoolean();
    }
    
    @SubscribeEvent
    public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase("thutilities")) {
            loadConfig();
        }
        if (event.modID.equalsIgnoreCase("thaumcraft")) {
            loadTcConfig();
        }
    }
    
    public static class Settings
    {
        public static boolean displayVis;
        public static final String displayVis_name = "displayVisHUD";
        public static final String displayVis_desc = "Display Vis Storage HUD on screen when wearing a vis storage amulet.";
        public static final boolean displayVis_default = true;
        public static boolean displayVisOnShiftDown;
        public static final String displayVisOnShiftDown_name = "displayVisHUD_ShiftKey";
        public static final String displayVisOnShiftDown_desc = "Only display Vis Storage HUD when Shift key is pressed.";
        public static final boolean displayVisOnShiftDown_default = false;
        public static boolean useOldTextHUD;
        public static final String useOldTextHUD_name = "useOldHUD";
        public static final String useOldTextHUD_desc = "Display old vis storage HUD (text only)";
        public static final boolean useOldTextHUD_default = false;
        public static boolean tweakVisAmuletTooltip;
        public static final String tweakVisAmuletTooltip_name = "visAmuletTooltipTweak";
        public static final String tweakVisAmuletTooltip_desc = "Tweak the amulet tooltip to be like the wand";
        public static final boolean tweakVisAmuletTooltip_default = true;
        public static boolean generateOresInNether;
        public static final String generateOresInNether_name = "generateOresInNether";
        public static final String generateOresInNether_desc = "Generate Thaumcraft ores in the Nether";
        public static final boolean generateOresInNether_default = true;
        public static boolean generateInfusedOresInNether;
        public static final String generateInfusedOresInNether_name = "generateInfusedOresInNether";
        public static final String generateInfusedOresInNether_desc = "Generate Infused Netherrack ores in the Nether";
        public static final boolean generateInfusedOresInNether_default = true;
        public static boolean generateOtherOresInNether;
        public static final String generateOtherOresInNether_name = "generateOtherOresInNether";
        public static final String generateOtherOresInNether_desc = "Generate Amber and Cinnabar ores in the Nether";
        public static final boolean generateOtherOresInNether_default = true;
        public static int netherOreRarity;
        public static final String netherOreRarity_name = "netherOreRarity";
        public static final String netherOreRarity_desc = "Ore generation rarity in the nether";
        public static final int netherOreRarity_default = 4;
    }
    
    public static class ThaumcraftSettings
    {
        public static boolean wandDialBottom;
    }
}
