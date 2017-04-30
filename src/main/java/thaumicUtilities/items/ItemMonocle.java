package thaumicUtilities.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.IGoggles;
import thaumcraft.api.nodes.IRevealer;
import thaumicUtilities.utils.ThaumcraftUtils;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMonocle extends Item implements IBauble, IRevealer, IGoggles {

	
    private IIcon icon;

    public ItemMonocle() {
        this.setMaxStackSize(1);
        this.setCreativeTab(ThaumcraftUtils.getThaumcraftTab());
    }
	
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
       this.icon = ir.registerIcon("thaumicdyes:ItemFabricResource"); //thisone

    }
    
    /*
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
      return this.icon;
    }*/
	
	
	
	@Override
	public boolean showNodes(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}
	
	@Override
	public boolean showIngamePopups(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.AMULET;
	}
	
	@Override
	public boolean canEquip(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack arg0, EntityLivingBase arg1) {
		return true;
	}

	@Override
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {
		
	}

	@Override
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {
		
	}

	@Override
	public void onWornTick(ItemStack arg0, EntityLivingBase arg1) {
		
	}

}
