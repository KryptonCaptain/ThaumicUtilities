package com.saegusa.thu.blocks;

import com.saegusa.thu.ThaumicUtilities;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MathHelper;
import thaumcraft.api.ItemApi;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import com.saegusa.thu.utils.RenderUtils;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import com.saegusa.thu.utils.ThaumcraftUtils;
import net.minecraft.block.material.Material;
import java.util.Random;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class BlockNetherOre extends Block
{
    public IIcon[] icon;
    private Random rand;
    
    public BlockNetherOre() {
        super(Material.rock);
        this.icon = new IIcon[3];
        this.rand = new Random();
        this.setResistance(5.0f);
        this.setHardness(1.5f);
        this.setStepSound(Block.soundTypeStone);
        this.setCreativeTab(ThaumcraftUtils.getThaumcraftTab());
        this.setTickRandomly(true);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister ir) {
        this.icon[0] = ir.registerIcon("minecraft:netherrack");
        this.icon[1] = ir.registerIcon("thaumcraft:infusedore");
        this.icon[2] = ir.registerIcon("thu:nether_amber");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        if (meta == 6) {
            return this.icon[2];
        }
        return this.icon[0];
    }
    
    public boolean canSilkHarvest(final World world, final EntityPlayer player, final int x, final int y, final int z, final int metadata) {
        return true;
    }
    
    public int damageDropped(final int meta) {
        return meta;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(item, 1, 0));
        par3List.add(new ItemStack(item, 1, 1));
        par3List.add(new ItemStack(item, 1, 2));
        par3List.add(new ItemStack(item, 1, 3));
        par3List.add(new ItemStack(item, 1, 4));
        par3List.add(new ItemStack(item, 1, 5));
        par3List.add(new ItemStack(item, 1, 6));
    }
    
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(final World worldObj, final MovingObjectPosition target, final EffectRenderer effectRenderer) {
        final int md = worldObj.getBlockMetadata(target.blockX, target.blockY, target.blockZ);
        if (md != 6 && md < 6) {
            RenderUtils.infusedStoneSparkle(worldObj, target.blockX, target.blockY, target.blockZ, md);
        }
        return super.addHitEffects(worldObj, target, effectRenderer);
    }
    
    public boolean addDestroyEffects(final World world, final int x, final int y, final int z, final int meta, final EffectRenderer effectRenderer) {
        return super.addDestroyEffects(world, x, y, z, meta, effectRenderer);
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess par1iBlockAccess, final int par2, final int par3, final int par4) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.setBlockBoundsBasedOnState(par1iBlockAccess, par2, par3, par4);
    }
    
    public void addCollisionBoxesToList(final World world, final int i, final int j, final int k, final AxisAlignedBB axisalignedbb, final List arraylist, final Entity par7Entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, par7Entity);
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int meta, final int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        if (meta == 6) {
            for (int q = 1 + world.rand.nextInt(fortune + 1), a = 0; a < q; ++a) {
                ret.add(ItemApi.getItem("itemResource", 6));
            }
        }
        else {
            for (int q = 1 + world.rand.nextInt(2 + fortune), a = 0; a < q; ++a) {
                ret.add(ItemApi.getItem("itemShard", meta));
            }
        }
        return ret;
    }
    
    public int getExpDrop(final IBlockAccess world, final int md, final int fortune) {
        if (md != 6) {
            return MathHelper.getRandomIntegerInRange(this.rand, 0, 3);
        }
        if (md == 6) {
            return MathHelper.getRandomIntegerInRange(this.rand, 1, 4);
        }
        return super.getExpDrop(world, md, fortune);
    }
    
    public boolean isSideSolid(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection side) {
        return true;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return ThaumicUtilities.netherOre_renderID;
    }
}
