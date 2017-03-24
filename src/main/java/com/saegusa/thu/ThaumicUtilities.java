package com.saegusa.thu;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.IWorldGenerator;
import com.saegusa.thu.world.NetherOreGen;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import com.saegusa.thu.render.BlockOreRenderer;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import com.saegusa.thu.blocks.BlockNetherOreItem;
import com.saegusa.thu.blocks.BlockNetherOre;
import com.saegusa.thu.render.RenderGuiHandler;
import net.minecraftforge.common.MinecraftForge;
import com.saegusa.thu.settings.ConfigHandler;
import java.io.File;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import cpw.mods.fml.common.SidedProxy;
import com.saegusa.thu.core.IProxy;
import cpw.mods.fml.common.Mod;

@Mod(modid = "thutilities", name = "Thaumic Utilities", version = "@version")
public class ThaumicUtilities
{
    @Mod.Instance("thutilities")
    public static ThaumicUtilities instance;
    @SidedProxy(serverSide = "com.saegusa.thu.core.ServerProxy", clientSide = "com.saegusa.thu.core.ClientProxy")
    public static IProxy proxy;
    
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        ConfigHandler.init(new File(event.getModConfigurationDirectory() + File.separator + "ThaumicUtilities.cfg"), new File(event.getModConfigurationDirectory() + File.separator + "Thaumcraft.cfg"));
        MinecraftForge.EVENT_BUS.register((Object)new RenderGuiHandler());
        
        ModContent.preInit();
        
        ThaumicUtilities.proxy.preInit(event);
        
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)new ConfigHandler());
        
        ModContent.init();
        
        ThaumicUtilities.proxy.init(event);
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        ModContent.postInit();
    	
    	ThaumicUtilities.proxy.postInit(event);
    }
    
}
