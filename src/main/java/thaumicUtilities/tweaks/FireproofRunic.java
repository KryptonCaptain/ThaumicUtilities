package thaumicUtilities.tweaks;

import thaumcraft.common.Thaumcraft;
import thaumcraft.common.entities.monster.EntityEldritchGuardian;
import thaumcraft.common.lib.research.ResearchManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FireproofRunic {
	
	@SubscribeEvent
    public void onUpdate(LivingUpdateEvent event) {
		/*
        if (event.entityLiving != null && event.source != null ) {
            
            final EntityLivingBase living = event.entityLiving;
            final Entity source = event.source.getEntity();
            
            if (living instanceof EntityPlayer) {
            	
            	boolean hasResearch = ResearchManager.isResearchComplete(source.getCommandSenderName(), "ELDRITCHMINOR");
            	int permwarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(source.getCommandSenderName());
            	int actualwarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(source.getCommandSenderName()) + Thaumcraft.proxy.getPlayerKnowledge().getWarpSticky(source.getCommandSenderName());
                
                if (!hasResearch ) {
                    event.setCanceled(true);
                    ((EntityPlayer) source).addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("util.noHitGuardian")));
                }
            }
        }*/
		if (event.entityLiving != null) {
			if (event.entity instanceof EntityPlayerMP && (!(event.entity instanceof FakePlayer))) {
	        	EntityPlayer player = (EntityPlayer) event.entity;
	        	
	        	int total = ((Integer[])Thaumcraft.instance.runicEventHandler.runicInfo.get(Integer.valueOf(player.getEntityId())))[0].intValue();
	            int current = ((Integer)Thaumcraft.instance.runicEventHandler.runicCharge.get(Integer.valueOf(player.getEntityId()))).intValue();
	            
	            if (total > 0 && current > 0 && /*event.source == DamageSource.onFire &&*/ player.isBurning()) {
	            	player.extinguish();
	            } 
			}
		}
        
    }

}
