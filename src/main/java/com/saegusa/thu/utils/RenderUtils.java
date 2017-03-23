package com.saegusa.thu.utils;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import com.saegusa.thu.ThaumicUtilities;
import com.saegusa.thu.client.fx.FXSparkle;
import com.saegusa.thu.render.ParticleEngine;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class RenderUtils
{
    public static final String[] colorNames;
    public static final String[] colorCodes;
    public static final int[] colors;
    public static int[] connectedTextureRefByID;
    public static float[] lightBrightnessTable;
    private static Map textureSizeCache;
    static Map<String, ResourceLocation> boundTextures;
    static DecimalFormat myFormatter;
    
    public static float getBrightnessFromLight(final int light) {
        if (RenderUtils.lightBrightnessTable == null) {
            RenderUtils.lightBrightnessTable = new float[16];
            final float f = 0.0f;
            for (int i = 0; i <= 15; ++i) {
                final float f2 = 1.0f - i / 15.0f;
                RenderUtils.lightBrightnessTable[i] = (1.0f - f2) / (f2 * 3.0f + 1.0f) * (1.0f - f) + f;
            }
        }
        return RenderUtils.lightBrightnessTable[light];
    }
    
    public static void renderFacingQuad(final double px, final double py, final double pz, final float angle, final float scale, final float alpha, final int frames, final int cframe, final float partialTicks, final int color) {
        if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
            final Tessellator tessellator = Tessellator.instance;
            final float arX = ActiveRenderInfo.rotationX;
            final float arZ = ActiveRenderInfo.rotationZ;
            final float arYZ = ActiveRenderInfo.rotationYZ;
            final float arXY = ActiveRenderInfo.rotationXY;
            final float arXZ = ActiveRenderInfo.rotationXZ;
            final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
            final double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
            final double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
            final double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
            GL11.glTranslated(-iPX, -iPY, -iPZ);
            tessellator.startDrawingQuads();
            tessellator.setBrightness(220);
            tessellator.setColorRGBA_I(color, (int)(alpha * 255.0f));
            final Vec3 v1 = Vec3.createVectorHelper((double)(-arX * scale - arYZ * scale), (double)(-arXZ * scale), (double)(-arZ * scale - arXY * scale));
            final Vec3 v2 = Vec3.createVectorHelper((double)(-arX * scale + arYZ * scale), (double)(arXZ * scale), (double)(-arZ * scale + arXY * scale));
            final Vec3 v3 = Vec3.createVectorHelper((double)(arX * scale + arYZ * scale), (double)(arXZ * scale), (double)(arZ * scale + arXY * scale));
            final Vec3 v4 = Vec3.createVectorHelper((double)(arX * scale - arYZ * scale), (double)(-arXZ * scale), (double)(arZ * scale - arXY * scale));
            if (angle != 0.0f) {
                final Vec3 pvec = Vec3.createVectorHelper(iPX, iPY, iPZ);
                final Vec3 tvec = Vec3.createVectorHelper(px, py, pz);
                final Vec3 qvec = pvec.subtract(tvec).normalize();
                QuadHelper.setAxis(qvec, angle).rotate(v1);
                QuadHelper.setAxis(qvec, angle).rotate(v2);
                QuadHelper.setAxis(qvec, angle).rotate(v3);
                QuadHelper.setAxis(qvec, angle).rotate(v4);
            }
            final float f2 = cframe / frames;
            final float f3 = (cframe + 1) / frames;
            final float f4 = 0.0f;
            final float f5 = 1.0f;
            tessellator.setNormal(0.0f, 0.0f, -1.0f);
            tessellator.addVertexWithUV(px + v1.xCoord, py + v1.yCoord, pz + v1.zCoord, (double)f2, (double)f5);
            tessellator.addVertexWithUV(px + v2.xCoord, py + v2.yCoord, pz + v2.zCoord, (double)f3, (double)f5);
            tessellator.addVertexWithUV(px + v3.xCoord, py + v3.yCoord, pz + v3.zCoord, (double)f3, (double)f4);
            tessellator.addVertexWithUV(px + v4.xCoord, py + v4.yCoord, pz + v4.zCoord, (double)f2, (double)f4);
            tessellator.draw();
        }
    }
    
    public static void renderFacingStrip(final double px, final double py, final double pz, final float angle, final float scale, final float alpha, final int frames, final int strip, final int frame, final float partialTicks, final int color) {
        if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
            final Tessellator tessellator = Tessellator.instance;
            final float arX = ActiveRenderInfo.rotationX;
            final float arZ = ActiveRenderInfo.rotationZ;
            final float arYZ = ActiveRenderInfo.rotationYZ;
            final float arXY = ActiveRenderInfo.rotationXY;
            final float arXZ = ActiveRenderInfo.rotationXZ;
            final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
            final double iPX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
            final double iPY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
            final double iPZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;
            GL11.glTranslated(-iPX, -iPY, -iPZ);
            tessellator.startDrawingQuads();
            tessellator.setBrightness(220);
            tessellator.setColorRGBA_I(color, (int)(alpha * 255.0f));
            final Vec3 v1 = Vec3.createVectorHelper((double)(-arX * scale - arYZ * scale), (double)(-arXZ * scale), (double)(-arZ * scale - arXY * scale));
            final Vec3 v2 = Vec3.createVectorHelper((double)(-arX * scale + arYZ * scale), (double)(arXZ * scale), (double)(-arZ * scale + arXY * scale));
            final Vec3 v3 = Vec3.createVectorHelper((double)(arX * scale + arYZ * scale), (double)(arXZ * scale), (double)(arZ * scale + arXY * scale));
            final Vec3 v4 = Vec3.createVectorHelper((double)(arX * scale - arYZ * scale), (double)(-arXZ * scale), (double)(arZ * scale - arXY * scale));
            if (angle != 0.0f) {
                final Vec3 pvec = Vec3.createVectorHelper(iPX, iPY, iPZ);
                final Vec3 tvec = Vec3.createVectorHelper(px, py, pz);
                final Vec3 qvec = pvec.subtract(tvec).normalize();
                QuadHelper.setAxis(qvec, angle).rotate(v1);
                QuadHelper.setAxis(qvec, angle).rotate(v2);
                QuadHelper.setAxis(qvec, angle).rotate(v3);
                QuadHelper.setAxis(qvec, angle).rotate(v4);
            }
            final float f2 = frame / frames;
            final float f3 = (frame + 1) / frames;
            final float f4 = strip / frames;
            final float f5 = (strip + 1.0f) / frames;
            tessellator.setNormal(0.0f, 0.0f, -1.0f);
            tessellator.addVertexWithUV(px + v1.xCoord, py + v1.yCoord, pz + v1.zCoord, (double)f3, (double)f5);
            tessellator.addVertexWithUV(px + v2.xCoord, py + v2.yCoord, pz + v2.zCoord, (double)f3, (double)f4);
            tessellator.addVertexWithUV(px + v3.xCoord, py + v3.yCoord, pz + v3.zCoord, (double)f2, (double)f4);
            tessellator.addVertexWithUV(px + v4.xCoord, py + v4.yCoord, pz + v4.zCoord, (double)f2, (double)f5);
            tessellator.draw();
        }
    }
    
    public static void renderAnimatedQuad(final float scale, final float alpha, final int frames, final int cframe, final float partialTicks, final int color) {
        if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
            final Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setBrightness(220);
            tessellator.setColorRGBA_I(color, (int)(alpha * 255.0f));
            final float f2 = cframe / frames;
            final float f3 = (cframe + 1) / frames;
            final float f4 = 0.0f;
            final float f5 = 1.0f;
            tessellator.setNormal(0.0f, 0.0f, -1.0f);
            tessellator.addVertexWithUV(-0.5 * scale, 0.5 * scale, 0.0, (double)f2, (double)f5);
            tessellator.addVertexWithUV(0.5 * scale, 0.5 * scale, 0.0, (double)f3, (double)f5);
            tessellator.addVertexWithUV(0.5 * scale, -0.5 * scale, 0.0, (double)f3, (double)f4);
            tessellator.addVertexWithUV(-0.5 * scale, -0.5 * scale, 0.0, (double)f2, (double)f4);
            tessellator.draw();
        }
    }
    
    public static void renderAnimatedQuadStrip(final float scale, final float alpha, final int frames, final int strip, final int cframe, final float partialTicks, final int color) {
        if (Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer) {
            final Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setBrightness(220);
            tessellator.setColorRGBA_I(color, (int)(alpha * 255.0f));
            final float f2 = cframe / frames;
            final float f3 = (cframe + 1) / frames;
            final float f4 = strip / frames;
            final float f5 = (strip + 1) / frames;
            tessellator.setNormal(0.0f, 0.0f, -1.0f);
            tessellator.addVertexWithUV(-0.5 * scale, 0.5 * scale, 0.0, (double)f2, (double)f5);
            tessellator.addVertexWithUV(0.5 * scale, 0.5 * scale, 0.0, (double)f3, (double)f5);
            tessellator.addVertexWithUV(0.5 * scale, -0.5 * scale, 0.0, (double)f3, (double)f4);
            tessellator.addVertexWithUV(-0.5 * scale, -0.5 * scale, 0.0, (double)f2, (double)f4);
            tessellator.draw();
        }
    }
    
    public static Vec3 perpendicular(final Vec3 v) {
        if (v.zCoord == 0.0) {
            return zCrossProduct(v);
        }
        return xCrossProduct(v);
    }
    
    public static Vec3 xCrossProduct(final Vec3 v) {
        final double d = v.zCoord;
        final double d2 = -v.yCoord;
        v.xCoord = 0.0;
        v.yCoord = d;
        v.zCoord = d2;
        return v;
    }
    
    public static Vec3 zCrossProduct(final Vec3 v) {
        final double d = v.yCoord;
        final double d2 = -v.xCoord;
        v.xCoord = d;
        v.yCoord = d2;
        v.zCoord = 0.0;
        return v;
    }
    
    public static void drawTexturedQuad(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6, final double zLevel) {
        final float var7 = 0.00390625f;
        final float var8 = 0.00390625f;
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), zLevel, (double)((par3 + 0) * var7), (double)((par4 + par6) * var8));
        var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), zLevel, (double)((par3 + par5) * var7), (double)((par4 + par6) * var8));
        var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), zLevel, (double)((par3 + par5) * var7), (double)((par4 + 0) * var8));
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), zLevel, (double)((par3 + 0) * var7), (double)((par4 + 0) * var8));
        var9.draw();
    }
    
    public static void drawTexturedQuadFull(final int par1, final int par2, final double zLevel) {
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + 16), zLevel, 0.0, 1.0);
        var9.addVertexWithUV((double)(par1 + 16), (double)(par2 + 16), zLevel, 1.0, 1.0);
        var9.addVertexWithUV((double)(par1 + 16), (double)(par2 + 0), zLevel, 1.0, 0.0);
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), zLevel, 0.0, 0.0);
        var9.draw();
    }
    
    public static void renderQuad(final String texture) {
        renderQuad(texture, 1, 0.66f);
    }
    
    public static void renderQuad(final String texture, final int blend, final float trans) {
        renderQuad(texture, blend, trans, 1.0f, 1.0f, 1.0f);
    }
    
    public static void renderQuad(final String texture, final int blend, final float trans, final float r, final float g, final float b) {
        bindTexture(texture);
        final Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(32826);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, blend);
        GL11.glColor4f(r, g, b, trans);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(r, g, b, trans);
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, 0.0, 1.0);
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, 1.0, 1.0);
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, 1.0, 0.0);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDisable(32826);
    }
    
    public static void renderQuadCenteredFromTexture(final String texture, final float scale, final float red, final float green, final float blue, final int brightness, final int blend, final float opacity) {
        bindTexture(texture);
        renderQuadCenteredFromTexture(scale, red, green, blue, brightness, blend, opacity);
    }
    
    public static void renderQuadCenteredFromTexture(final ResourceLocation texture, final float scale, final float red, final float green, final float blue, final int brightness, final int blend, final float opacity) {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        renderQuadCenteredFromTexture(scale, red, green, blue, brightness, blend, opacity);
    }
    
    public static void renderQuadCenteredFromTexture(final float scale, final float red, final float green, final float blue, final int brightness, final int blend, final float opacity) {
        final Tessellator tessellator = Tessellator.instance;
        GL11.glScalef(scale, scale, scale);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, blend);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, opacity);
        tessellator.startDrawingQuads();
        if (brightness > 0) {
            tessellator.setBrightness(brightness);
        }
        tessellator.setColorRGBA_F(red, green, blue, opacity);
        tessellator.addVertexWithUV(-0.5, 0.5, 0.0, 0.0, 1.0);
        tessellator.addVertexWithUV(0.5, 0.5, 0.0, 1.0, 1.0);
        tessellator.addVertexWithUV(0.5, -0.5, 0.0, 1.0, 0.0);
        tessellator.addVertexWithUV(-0.5, -0.5, 0.0, 0.0, 0.0);
        tessellator.draw();
        GL11.glDisable(3042);
    }
    
    public static void renderQuadFromTexture(final String texture, final int tileSize, final int icon, final float scale, final float red, final float green, final float blue, final int brightness, final int blend, final float opacity) {
        bindTexture(texture);
        final int size = getTextureSize(texture, tileSize);
        final float size2 = size * tileSize;
        final float float_sizeMinus0_01 = size - 0.01f;
        final float float_texNudge = 1.0f / (size * size * 2.0f);
        final float float_reciprocal = 1.0f / size;
        final Tessellator tessellator = Tessellator.instance;
        final float f = (icon % tileSize * size + 0.0f) / size2;
        final float f2 = (icon % tileSize * size + float_sizeMinus0_01) / size2;
        final float f3 = (icon / tileSize * size + 0.0f) / size2;
        final float f4 = (icon / tileSize * size + float_sizeMinus0_01) / size2;
        final float f5 = 0.0f;
        final float f6 = 0.3f;
        GL11.glEnable(32826);
        GL11.glScalef(scale, scale, scale);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, blend);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, opacity);
        tessellator.startDrawingQuads();
        tessellator.setBrightness(brightness);
        tessellator.setColorRGBA_F(red, green, blue, opacity);
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)f2, (double)f3);
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)f, (double)f3);
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)f, (double)f4);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)f2, (double)f4);
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDisable(32826);
    }
    
    public static void renderQuadFromIcon(final boolean isBlock, final IIcon icon, final float scale, final float red, final float green, final float blue, final int brightness, final int blend, final float opacity) {
        if (isBlock) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        }
        else {
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
        }
        final Tessellator tessellator = Tessellator.instance;
        final float f1 = icon.getMaxU();
        final float f2 = icon.getMinV();
        final float f3 = icon.getMinU();
        final float f4 = icon.getMaxV();
        GL11.glScalef(scale, scale, scale);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, blend);
        GL11.glColor4f(red, green, blue, opacity);
        tessellator.startDrawingQuads();
        if (brightness > -1) {
            tessellator.setBrightness(brightness);
        }
        tessellator.setColorRGBA_F(red, green, blue, opacity);
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)f1, (double)f4);
        tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)f3, (double)f4);
        tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)f3, (double)f2);
        tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)f1, (double)f2);
        tessellator.draw();
        GL11.glDisable(3042);
    }
    
    public static void renderQuadCenteredFromIcon(final boolean isBlock, final IIcon icon, final float scale, final float red, final float green, final float blue, final int brightness, final int blend, final float opacity) {
        if (isBlock) {
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        }
        else {
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
        }
        final Tessellator tessellator = Tessellator.instance;
        final float f1 = icon.getMaxU();
        final float f2 = icon.getMinV();
        final float f3 = icon.getMinU();
        final float f4 = icon.getMaxV();
        GL11.glEnable(32826);
        GL11.glScalef(scale, scale, scale);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, blend);
        GL11.glColor4f(red, green, blue, opacity);
        tessellator.startDrawingQuads();
        tessellator.setBrightness(brightness);
        tessellator.setColorRGBA_F(red, green, blue, opacity);
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        tessellator.addVertexWithUV(-0.5, 0.5, 0.0, (double)f1, (double)f4);
        tessellator.addVertexWithUV(0.5, 0.5, 0.0, (double)f3, (double)f4);
        tessellator.addVertexWithUV(0.5, -0.5, 0.0, (double)f3, (double)f2);
        tessellator.addVertexWithUV(-0.5, -0.5, 0.0, (double)f1, (double)f2);
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDisable(32826);
    }
    
    public static int getTextureAnimationSize(final String s) {
        if (RenderUtils.textureSizeCache.get(s) != null) {
            return (Integer) /*add*/ RenderUtils.textureSizeCache.get(s);
        }
        try {
            final InputStream inputstream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("thaumcraft", s)).getInputStream();
            if (inputstream == null) {
                throw new Exception("Image not found: " + s);
            }
            final BufferedImage bi = ImageIO.read(inputstream);
            final int size = bi.getWidth() / bi.getHeight();
            RenderUtils.textureSizeCache.put(s, size);
            return size;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 16;
        }
    }
    
    public static int getTextureSize(final String s, final int dv) {
        if (RenderUtils.textureSizeCache.get(Arrays.asList(s, dv)) != null) {
            return (Integer) /*add*/ RenderUtils.textureSizeCache.get(Arrays.asList(s, dv));
        }
        try {
            final InputStream inputstream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("thaumcraft", s)).getInputStream();
            if (inputstream == null) {
                throw new Exception("Image not found: " + s);
            }
            final BufferedImage bi = ImageIO.read(inputstream);
            final int size = bi.getWidth() / dv;
            RenderUtils.textureSizeCache.put(Arrays.asList(s, dv), size);
            return size;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 16;
        }
    }
    
    public static int getBrightnessForRender(final Entity entity, final double x, final double z) {
        final int var2 = MathHelper.floor_double(x);
        final int var3 = MathHelper.floor_double(z);
        if (entity.worldObj.blockExists(var2, 0, var3)) {
            final double var4 = (entity.boundingBox.maxY - entity.boundingBox.minY) * 0.66;
            final int var5 = MathHelper.floor_double(entity.posY - entity.yOffset + var4);
            return entity.worldObj.getLightBrightnessForSkyBlocks(var2, var5, var3, 2);
        }
        return 0;
    }
    
    public static void bindTexture(final String texture) {
        ResourceLocation rl = null;
        if (RenderUtils.boundTextures.containsKey(texture)) {
            rl = RenderUtils.boundTextures.get(texture);
        }
        else {
            rl = new ResourceLocation("thaumcraft", texture);
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(rl);
    }
    
    public static void bindTexture(final String mod, final String texture) {
        ResourceLocation rl = null;
        if (RenderUtils.boundTextures.containsKey(mod + ":" + texture)) {
            rl = RenderUtils.boundTextures.get(mod + ":" + texture);
        }
        else {
            rl = new ResourceLocation(mod, texture);
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(rl);
    }
    
    public static void bindTexture(final ResourceLocation resource) {
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
    }
    
    public static void infusedStoneSparkle(final World world, final int x, final int y, final int z, final int md) {
        if (!world.isRemote) {
            return;
        }
        int color = 0;
        switch (md) {
            case 1: {
                color = 1;
                break;
            }
            case 2: {
                color = 4;
                break;
            }
            case 3: {
                color = 2;
                break;
            }
            case 4: {
                color = 3;
                break;
            }
            case 5: {
                color = 6;
                break;
            }
            case 6: {
                color = 5;
                break;
            }
        }
        for (int a = 0; a < ThaumicUtilities.proxy.particleCount(3); ++a) {
            final FXSparkle fx = new FXSparkle(world, x + world.rand.nextFloat(), y + world.rand.nextFloat(), z + world.rand.nextFloat(), 1.75f, (color == -1) ? world.rand.nextInt(5) : color, 3 + world.rand.nextInt(3));
            fx.setGravity(0.1f);
            ParticleEngine.instance.addEffect(world, fx);
        }
    }
    
    public static void drawTag(final int x, final int y, final Aspect aspect, final float amount, final int bonus, final double z, final int blend, final float alpha) {
        drawTag(x, y, aspect, amount, bonus, z, blend, alpha, false);
    }
    
    public static void drawTag(final int x, final int y, final Aspect aspect, final float amt, final int bonus, final double z) {
        drawTag(x, y, aspect, amt, bonus, z, 771, 1.0f, false);
    }
    
    public static void drawTag(final int x, final int y, final Aspect aspect) {
        drawTag(x, y, aspect, 0.0f, 0, 0.0, 771, 1.0f, true);
    }
    
    public static void drawTag(final int x, final int y, final Aspect aspect, final float amount, final int bonus, final double z, final int blend, final float alpha, final boolean bw) {
        drawTag(x, y, aspect, amount, bonus, z, blend, alpha, bw);
    }
    
    public static Timer getTimer(final Minecraft mc) {
        try {
            return (Timer)ReflectionHelper.getPrivateValue((Class)Minecraft.class, (Object)mc, new String[] { "timer", "Q", "field_71428_T" });
        }
        catch (Exception ex) {
            return new Timer(20.0f);
        }
    }
    
    public static int getGuiXSize(final GuiContainer gui) {
        try {
            return ReflectionHelper.getPrivateValue((Class)GuiContainer.class, (Object)gui, new String[] { "xSize", "f", "field_146999_f" });
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    public static int getGuiYSize(final GuiContainer gui) {
        try {
            return ReflectionHelper.getPrivateValue((Class)GuiContainer.class, (Object)gui, new String[] { "ySize", "g", "field_147000_g" });
        }
        catch (Exception ex) {
            return 0;
        }
    }
    
    public static float getGuiZLevel(final Gui gui) {
        try {
            return ReflectionHelper.getPrivateValue((Class)Gui.class, (Object)gui, new String[] { "zLevel", "e", "field_73735_i" });
        }
        catch (Exception ex) {
            return 0.0f;
        }
    }
    
    public static ResourceLocation getParticleTexture() {
        try {
            return (ResourceLocation)ReflectionHelper.getPrivateValue((Class)EffectRenderer.class, (Object)null, new String[] { "particleTextures", "b", "field_110737_b" });
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    static {
        colorNames = new String[] { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
        colorCodes = new String[] { "§f", 	 "§6", 		"§d", 		"§9", 			"§e", 	"§a", "§d", 	"§8", 		"§7", 	"§b", 		"§5", "§9", 	"§4", 	"§2", 	"§c", 	"§8" };
        colors = new int[] { 15790320, 15435844, 12801229, 6719955, 14602026, 4312372, 14188952, 4408131, 10526880, 2651799, 8073150, 2437522, 5320730, 3887386, 11743532, 1973019 };
        RenderUtils.connectedTextureRefByID = new int[] { 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22, 26, 16, 16, 20, 20, 16, 16, 28, 28, 21, 21, 46, 42, 21, 21, 43, 38, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 16, 16, 20, 20, 16, 16, 28, 28, 25, 25, 45, 37, 25, 25, 40, 32, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22, 26, 7, 7, 24, 24, 7, 7, 10, 10, 29, 29, 44, 41, 29, 29, 39, 33, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 7, 7, 24, 24, 7, 7, 10, 10, 8, 8, 36, 35, 8, 8, 34, 11 };
        RenderUtils.lightBrightnessTable = null;
        RenderUtils.textureSizeCache = new HashMap();
        RenderUtils.boundTextures = new HashMap<String, ResourceLocation>();
        RenderUtils.myFormatter = new DecimalFormat("#######.##");
    }
}
