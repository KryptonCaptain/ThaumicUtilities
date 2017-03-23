package com.saegusa.thu.tweaks;

import cpw.mods.fml.relauncher.*;
import java.text.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.client.gui.*;
import thaumcraft.api.*;
import java.util.*;
import cpw.mods.fml.common.eventhandler.*;
import com.saegusa.thu.settings.*;
import net.minecraft.item.*;
import thaumcraft.api.aspects.*;

@SideOnly(Side.CLIENT)
public class VisAmuletTooltipTweaker
{
    static DecimalFormat formatter;
    
    @SubscribeEvent
    public void handleAmuletTooltip(final ItemTooltipEvent event) {
        if (display() && !GuiScreen.isShiftKeyDown() && (event.itemStack.isItemEqual(ItemApi.getItem("itemAmuletVis", 0)) || event.itemStack.isItemEqual(ItemApi.getItem("itemAmuletVis", 1))) && event.toolTip.size() > 2) {
            event.toolTip.set(2, visInformation(event.itemStack));
            final Iterator<String> iter = event.toolTip.iterator();
            while (iter.hasNext()) {
                final String str = iter.next();
                if (event.toolTip.indexOf(str) > 2) {
                    iter.remove();
                }
            }
        }
    }
    
    private static boolean display() {
        return ConfigurationHandler.Settings.tweakVisAmuletTooltip;
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
                    tt = tt + "§" + aspect.getChatcolor() + amount + "§r";
                }
                else {
                    stack.stackTagCompound.setInteger(aspect.getTag(), 0);
                }
            }
            return tt;
        }
        return "§e0 §r| §20 §r| §c0 §r| §30 §r| §70 §r| §80"; //fixed bad characters
    }
    
    static {
        VisAmuletTooltipTweaker.formatter = new DecimalFormat("#######.##");
    }
}
