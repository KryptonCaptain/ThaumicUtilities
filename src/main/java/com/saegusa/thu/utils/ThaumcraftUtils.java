package com.saegusa.thu.utils;

import net.minecraft.block.*;
import net.minecraft.item.*;
import cpw.mods.fml.common.*;
import net.minecraft.creativetab.*;

public class ThaumcraftUtils
{
    public static Block getBlock(final String itemString) {
        try {
            final String itemClass = "thaumcraft.common.config.ConfigBlocks";
            final Object obj = Class.forName(itemClass).getField(itemString).get(null);
            if (obj instanceof Block) {
                return (Block)obj;
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static Item getItem(final String itemString, final int meta) {
        final Item item = null;
        try {
            final String itemClass = "thaumcraft.common.config.ConfigItems";
            final Object obj = Class.forName(itemClass).getField(itemString).get(null);
            if (obj instanceof Item) {
                return (Item)obj;
            }
            if (obj instanceof ItemStack) {
                return (Item)obj;
            }
        }
        catch (Exception ex) {
            FMLLog.warning("[Thaumcraft] Could not retrieve item identified by: " + itemString, new Object[0]);
        }
        return item;
    }
    
    public static CreativeTabs getThaumcraftTab() {
        try {
            final String cl = "thaumcraft.common.Thaumcraft";
            final Object obj = Class.forName(cl).getField("tabTC").get(null);
            if (obj instanceof CreativeTabs) {
                return (CreativeTabs)obj;
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }
}
