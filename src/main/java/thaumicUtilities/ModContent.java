package thaumicUtilities;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import thaumicUtilities.blocks.BlockNetherOre;
import thaumicUtilities.blocks.BlockNetherOreItem;
import thaumicUtilities.items.ItemMonocle;
import thaumicUtilities.render.BlockOreRenderer;
import thaumicUtilities.world.NetherOreGen;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModContent {
	
	public static int netherOre_renderID;
    public static Block blockNetherOre;
    //public static Item itemAmulet;
    //public static Item itemMonocle;
	
	public static void preInit()
	{
		preInitItems();
		preInitBlocks();
	}
	
	public static void init()
	{
		//initializeItems();
		initializeBlocks();
		
		GameRegistry.registerWorldGenerator((IWorldGenerator)new NetherOreGen(), 0);
	}

	public static void postInit()
	{
		//postInitItems();
		//postInitBlocks();
		postInitThaumcraft();
	}
	
	private static void preInitBlocks() //reg blocks
	{
		GameRegistry.registerBlock(blockNetherOre = new BlockNetherOre().setBlockName("blockNetherOre"), BlockNetherOreItem.class, "blockNetherOre");
        netherOre_renderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new BlockOreRenderer());
	}
	
	private static void preInitItems() { //reg items
	    //itemAmulet = new ItemAmulet().setUnlocalizedName("ItemAmulet");
	    //GameRegistry.registerItem(itemAmulet, "ItemAmulet");
		
		//itemMonocle = new ItemMonocle().setUnlocalizedName("ItemMonocle");
	    //GameRegistry.registerItem(itemMonocle, "ItemMonocle");
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
