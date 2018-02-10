package thaumicUtilities;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import thaumcraft.api.aspects.Aspect;
import thaumicUtilities.core.IProxy;
import thaumicUtilities.render.RenderGuiHandler;
import thaumicUtilities.settings.ConfigHandler;
import thaumicUtilities.tweaks.DontNoticeMeGuardianSenpai;
import thaumicUtilities.tweaks.FireproofRunic;
import thaumicUtilities.tweaks.TooltipTweaker;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = "thutilities", 
		name = "Thaumic Utilities", 
		version = "${version}",
		dependencies = "required-after:Thaumcraft@[4.2.3.5,)"
	)

public class ThaumicUtilities
{
    @Mod.Instance("thutilities")
    public static ThaumicUtilities instance;
    @SidedProxy(serverSide = "thaumicUtilities.core.ServerProxy", clientSide = "thaumicUtilities.core.ClientProxy")
    public static IProxy proxy;
    
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        ConfigHandler.init(new File(event.getModConfigurationDirectory() + File.separator + "ThaumicUtilities.cfg"), new File(event.getModConfigurationDirectory() + File.separator + "Thaumcraft.cfg"));
        MinecraftForge.EVENT_BUS.register(new RenderGuiHandler());
        
        if (!ConfigHandler.clientMode) {
            ModContent.preInit();        	
        }
        
        ThaumicUtilities.proxy.preInit(event);
        
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ConfigHandler());
        
        if (!ConfigHandler.clientMode) {
            ModContent.init();        	
        }
        
        ThaumicUtilities.proxy.init(event);
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
    	
    	if (!ConfigHandler.clientMode) {
            ModContent.postInit();        	
        }
    	ModCompat.init();

    	if (ConfigHandler.noStepOnGuardian)
    		MinecraftForge.EVENT_BUS.register(new DontNoticeMeGuardianSenpai());
    	if (ConfigHandler.fireproofRunic)
    		MinecraftForge.EVENT_BUS.register(new FireproofRunic());
    	
    	ThaumicUtilities.proxy.postInit(event);
    	
    	
    }
    
}
