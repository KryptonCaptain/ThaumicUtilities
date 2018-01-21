package thaumicUtilities.tweaks;

import thaumcraft.common.Thaumcraft;
import thaumcraft.common.entities.monster.EntityEldritchGuardian;
import thaumcraft.common.lib.research.ResearchManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

public class DontNoticeMeGuardianSenpai {
	
	@SubscribeEvent
    public void onEntityLiving(final LivingEvent event) {
        this.doMobCheck(event);
    }
    
    @SubscribeEvent
    public void onEntityTargetedEvent(final LivingSetAttackTargetEvent event) {
        this.doMobCheck(event);
    }
    //totally didn't take all this from reliquary...
    public void doMobCheck(final LivingSetAttackTargetEvent event) {
        if (event.target == null) {
            return;
        }
        if (!(event.target instanceof EntityPlayer)) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)event.target;
        this.doGuardianCheck(event.entity, player);

    }
    
    public void doMobCheck(final LivingEvent event) {
        if (event.entity instanceof EntityLiving) {
            final EntityLiving entityLiving = (EntityLiving)event.entity;
            if (entityLiving.getAttackTarget() == null) {
                return;
            }
            if (!(entityLiving.getAttackTarget() instanceof EntityPlayer)) {
                return;
            }
            final EntityPlayer player = (EntityPlayer)entityLiving.getAttackTarget();
            this.doGuardianCheck(event.entity, player);
        }
    }
    
    
    public void doGuardianCheck(final Entity e, final EntityPlayer p) {
    	boolean hasResearch = ResearchManager.isResearchComplete(p.getCommandSenderName(), "ELDRITCHMAJOR");
    	int permwarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(p.getCommandSenderName());
    	int actualwarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(p.getCommandSenderName()) + Thaumcraft.proxy.getPlayerKnowledge().getWarpSticky(p.getCommandSenderName());

        if (e instanceof EntityEldritchGuardian && (!hasResearch || actualwarp < 50) ) {
            ((EntityEldritchGuardian)e).setAttackTarget((EntityLivingBase)null);
            //((EntityEldritchGuardian)e).setTarget((Entity)null);
            //((EntityEldritchGuardian)e).setRevengeTarget((EntityLivingBase)null); //what, you think there's no consequences?
        }
    }
    
    
    
    @SubscribeEvent
    public void onMobHit(LivingHurtEvent event) {
        if (event.entityLiving != null && event.source != null && event.source.getEntity() != null) {
            
            final EntityLivingBase living = event.entityLiving;
            final Entity source = event.source.getEntity();
            
            if (source instanceof EntityPlayer && living instanceof EntityEldritchGuardian) {
            	
            	boolean hasResearch = ResearchManager.isResearchComplete(source.getCommandSenderName(), "ELDRITCHMINOR");
            	int permwarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(source.getCommandSenderName());
            	int actualwarp = Thaumcraft.proxy.getPlayerKnowledge().getWarpPerm(source.getCommandSenderName()) + Thaumcraft.proxy.getPlayerKnowledge().getWarpSticky(source.getCommandSenderName());
                
                if (!hasResearch ) {
                    event.setCanceled(true);
                    ((EntityPlayer) source).addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("util.noHitGuardian")));
                }
            }
        }
    }

}
