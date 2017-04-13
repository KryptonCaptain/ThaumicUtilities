package com.saegusa.thu;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModCompat {
	
	public static Item FMsubCollar;
	public static Boolean ForbiddenMagic;
	
	public static void init() {
		
		FMsubCollar = GameRegistry.findItem("ForbiddenMagic", "SubCollar");
		ForbiddenMagic = Loader.isModLoaded("ForbiddenMagic");
	}

}
