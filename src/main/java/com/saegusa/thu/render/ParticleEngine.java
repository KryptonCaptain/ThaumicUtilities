package com.saegusa.thu.render;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReportedException;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ActiveRenderInfo;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.particle.EntityFX;
import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;

public class ParticleEngine
{
    public static ParticleEngine instance;
    public static final ResourceLocation particleTexture;
    public static final ResourceLocation particleTexture2;
    protected World worldObj;
    private HashMap<Integer, ArrayList<EntityFX>>[] particles;
    
    public ParticleEngine() {
        this.particles = (HashMap<Integer, ArrayList<EntityFX>>[])new HashMap[] { new HashMap(), new HashMap(), new HashMap(), new HashMap() };
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        final float frame = event.partialTicks;
        final Entity entity = (Entity)Minecraft.getMinecraft().thePlayer;
        final TextureManager renderer = Minecraft.getMinecraft().renderEngine;
        final int dim = Minecraft.getMinecraft().theWorld.provider.dimensionId;
        renderer.bindTexture(ParticleEngine.particleTexture);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glAlphaFunc(516, 0.003921569f);
        boolean rebound = false;
        for (int layer = 0; layer < 4; ++layer) {
            if (this.particles[layer].containsKey(dim)) {
                final ArrayList<EntityFX> parts = this.particles[layer].get(dim);
                if (parts.size() != 0) {
                    if (!rebound && layer >= 2) {
                        renderer.bindTexture(ParticleEngine.particleTexture2);
                        rebound = true;
                    }
                    GL11.glPushMatrix();
                    switch (layer) {
                        case 0:
                        case 2: {
                            GL11.glBlendFunc(770, 1);
                            break;
                        }
                        case 1:
                        case 3: {
                            GL11.glBlendFunc(770, 771);
                            break;
                        }
                    }
                    final float f1 = ActiveRenderInfo.rotationX;
                    final float f2 = ActiveRenderInfo.rotationZ;
                    final float f3 = ActiveRenderInfo.rotationYZ;
                    final float f4 = ActiveRenderInfo.rotationXY;
                    final float f5 = ActiveRenderInfo.rotationXZ;
                    EntityFX.interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * frame;
                    EntityFX.interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * frame;
                    EntityFX.interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * frame;
                    final Tessellator tessellator = Tessellator.instance;
                    tessellator.startDrawingQuads();
                    for (int j = 0; j < parts.size(); ++j) {
                        final EntityFX entityfx = parts.get(j);
                        if (entityfx != null) {
                            tessellator.setBrightness(entityfx.getBrightnessForRender(frame));
                            try {
                                entityfx.renderParticle(tessellator, frame, f1, f5, f2, f3, f4);
                            }
                            catch (Throwable throwable) {
                                final CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
                                final CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
                                crashreportcategory.addCrashSectionCallable("Particle", (Callable)new Callable() {
                                    @Override
                                    public String call() {
                                        return entityfx.toString();
                                    }
                                });
                                crashreportcategory.addCrashSectionCallable("Particle Type", (Callable)new Callable() {
                                    @Override
                                    public String call() {
                                        return "ENTITY_PARTICLE_TEXTURE";
                                    }
                                });
                                throw new ReportedException(crashreport);
                            }
                        }
                    }
                    tessellator.draw();
                    GL11.glPopMatrix();
                }
            }
        }
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glPopMatrix();
    }
    
    public void addEffect(final World world, final EntityFX fx) {
        if (!this.particles[fx.getFXLayer()].containsKey(world.provider.dimensionId)) {
            this.particles[fx.getFXLayer()].put(world.provider.dimensionId, new ArrayList<EntityFX>());
        }
        final ArrayList<EntityFX> parts = this.particles[fx.getFXLayer()].get(world.provider.dimensionId);
        if (parts.size() >= 2000) {
            parts.remove(0);
        }
        parts.add(fx);
        this.particles[fx.getFXLayer()].put(world.provider.dimensionId, parts);
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void updateParticles(final TickEvent.ClientTickEvent event) {
        if (event.side == Side.SERVER) {
            return;
        }
        final Minecraft mc = FMLClientHandler.instance().getClient();
        final World world = (World)mc.theWorld;
        if (mc.theWorld == null) {
            return;
        }
        final int dim = world.provider.dimensionId;
        if (event.phase == TickEvent.Phase.START) {
            for (int layer = 0; layer < 4; ++layer) {
                if (this.particles[layer].containsKey(dim)) {
                    final ArrayList<EntityFX> parts = this.particles[layer].get(dim);
                    for (int j = 0; j < parts.size(); ++j) {
                        final EntityFX entityfx = parts.get(j);
                        try {
                            if (entityfx != null) {
                                entityfx.onUpdate();
                            }
                        }
                        catch (Throwable throwable) {
                            final CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
                            final CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being ticked");
                            crashreportcategory.addCrashSectionCallable("Particle", (Callable)new Callable() {
                                @Override
                                public String call() {
                                    return entityfx.toString();
                                }
                            });
                            crashreportcategory.addCrashSectionCallable("Particle Type", (Callable)new Callable() {
                                @Override
                                public String call() {
                                    return "ENTITY_PARTICLE_TEXTURE";
                                }
                            });
                            throw new ReportedException(crashreport);
                        }
                        if (entityfx == null || entityfx.isDead) {
                            parts.remove(j--);
                            this.particles[layer].put(dim, parts);
                        }
                    }
                }
            }
        }
    }
    
    static {
        ParticleEngine.instance = new ParticleEngine();
        particleTexture = new ResourceLocation("thaumcraft", "textures/misc/particles.png");
        particleTexture2 = new ResourceLocation("thaumcraft", "textures/misc/particles2.png");
    }
}
