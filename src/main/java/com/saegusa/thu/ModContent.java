package com.saegusa.thu;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import com.saegusa.thu.blocks.BlockNetherOre;
import com.saegusa.thu.blocks.BlockNetherOreItem;
import com.saegusa.thu.items.ItemAmulet;
import com.saegusa.thu.render.BlockOreRenderer;
import com.saegusa.thu.world.NetherOreGen;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModContent {
	
	public static int netherOre_renderID;
    public static Block blockNetherOre;
    public static Item itemAmulet;
	
	public static void preInit()
	{
		preInitItems();
		preInitBlocks();
	}
	
	public static void init()
	{
		initializeItems();
		initializeBlocks();
		
		GameRegistry.registerWorldGenerator((IWorldGenerator)new NetherOreGen(), 0);
	}

	public static void postInit()
	{
		//postInitItems();
		//postInitBlocks();
		postInitThaumcraft();
	}
	
	private static void preInitBlocks()
	{
		GameRegistry.registerBlock(blockNetherOre = new BlockNetherOre().setBlockName("blockNetherOre"), BlockNetherOreItem.class, "blockNetherOre");
        netherOre_renderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new BlockOreRenderer());
	}
	
	private static void preInitItems() {
	    //itemAmulet = new ItemAmulet().setUnlocalizedName("ItemAmulet");
	    //GameRegistry.registerItem(itemAmulet, "ItemAmulet");
	}
	
	private static void initializeItems() {
		// register recipes
		
	}
	private static void initializeBlocks() {
		// register tiles
		
	}

	private static void postInitThaumcraft()
	{
		//Add aspects to items/blocks where needed
		
		//AspectList addAspects = new AspectList().add(Aspect.TREE, 4);
		//ThaumcraftApi.registerObjectTag(new ItemStack(BlockWoodenDevice,1,1),addAspects);
		
	}
		
	
    static {
        netherOre_renderID = -1;
    }

}
