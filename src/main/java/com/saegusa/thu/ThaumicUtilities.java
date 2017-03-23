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
import com.saegusa.thu.settings.ConfigurationHandler;
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
