package thaumicUtilities.tweaks;

import java.text.DecimalFormat;
import java.util.Iterator;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.ItemApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.relics.ItemSanityChecker;
import thaumcraft.common.lib.events.EventHandlerRunic;
import thaumcraft.common.lib.research.ResearchManager;
import thaumicUtilities.ModCompat;
import thaumicUtilities.settings.ConfigHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class VisAmuletTooltipTweaker
{
    static DecimalFormat formatter;
    
    @SubscribeEvent
    public void handleAmuletTooltip(final ItemTooltipEvent event) {
		if (display() && !GuiScreen.isShiftKeyDown() && (
        		event.itemStack.isItemEqual(ItemApi.getItem("itemAmuletVis", 0)) || 
        		event.itemStack.isItemEqual(ItemApi.getItem("itemAmuletVis", 1)) ||
        		//TODO FM support
        		(ModCompat.FMsubCollar!=null && event.itemStack.isItemEqual(new ItemStack(ModCompat.FMsubCollar)))
        		) && event.toolTip.size() > 2) {
            event.toolTip.set(2, visInformation(event.itemStack));
            event.itemStack.getEnchantmentTagList();
            
            final Iterator<String> iter = event.toolTip.iterator();
            while (iter.hasNext() ) {
                final String str = iter.next();
                if (event.toolTip.indexOf(str) > 2) {
                    iter.remove();
                }
            }
            
            //Add runic charge to alt tooltip
            int charge = EventHandlerRunic.getFinalCharge(event.itemStack);
            if (charge > 0) {
              event.toolTip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.runic.charge") + " +" + charge);
            }
        }
    }
    
    private static boolean display() {
        return ConfigHandler.tweakVisAmuletTooltip;
    }
    
    private static String visInformation(final ItemStack stack) {
        if (stack.hasTagCompound()) {
            String tt = "";
            final int tot = 0;
            int num = 0;
            for (final Aspect aspect : Aspect.getPrimalAspects()) {
                if (stack.stackTagCompound.hasKey(aspect.getTag())) {
                    final String amount = VisAmuletTooltipTweaker.formatter.format(stack.stackTagCompound.getInteger(aspect.getTag()) / 100.0f);
                    ++num;
                    final String text = "";
                    if (tt.length() > 0) {
                        tt += " | ";
                    }
                    tt = tt + "\u00a7" + aspect.getChatcolor() + amount + EnumChatFormatting.RESET;
                }
                else {
                    stack.stackTagCompound.setInteger(aspect.getTag(), 0);
                }
            }
            return tt;
        }
        return (EnumChatFormatting.YELLOW + "0 " + EnumChatFormatting.RESET + "| " + 
        		EnumChatFormatting.DARK_GREEN + "0 " + EnumChatFormatting.RESET + "| " + 
        		EnumChatFormatting.RED + "0 " + EnumChatFormatting.RESET + "| " + 
        		EnumChatFormatting.DARK_AQUA + "0 " + EnumChatFormatting.RESET + "| " + 
        		EnumChatFormatting.GRAY + "0 " + EnumChatFormatting.RESET + "| " + 
        		EnumChatFormatting.DARK_GRAY + "0 " + EnumChatFormatting.RESET         		
        		);
        		//"�e0 �r| �20 �r| �c0 �r| �30 �r| �70 �r| �80";
    }
    
    static {
        VisAmuletTooltipTweaker.formatter = new DecimalFormat("#######.##");
    }
    
    
    @SubscribeEvent
    public void handleSanityTooltip(final ItemTooltipEvent event) {
    	EntityPlayer player = event.entityPlayer;
		if (ConfigHandler.tweakSanityTooltip && 
				ResearchManager.isResearchComplete(player.getCommandSenderName(), "SANITYCHECK") &&
        		event.itemStack.isItemEqual(ItemApi.getItem("itemSanityChecker", 0)) &&
        		( player.inventory.getCurrentItem() != null && 
        		player.inventory.getCurrentItem().getItem() instanceof ItemSanityChecker)
				) {
			
			int permwarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(player.getCommandSenderName());
			int stickywarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpSticky(player.getCommandSenderName());
			int tempwarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpTemp(player.getCommandSenderName());
			
			int warp = permwarp+stickywarp+tempwarp;
			
			if (GuiScreen.isShiftKeyDown()) {
				if (tempwarp> 0 ) 
				event.toolTip.add(EnumChatFormatting.GRAY + " - " + tempwarp + " " + StatCollector.translateToLocal("util.warp.temp") );
				if (stickywarp > 0)
				event.toolTip.add(EnumChatFormatting.LIGHT_PURPLE + " - " + stickywarp + " " +  StatCollector.translateToLocal("util.warp.sticky") );
				if (permwarp > 0)
				event.toolTip.add(EnumChatFormatting.DARK_PURPLE + " - " + permwarp + " " +  StatCollector.translateToLocal("util.warp.perm") );

			} else {
				if (warp > 0) {
					event.toolTip.add(EnumChatFormatting.DARK_PURPLE + " - " + warp + " " +  StatCollector.translateToLocal("util.warp") );
					event.toolTip.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("util.shift") );
				}
			}
			
        }
    }
    
    
    @SubscribeEvent
    public void handleSanguineTooltip(final ItemTooltipEvent event) {
		if (display() /*&& !GuiScreen.isShiftKeyDown()*/ && (
        		//TODO BM support
        		(ModCompat.BMSanguineHelm!=null && event.itemStack.isItemEqual(new ItemStack(ModCompat.BMSanguineHelm))) ||
        		(ModCompat.BMSanguineChest!=null && event.itemStack.isItemEqual(new ItemStack(ModCompat.BMSanguineChest))) ||
        		(ModCompat.BMSanguineLegs!=null && event.itemStack.isItemEqual(new ItemStack(ModCompat.BMSanguineLegs))) ||
        		(ModCompat.BMSanguineBoots!=null && event.itemStack.isItemEqual(new ItemStack(ModCompat.BMSanguineBoots))) 
        		) && event.toolTip.size() > 2) {
            //event.toolTip.set(1, visInformation(event.itemStack));
            //event.itemStack.getEnchantmentTagList();
			EntityPlayer player = event.entityPlayer;
			int charge = getFinalVis(event.itemStack, player, null);
			//event.toolTip.remove(1);

            final Iterator<String> iter = event.toolTip.iterator();
            while (iter.hasNext() ) {
                final String str = iter.next();
                if (event.toolTip.indexOf(str) == 2 || event.toolTip.indexOf(str) == 3) {
                    iter.remove();
                }
            }
            
            event.toolTip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + charge + "%");

        }
    }
    
    public static int getFinalVis(ItemStack stack, EntityPlayer player, Aspect aspect)
    {
      if ((stack == null) || (!(stack.getItem() instanceof IVisDiscountGear))) {
        return 0;
      }
      IVisDiscountGear armor = (IVisDiscountGear)stack.getItem();
      return armor.getVisDiscount(stack, player, aspect);
    }
    
}
