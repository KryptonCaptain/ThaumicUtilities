package com.saegusa.thu.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class BlockNetherOreItem extends ItemBlock
{
    public static final int[] colors;
    
    public BlockNetherOreItem(final Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    public int getMetadata(final int par1) {
        return par1;
    }
    
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }
    
    static {
        colors = new int[] { 16777215, 16777086, 16727041, 37119, 40960, 15650047, 5592439 };
    }
}
