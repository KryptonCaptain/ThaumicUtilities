package com.saegusa.thu;

import com.saegusa.thu.core.*;
import net.minecraft.block.*;
import java.io.*;
import com.saegusa.thu.settings.*;
import net.minecraftforge.common.*;
import com.saegusa.thu.blocks.*;
import cpw.mods.fml.common.registry.*;
import com.saegusa.thu.render.*;
import cpw.mods.fml.client.registry.*;
import com.saegusa.thu.world.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = "thutilities", name = "Thaumic Utilities", version = "@version")
public class ThaumicUtilities
{
    @Mod.Instance("thutilities")
    public static ThaumicUtilities instance;
    @SidedProxy(serverSide = "com.saegusa.thu.core.ServerProxy", clientSide = "com.saegusa.thu.core.ClientProxy")
    public static IProxy proxy;
    public static int netherOre_renderID;
    public static Block blockNetherOre;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory() + File.separator + "ThaumicUtilities.cfg"), new File(event.getModConfigurationDirectory() + File.separator + "Thaumcraft.cfg"));
        MinecraftForge.EVENT_BUS.register((Object)new RenderGuiHandler());
        ThaumicUtilities.proxy.preInit(event);
        GameRegistry.registerBlock(ThaumicUtilities.blockNetherOre = new BlockNetherOre().setBlockName("blockNetherOre"), (Class)BlockNetherOreItem.class, "blockNetherOre");
        ThaumicUtilities.netherOre_renderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new BlockOreRenderer());
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)new ConfigurationHandler());
        GameRegistry.registerWorldGenerator((IWorldGenerator)new NetherOreGen(), 0);
        ThaumicUtilities.proxy.init(event);
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        ThaumicUtilities.proxy.postInit(event);
    }
    
    static {
        ThaumicUtilities.netherOre_renderID = -1;
    }
}
