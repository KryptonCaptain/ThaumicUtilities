package thaumicUtilities.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.IRunicArmor;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.baubles.ItemAmuletVis;
import thaumicUtilities.utils.ThaumcraftUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAmulet extends ItemAmuletVis implements IRunicArmor{

	public IIcon icon;
	
    public ItemAmulet() {
        this.maxStackSize = 1;
        this.setCreativeTab(ThaumcraftUtils.getThaumcraftTab());
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister ir) {
        this.icon = ir.registerIcon("thaumcraft:vis_amulet_lesser");
    }
	
    public int getMaxVis(ItemStack stack) {
        return 5000;
    }
    
    
    private Aspect[] aspects = {Aspect.ORDER, Aspect.ENTROPY, Aspect.EARTH, Aspect.WATER, Aspect.AIR, Aspect.FIRE};
    
    public void onUpdate(ItemStack itemstack, EntityPlayer player) {
		if (player.ticksExisted % 100 == 0) {
			List lowAspects = new ArrayList();
			for (int i=0; i<6; i++) {
				double visCount = ((ItemAmulet) itemstack.getItem()).getVis(itemstack, this.aspects[i]);
				double cutoffPercent = (((((ItemAmulet) itemstack.getItem()).getMaxVis(itemstack))));
				if (visCount < cutoffPercent) {
					lowAspects.add(aspects[i]);
				}
			}
			if (lowAspects.size() > 0) {
				for (int i=0; i<lowAspects.size(); i++) {
					((ItemAmulet) itemstack.getItem()).addVis(itemstack, (Aspect) lowAspects.get(i), 1, true);
				}
			}	
		
		}

}

	
	
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}
	
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}
	
	public int getRunicCharge(ItemStack itemstack)
	{
	  return 0;
	}
}
