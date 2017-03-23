package com.saegusa.thu.client.fx;

import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import cpw.mods.fml.client.*;
import net.minecraft.util.*;

public class FXSparkle extends EntityFX
{
    public boolean leyLineEffect;
    public int multiplier;
    public boolean shrink;
    public int particle;
    public boolean tinkle;
    public int blendmode;
    public boolean slowdown;
    public int currentColor;
    
    public FXSparkle(final World world, final double d, final double d1, final double d2, final float f, float f1, final float f2, final float f3, final int m) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.leyLineEffect = false;
        this.multiplier = 2;
        this.shrink = true;
        this.particle = 16;
        this.tinkle = false;
        this.blendmode = 1;
        this.slowdown = true;
        this.currentColor = 0;
        if (f1 == 0.0f) {
            f1 = 1.0f;
        }
        this.particleRed = f1;
        this.particleGreen = f2;
        this.particleBlue = f3;
        this.particleGravity = 0.0f;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.particleScale *= f;
        this.particleMaxAge = 3 * m;
        this.multiplier = m;
        this.noClip = false;
        this.setSize(0.01f, 0.01f);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
    }
    
    public FXSparkle(final World world, final double d, final double d1, final double d2, final float f, final int type, final int m) {
        this(world, d, d1, d2, f, 0.0f, 0.0f, 0.0f, m);
        switch (this.currentColor = type) {
            case 0: {
                this.particleRed = 0.75f + world.rand.nextFloat() * 0.25f;
                this.particleGreen = 0.25f + world.rand.nextFloat() * 0.25f;
                this.particleBlue = 0.75f + world.rand.nextFloat() * 0.25f;
                break;
            }
            case 1: {
                this.particleRed = 0.5f + world.rand.nextFloat() * 0.3f;
                this.particleGreen = 0.5f + world.rand.nextFloat() * 0.3f;
                this.particleBlue = 0.2f;
                break;
            }
            case 2: {
                this.particleRed = 0.2f;
                this.particleGreen = 0.2f;
                this.particleBlue = 0.7f + world.rand.nextFloat() * 0.3f;
                break;
            }
            case 3: {
                this.particleRed = 0.2f;
                this.particleGreen = 0.7f + world.rand.nextFloat() * 0.3f;
                this.particleBlue = 0.2f;
                break;
            }
            case 4: {
                this.particleRed = 0.7f + world.rand.nextFloat() * 0.3f;
                this.particleGreen = 0.2f;
                this.particleBlue = 0.2f;
                break;
            }
            case 5: {
                this.blendmode = 771;
                this.particleRed = world.rand.nextFloat() * 0.1f;
                this.particleGreen = world.rand.nextFloat() * 0.1f;
                this.particleBlue = world.rand.nextFloat() * 0.1f;
                break;
            }
            case 6: {
                this.particleRed = 0.8f + world.rand.nextFloat() * 0.2f;
                this.particleGreen = 0.8f + world.rand.nextFloat() * 0.2f;
                this.particleBlue = 0.8f + world.rand.nextFloat() * 0.2f;
                break;
            }
            case 7: {
                this.particleRed = 0.2f;
                this.particleGreen = 0.5f + world.rand.nextFloat() * 0.3f;
                this.particleBlue = 0.6f + world.rand.nextFloat() * 0.3f;
                break;
            }
        }
    }
    
    public FXSparkle(final World world, final double d, final double d1, final double d2, final double x, final double y, final double z, final float f, final int type, final int m) {
        this(world, d, d1, d2, f, type, m);
        final double dx = x - this.posX;
        final double dy = y - this.posY;
        final double dz = z - this.posZ;
        this.motionX = dx / this.particleMaxAge;
        this.motionY = dy / this.particleMaxAge;
        this.motionZ = dz / this.particleMaxAge;
    }
    
    public void renderParticle(final Tessellator tessellator, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
        final int part = this.particle + this.particleAge / this.multiplier;
        final float var8 = part % 4 / 16.0f;
        final float var9 = var8 + 0.0624375f;
        final float var10 = 0.25f;
        final float var11 = var10 + 0.0624375f;
        float var12 = 0.1f * this.particleScale;
        if (this.shrink) {
            var12 *= (this.particleMaxAge - this.particleAge + 1) / this.particleMaxAge;
        }
        final float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - FXSparkle.interpPosX);
        final float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - FXSparkle.interpPosY);
        final float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - FXSparkle.interpPosZ);
        final float var16 = 1.0f;
        tessellator.setBrightness(240);
        tessellator.setColorRGBA_F(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0f);
        tessellator.addVertexWithUV((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12), (double)var9, (double)var11);
        tessellator.addVertexWithUV((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12), (double)var9, (double)var10);
        tessellator.addVertexWithUV((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12), (double)var8, (double)var10);
        tessellator.addVertexWithUV((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12), (double)var8, (double)var11);
    }
    
    public int getFXLayer() {
        return (this.blendmode != 1) ? 1 : 0;
    }
    
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge == 0 && this.tinkle && this.worldObj.rand.nextInt(10) == 0) {
            this.worldObj.playSoundAtEntity((Entity)this, "random.orb", 0.02f, 0.7f * ((this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.6f + 2.0f));
        }
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.motionY -= 0.04 * this.particleGravity;
        if (!this.noClip) {
            this.pushOutOfBlocks(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        if (this.slowdown) {
            this.motionX *= 0.9080000019073486;
            this.motionY *= 0.9080000019073486;
            this.motionZ *= 0.9080000019073486;
            if (this.onGround) {
                this.motionX *= 0.699999988079071;
                this.motionZ *= 0.699999988079071;
            }
        }
        if (this.leyLineEffect) {
            final FXSparkle fx = new FXSparkle(this.worldObj, this.prevPosX + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1f, this.prevPosY + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1f, this.prevPosZ + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.1f, 1.0f, this.currentColor, 3 + this.worldObj.rand.nextInt(3));
            fx.noClip = true;
            FMLClientHandler.instance().getClient().effectRenderer.addEffect((EntityFX)fx);
        }
    }
    
    public void setGravity(final float value) {
        this.particleGravity = value;
    }
    
    protected boolean pushOutOfBlocks(final double par1, final double par3, final double par5) {
        final int var7 = MathHelper.floor_double(par1);
        final int var8 = MathHelper.floor_double(par3);
        final int var9 = MathHelper.floor_double(par5);
        final double var10 = par1 - var7;
        final double var11 = par3 - var8;
        final double var12 = par5 - var9;
        if (!this.worldObj.isAirBlock(var7, var8, var9)) {
            final boolean var13 = !this.worldObj.isBlockNormalCubeDefault(var7 - 1, var8, var9, true);
            final boolean var14 = !this.worldObj.isBlockNormalCubeDefault(var7 + 1, var8, var9, true);
            final boolean var15 = !this.worldObj.isBlockNormalCubeDefault(var7, var8 - 1, var9, true);
            final boolean var16 = !this.worldObj.isBlockNormalCubeDefault(var7, var8 + 1, var9, true);
            final boolean var17 = !this.worldObj.isBlockNormalCubeDefault(var7, var8, var9 - 1, true);
            final boolean var18 = !this.worldObj.isBlockNormalCubeDefault(var7, var8, var9 + 1, true);
            byte var19 = -1;
            double var20 = 9999.0;
            if (var13 && var10 < var20) {
                var20 = var10;
                var19 = 0;
            }
            if (var14 && 1.0 - var10 < var20) {
                var20 = 1.0 - var10;
                var19 = 1;
            }
            if (var15 && var11 < var20) {
                var20 = var11;
                var19 = 2;
            }
            if (var16 && 1.0 - var11 < var20) {
                var20 = 1.0 - var11;
                var19 = 3;
            }
            if (var17 && var12 < var20) {
                var20 = var12;
                var19 = 4;
            }
            if (var18 && 1.0 - var12 < var20) {
                var20 = 1.0 - var12;
                var19 = 5;
            }
            final float var21 = this.rand.nextFloat() * 0.05f + 0.025f;
            final float var22 = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            if (var19 == 0) {
                this.motionX = -var21;
                final double n = var22;
                this.motionZ = n;
                this.motionY = n;
            }
            if (var19 == 1) {
                this.motionX = var21;
                final double n2 = var22;
                this.motionZ = n2;
                this.motionY = n2;
            }
            if (var19 == 2) {
                this.motionY = -var21;
                final double n3 = var22;
                this.motionZ = n3;
                this.motionX = n3;
            }
            if (var19 == 3) {
                this.motionY = var21;
                final double n4 = var22;
                this.motionZ = n4;
                this.motionX = n4;
            }
            if (var19 == 4) {
                this.motionZ = -var21;
                final double n5 = var22;
                this.motionX = n5;
                this.motionY = n5;
            }
            if (var19 == 5) {
                this.motionZ = var21;
                final double n6 = var22;
                this.motionX = n6;
                this.motionY = n6;
            }
            return true;
        }
        return false;
    }
}
