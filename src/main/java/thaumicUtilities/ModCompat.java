package thaumicUtilities;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModCompat {
	
	public static Item FMsubCollar;
	public static Boolean ForbiddenMagic;
	
	public static Item BMSanguineHelm;
	public static Item BMSanguineChest;
	public static Item BMSanguineLegs;
	public static Item BMSanguineBoots;
	public static Boolean BloodMagic;
	
	public static void init() {
		
		FMsubCollar = GameRegistry.findItem("ForbiddenMagic", "SubCollar");
		ForbiddenMagic = Loader.isModLoaded("ForbiddenMagic");

		
		BMSanguineHelm = GameRegistry.findItem("AWWayofTime", "sanguineHelmet");
		BMSanguineChest = GameRegistry.findItem("AWWayofTime", "sanguineRobe");
		BMSanguineLegs = GameRegistry.findItem("AWWayofTime", "sanguinePants");
		BMSanguineBoots = GameRegistry.findItem("AWWayofTime", "sanguineBoots");
		BloodMagic = Loader.isModLoaded("AWWayofTime");
	}

}
