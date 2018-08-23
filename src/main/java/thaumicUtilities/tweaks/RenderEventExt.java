package thaumicUtilities.tweaks;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.IGoggles;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.misc.PacketNote;
import thaumcraft.common.tiles.TileInfusionMatrix;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

public class RenderEventExt {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void blockHighlight(DrawBlockHighlightEvent event) {
		int ticks = event.player.ticksExisted;
		MovingObjectPosition target = event.target;
	    
		if ((event.player.inventory.armorItemInSlot(3) != null) && ((event.player.inventory.armorItemInSlot(3).getItem() instanceof IGoggles)) && (((IGoggles)event.player.inventory.armorItemInSlot(3).getItem()).showIngamePopups(event.player.inventory.armorItemInSlot(3), event.player)))
	    {
			boolean spaceAbove = event.player.worldObj.isAirBlock(target.blockX, target.blockY + 1, target.blockZ);
			TileEntity te = event.player.worldObj.getTileEntity(target.blockX, target.blockY, target.blockZ);
			if (te != null) {
				/*
				int note = -1;
				if ((te instanceof TileInfusionMatrix)) {
					note = ((TileInfusionMatrix)te).instability;
				}
				
		        if (note >= 0)
		        {
		          if (ticks % 5 == 0) {
		            PacketHandler.INSTANCE.sendToServer(new PacketNote(target.blockX, target.blockY, target.blockZ, event.player.worldObj.provider.dimensionId));
		          }
		          drawTextInAir(target.blockX, target.blockY + 1, target.blockZ, event.partialTicks, StatCollector.translateToLocal("tc.inst."+note));
		        }*/
				if ((te instanceof TileInfusionMatrix)) {
					int inst = Math.min(5, ((TileInfusionMatrix)te).instability / 2);
					if (inst > 5)
						inst = 5;
					String s = StatCollector.translateToLocal("stability." + inst);
		            GL11.glDisable(2929);
		            drawTextInAir(target.blockX, target.blockY + 0.1D, target.blockZ, event.partialTicks, s);
		            GL11.glEnable(2929);
				}
			}
	    }
	    
	}
	 
	public void drawTextInAir(double x, double y, double z, float partialTicks, String text) {
		if ((Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer)) {
			EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
			double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
			double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
			
			GL11.glPushMatrix();
			
			GL11.glTranslated(-iPX + x + 0.5D, -iPY + y + 0.5D, -iPZ + z + 0.5D);
			
			float xd = (float)(iPX - (x + 0.5D));
			float zd = (float)(iPZ - (z + 0.5D));
			float rotYaw = (float)(Math.atan2(xd, zd) * 180.0D / 3.141592653589793D);
			
			GL11.glRotatef(rotYaw + 180.0F, 0.0F, 1.0F, 0.0F);
			
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glScalef(0.02F, 0.02F, 0.02F);
			int sw = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
			GL11.glEnable(3042);
			Minecraft.getMinecraft().fontRenderer.drawString(text, 1 - sw / 2, 1, 1118481);
			GL11.glTranslated(0.0D, 0.0D, -0.1D);
			Minecraft.getMinecraft().fontRenderer.drawString(text, -sw / 2, 0, 16777215);
			
			GL11.glPopMatrix();
		}
	}
}
