package com.saegusa.thu.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.ItemApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.Thaumcraft;
import baubles.api.BaublesApi;

import com.saegusa.thu.settings.ConfigHandler;
import com.saegusa.thu.settings.ConfigHandler.ThaumcraftSettings;
import com.saegusa.thu.utils.RenderUtils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiVisStorage extends Gui
{
    int mHeight;
    int mWidth;
    //private static final String AMULET = "itemAmuletVis";
    final ResourceLocation HUD;
    RenderItem ri;
    
    public GuiVisStorage(final Minecraft mc) {
        this.HUD = new ResourceLocation("thaumcraft", "textures/gui/hud.png");
        this.ri = new RenderItem();
        final ScaledResolution scaled = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        final int width = scaled.getScaledWidth();
        final int height = scaled.getScaledHeight();
        this.mHeight = scaled.getScaledHeight();
        this.mWidth = scaled.getScaledWidth();
        final long time = System.currentTimeMillis();
        int aer = 0;
        int terra = 0;
        int aqua = 0;
        int ignis = 0;
        int ordo = 0;
        int perditio = 0;
        if (!this.isAmuletInBaubles((EntityPlayer)mc.thePlayer) && !this.isAmuletInInventory((EntityPlayer)mc.thePlayer)) {
            return;
        }
        ItemStack amulet;
        if (this.isAmuletInBaubles((EntityPlayer)mc.thePlayer)) {
            amulet = this.getAmuletFromInventory(BaublesApi.getBaubles((EntityPlayer)mc.thePlayer));
        }
        else {
            if (!this.isAmuletInInventory((EntityPlayer)mc.thePlayer)) {
                return;
            }
            amulet = this.getAmuletFromInventory((IInventory)mc.thePlayer.inventory);
        }
        if (amulet != null) {
            if (ConfigHandler.displayVisOnShiftDown && !mc.thePlayer.isSneaking()) {
                return;
            }
            if (mc.inGameHasFocus && Minecraft.isGuiEnabled()) {
            	if (ConfigHandler.useOldTextHUD) {
                    aer = this.getVis(amulet, Aspect.AIR) / 100;
                    terra = this.getVis(amulet, Aspect.EARTH) / 100;
                    aqua = this.getVis(amulet, Aspect.WATER) / 100;
                    ignis = this.getVis(amulet, Aspect.FIRE) / 100;
                    ordo = this.getVis(amulet, Aspect.ORDER) / 100;
                    perditio = this.getVis(amulet, Aspect.ENTROPY) / 100;
                    this.render(mc, aer, terra, aqua, ignis, ordo, perditio);
            	}
            	else {
            		this.renderVisStorage(mc, amulet);
            	}
                
            }
        }
        
        EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
        if ((!player.capabilities.isCreativeMode) && (Thaumcraft.instance.runicEventHandler.runicCharge.containsKey(Integer.valueOf(player.getEntityId()))) && (((Integer)Thaumcraft.instance.runicEventHandler.runicCharge.get(Integer.valueOf(player.getEntityId()))).intValue() > 0) && (Thaumcraft.instance.runicEventHandler.runicInfo.containsKey(Integer.valueOf(player.getEntityId())))) {
            renderRunicArmorBar(player);
          }
        
    }
    
    private boolean checkBaubles() {
        return Loader.isModLoaded("Baubles");
    }
    
    private boolean isAmuletInBaubles(final EntityPlayer player) {
        if (this.checkBaubles()) {
            final IInventory babs = BaublesApi.getBaubles(player);
            for (int i = 0; i < babs.getSizeInventory(); ++i) { 
        		if (babs.getStackInSlot(i) != null && (
                		babs.getStackInSlot(i).isItemEqual(ItemApi.getItem("itemAmuletVis", 1)) || 
                		babs.getStackInSlot(i).isItemEqual(ItemApi.getItem("itemAmuletVis", 0))
                		)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isAmuletInInventory(final EntityPlayer player) { 
    		return player.inventory.hasItemStack(ItemApi.getItem("itemAmuletVis", 1)) || 
            		player.inventory.hasItemStack(ItemApi.getItem("itemAmuletVis", 0));
        
    }
    
    private ItemStack getAmuletFromInventory(final IInventory inventory) { 
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
    		if (inventory.getStackInSlot(i) != null && (
            		inventory.getStackInSlot(i).isItemEqual(ItemApi.getItem("itemAmuletVis", 1)) ||
            		inventory.getStackInSlot(i).isItemEqual(ItemApi.getItem("itemAmuletVis", 0)))) {
                return inventory.getStackInSlot(i);
            }            
        }
        return null;
    }
    
    private void render(final Minecraft mc, final int aer, final int terra, final int ignis, final int aqua, final int ordo, final int perditio) {
        if (mc.thePlayer.isSneaking()) {
        	
        	final ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            final int l = sr.getScaledHeight();
            final int dialLocation = ConfigHandler.ThaumcraftSettings.wandDialBottom ? 0 : (l - 32);
            EntityPlayer player = mc.thePlayer;
        	
        	if (!ThaumcraftSettings.wandDialBottom) {
                this.drawString(mc.fontRenderer, EnumChatFormatting.YELLOW + "Aer: " + EnumChatFormatting.WHITE + String.valueOf(aer), 4, this.mHeight - 62, 99);
                this.drawString(mc.fontRenderer, EnumChatFormatting.GREEN + "Terra: " + EnumChatFormatting.WHITE + String.valueOf(terra), 4, this.mHeight - 52, 99);
                this.drawString(mc.fontRenderer, EnumChatFormatting.RED + "Ignis: " + EnumChatFormatting.WHITE + String.valueOf(ignis), 4, this.mHeight - 42, 99);
                this.drawString(mc.fontRenderer, EnumChatFormatting.AQUA + "Aqua: " + EnumChatFormatting.WHITE + String.valueOf(aqua), 4, this.mHeight - 32, 99);
                this.drawString(mc.fontRenderer, EnumChatFormatting.GRAY + "Ordo: " + EnumChatFormatting.WHITE + String.valueOf(ordo), 4, this.mHeight - 22, 99);
                this.drawString(mc.fontRenderer, EnumChatFormatting.DARK_GRAY + "Perditio: " + EnumChatFormatting.WHITE + String.valueOf(perditio), 4, this.mHeight - 12, 99);
        	}
        	else {
        		this.drawString(mc.fontRenderer, EnumChatFormatting.YELLOW + "Aer: " + EnumChatFormatting.WHITE + String.valueOf(aer), 4, dialLocation + 12, 99);                
                this.drawString(mc.fontRenderer, EnumChatFormatting.GREEN + "Terra: " + EnumChatFormatting.WHITE + String.valueOf(terra), 4, dialLocation + 22, 99);
                this.drawString(mc.fontRenderer, EnumChatFormatting.RED + "Ignis: " + EnumChatFormatting.WHITE + String.valueOf(ignis), 4, dialLocation + 32, 99);
                this.drawString(mc.fontRenderer, EnumChatFormatting.AQUA + "Aqua: " + EnumChatFormatting.WHITE + String.valueOf(aqua), 4, dialLocation + 42, 99);
                this.drawString(mc.fontRenderer, EnumChatFormatting.GRAY + "Ordo: " + EnumChatFormatting.WHITE + String.valueOf(ordo), 4, dialLocation + 52, 99);
                this.drawString(mc.fontRenderer, EnumChatFormatting.DARK_GRAY + "Perditio: " + EnumChatFormatting.WHITE + String.valueOf(perditio), 4, dialLocation + 62, 99);
        	}
        	        	
        }
    }
    
    @SideOnly(Side.CLIENT)
    private void renderVisStorage(final Minecraft mc, final ItemStack amulet) {
        float transparency;
        if (mc.thePlayer.isSneaking()) {
            transparency = 1.0f;
        }
        else {
            transparency = 0.4f;
        }
        GL11.glPushMatrix();
        final ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, sr.getScaledWidth_double(), sr.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        final int k = sr.getScaledWidth();
        final int l = sr.getScaledHeight();
        final int dialLocation = ConfigHandler.ThaumcraftSettings.wandDialBottom ? 0 : (l - 32);
        GL11.glTranslatef(0.0f, (float)dialLocation, -2000.0f); //fixed being 2px out
        GL11.glColor4f(1.0f, 1.0f, 1.0f, transparency);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        mc.renderEngine.bindTexture(this.HUD);
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 0.5);
        RenderUtils.drawTexturedQuad(0, 0, 0, 0, 64, 64, -90.0);
        GL11.glPopMatrix();
        GL11.glTranslatef(16.0f, 16.0f, 0.0f);
        int max;
        if (amulet.isItemEqual(ItemApi.getItem("itemAmuletVis", 1))) {
            max = 25000;
        }
        else {
            max = 2500;
        }
        int count = 0;
        final AspectList aspects = this.getAllVis(amulet);
        for (final Aspect aspect : aspects.getAspects()) {
            final int amt = aspects.getAmount(aspect);
            GL11.glPushMatrix();
            if (ConfigHandler.ThaumcraftSettings.wandDialBottom) {
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glRotatef((float)(-15 + count * 24), 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(0.0f, -26.0f, 0.0f);
            GL11.glScaled(0.5, 0.5, 0.5);
            final int loc = (int)(34.0f * amt / max);
            GL11.glPushMatrix();
            final Color ac = new Color(aspect.getColor());
            GL11.glColor4f(ac.getRed() / 255.0f, ac.getGreen() / 255.0f, ac.getBlue() / 255.0f, transparency);
            RenderUtils.drawTexturedQuad(-4, 24 - loc, 104, 0, 8, loc, -90.0);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, transparency);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            RenderUtils.drawTexturedQuad(-8, -18, 72, 0, 16, 42, -90.0);
            GL11.glPopMatrix();
            final int sh = 0;
            if (mc.thePlayer.isSneaking()) {
                GL11.glPushMatrix();
                GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                final String msg = amt / 100 + "";
                mc.ingameGUI.drawString(mc.fontRenderer, msg, -18, -4, 16777215);
                GL11.glPopMatrix();
                mc.renderEngine.bindTexture(this.HUD);
            }
            GL11.glPopMatrix();
            ++count;
        }
        this.renderAmuletIcon(mc, (EntityPlayer)mc.thePlayer, amulet);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    @SideOnly(Side.CLIENT)
    public void renderAmuletIcon(final Minecraft mc, final EntityPlayer player, final ItemStack amulet) {
        if (amulet != null) {
            GL11.glPushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2903);
            GL11.glEnable(2896);
            try {
                this.ri.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, amulet, -8, -8);
            }
            catch (Exception ex) {}
            GL11.glDisable(2896);
            GL11.glPopMatrix();
        }
    }
    
    private int getVis(final ItemStack is, final Aspect aspect) {
        int out = 0;
        if (is.hasTagCompound() && is.stackTagCompound.hasKey(aspect.getTag())) {
            out = is.stackTagCompound.getInteger(aspect.getTag());
        }
        return out;
    }
    
    public AspectList getAllVis(final ItemStack is) {
        final AspectList out = new AspectList();
        for (final Aspect aspect : Aspect.getPrimalAspects()) {
            if (is.hasTagCompound() && is.stackTagCompound.hasKey(aspect.getTag())) {
                out.merge(aspect, is.stackTagCompound.getInteger(aspect.getTag()));
            }
            else {
                out.merge(aspect, 0);
            }
        }
        return out;
    }
    
    
    @SideOnly(Side.CLIENT)
    public void renderRunicArmorBar(EntityPlayer player)
    {
      Minecraft mc = Minecraft.getMinecraft();
      int total = ((Integer[])Thaumcraft.instance.runicEventHandler.runicInfo.get(Integer.valueOf(player.getEntityId())))[0].intValue();
      int current = ((Integer)Thaumcraft.instance.runicEventHandler.runicCharge.get(Integer.valueOf(player.getEntityId()))).intValue();
      
      this.drawCenteredString(mc.fontRenderer, EnumChatFormatting.GOLD + String.valueOf(current) + EnumChatFormatting.GOLD + " / " + EnumChatFormatting.GOLD + String.valueOf(total), (this.mWidth / 2)-113, this.mHeight - 39, 99);
    }
}
