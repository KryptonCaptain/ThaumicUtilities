package thaumicUtilities.settings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{
    public static Configuration config;
    public static Configuration tConfig;
    public static final String CATEGORY_GUI = "general.render";
    public static final String CATEGORY_TWEAKS = "general.tweaks";
    public static final String CATEGORY_WORLDGEN = "worldgen";
    
    public static boolean displayVis;
    public static boolean displayVisOnShiftDown;
    public static boolean useOldTextHUD;
    public static boolean tweakVisAmuletTooltip;
    public static boolean generateOresInNether;
    public static boolean generateInfusedOresInNether;
    public static boolean generateOtherOresInNether;
    public static int netherOreRarity;
    
    public static boolean displayRunic;
    public static boolean clientMode;
    public static boolean tweakSanityTooltip;

    
    public static void init(final File configFile, final File thaumcraftConfig) {
        if (config == null) {
            config = new Configuration(configFile, true);
            loadConfig();
        }
        if (tConfig == null) {
            tConfig = new Configuration(thaumcraftConfig);
            loadTcConfig();
        }
    }
    
    public static void loadConfig() {
        displayVis = config.getBoolean("displayVisHUD", "general.render", true, "Display Vis Storage HUD on screen when wearing a vis storage amulet.");
        displayVisOnShiftDown = config.getBoolean("displayVisHUD_ShiftKey", "general.render", false, "Only display Vis Storage HUD when Shift key is pressed.");
        useOldTextHUD = config.getBoolean("useOldHUD", "general.render", false, "Display old vis storage HUD (text only)");
        
        tweakVisAmuletTooltip = config.getBoolean("visAmuletTooltipTweak", "general.tweaks", true, "Tweak the amulet tooltip to be like the wand");
        
        generateOresInNether = config.getBoolean("generateOresInNether", "worldgen", true, "Generate Thaumcraft ores in the Nether");
        generateInfusedOresInNether = config.getBoolean("generateInfusedOresInNether", "worldgen", true, "Generate Infused Netherrack ores in the Nether");
        generateOtherOresInNether = config.getBoolean("generateOtherOresInNether", "worldgen", true, "Generate Amber and Cinnabar ores in the Nether");
        netherOreRarity = config.getInt("netherOreRarity", "worldgen", 4, 0, 12, "Ore generation rarity in the nether");
        
        displayRunic = config.getBoolean("displayRunicHUD", "general.render", true, "Displays current runic shielding amounts as numbers next to the health bar.");
        
        tweakSanityTooltip = config.getBoolean("sanityMeterTooltipTweak", "general.tweaks", true, "Tweak the Sanity Meter to display numeric warp values in the tooltip.");
        
        clientMode = config.getBoolean("clientMode", "general", false, "Disables the mod's blocks/items from registering. Only render/tweaks changes will remain.");
        
        if (config.hasChanged()) {
            config.save();
        }
    }
    
    public static void loadTcConfig() {
        ThaumcraftSettings.wandDialBottom = tConfig.get("general", "wand_dial_bottom", false).getBoolean();
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
    
    public static class ThaumcraftSettings
    {
        public static boolean wandDialBottom;
    }
}
