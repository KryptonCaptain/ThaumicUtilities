package thaumicUtilities.render;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public class BlockRenderer
{
    public static float W1;
    public static float W2;
    public static float W3;
    public static float W4;
    public static float W5;
    public static float W6;
    public static float W7;
    public static float W8;
    public static float W9;
    public static float W10;
    public static float W11;
    public static float W12;
    public static float W13;
    public static float W14;
    public static float W15;
    
    public static void drawFaces(final RenderBlocks renderblocks, final Block block, final IIcon icon, final boolean st) {
        drawFaces(renderblocks, block, icon, icon, icon, icon, icon, icon, st);
    }
    
    public static void drawFaces(final RenderBlocks renderblocks, final Block block, final IIcon i1, final IIcon i2, final IIcon i3, final IIcon i4, final IIcon i5, final IIcon i6, final boolean solidtop) {
        final Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, -1.0f, 0.0f);
        renderblocks.renderFaceYNeg(block, 0.0, 0.0, 0.0, i1);
        tessellator.draw();
        if (solidtop) {
            GL11.glDisable(3008);
        }
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 1.0f, 0.0f);
        renderblocks.renderFaceYPos(block, 0.0, 0.0, 0.0, i2);
        tessellator.draw();
        if (solidtop) {
            GL11.glEnable(3008);
        }
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, 1.0f);
        renderblocks.renderFaceXNeg(block, 0.0, 0.0, 0.0, i3);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0f, 0.0f, -1.0f);
        renderblocks.renderFaceXPos(block, 0.0, 0.0, 0.0, i4);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0f, 0.0f, 0.0f);
        renderblocks.renderFaceZNeg(block, 0.0, 0.0, 0.0, i5);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0f, 0.0f, 0.0f);
        renderblocks.renderFaceZPos(block, 0.0, 0.0, 0.0, i6);
        tessellator.draw();
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
    }
    
    public static int setBrightness(final IBlockAccess blockAccess, final int i, final int j, final int k, final Block block) {
        final Tessellator tessellator = Tessellator.instance;
        final int mb = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
        tessellator.setBrightness(mb);
        final float f = 1.0f;
        final int l = block.colorMultiplier(blockAccess, i, j, k);
        float f2 = (l >> 16 & 0xFF) / 255.0f;
        float f3 = (l >> 8 & 0xFF) / 255.0f;
        float f4 = (l & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            final float f5 = (f2 * 30.0f + f3 * 59.0f + f4 * 11.0f) / 100.0f;
            final float f6 = (f2 * 30.0f + f3 * 70.0f) / 100.0f;
            final float f7 = (f2 * 30.0f + f4 * 70.0f) / 100.0f;
            f2 = f5;
            f3 = f6;
            f4 = f7;
        }
        tessellator.setColorOpaque_F(f * f2, f * f3, f * f4);
        return mb;
    }
    
    protected static void renderAllSides(final IBlockAccess world, final int x, final int y, final int z, final Block block, final RenderBlocks renderer, final IIcon tex) {
        renderAllSides(world, x, y, z, block, renderer, tex, true);
    }
    
    protected static void renderAllSides(final IBlockAccess world, final int x, final int y, final int z, final Block block, final RenderBlocks renderer, final IIcon tex, final boolean allsides) {
        if (allsides || block.shouldSideBeRendered(world, x + 1, y, z, 6)) {
            renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, tex);
        }
        if (allsides || block.shouldSideBeRendered(world, x - 1, y, z, 6)) {
            renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, tex);
        }
        if (allsides || block.shouldSideBeRendered(world, x, y, z + 1, 6)) {
            renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, tex);
        }
        if (allsides || block.shouldSideBeRendered(world, x, y, z - 1, 6)) {
            renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, tex);
        }
        if (allsides || block.shouldSideBeRendered(world, x, y + 1, z, 6)) {
            renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, tex);
        }
        if (allsides || block.shouldSideBeRendered(world, x, y - 1, z, 6)) {
            renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, tex);
        }
    }
    
    protected static void renderAllSides(final IBlockAccess world, final int x, final int y, final int z, final Block block, final RenderBlocks renderer, final boolean allsides) {
        if (allsides || block.shouldSideBeRendered(world, x + 1, y, z, 6)) {
            renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, block.getIcon(world, x, y, z, 5));
        }
        if (allsides || block.shouldSideBeRendered(world, x - 1, y, z, 6)) {
            renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, block.getIcon(world, x, y, z, 4));
        }
        if (allsides || block.shouldSideBeRendered(world, x, y, z + 1, 6)) {
            renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, block.getIcon(world, x, y, z, 3));
        }
        if (allsides || block.shouldSideBeRendered(world, x, y, z - 1, 6)) {
            renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, block.getIcon(world, x, y, z, 2));
        }
        if (allsides || block.shouldSideBeRendered(world, x, y + 1, z, 6)) {
            renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, block.getIcon(world, x, y, z, 1));
        }
        if (allsides || block.shouldSideBeRendered(world, x, y - 1, z, 6)) {
            renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, block.getIcon(world, x, y, z, 0));
        }
    }
    
    protected static void renderAllSidesInverted(final IBlockAccess world, final int x, final int y, final int z, final Block block, final RenderBlocks renderer, final IIcon tex, final boolean allsides) {
        if (allsides || !block.shouldSideBeRendered(world, x - 1, y, z, 6)) {
            renderer.renderFaceXPos(block, (double)(x - 1), (double)y, (double)z, tex);
        }
        if (allsides || !block.shouldSideBeRendered(world, x + 1, y, z, 6)) {
            renderer.renderFaceXNeg(block, (double)(x + 1), (double)y, (double)z, tex);
        }
        if (allsides || !block.shouldSideBeRendered(world, x, y, z - 1, 6)) {
            renderer.renderFaceZPos(block, (double)x, (double)y, (double)(z - 1), tex);
        }
        if (allsides || !block.shouldSideBeRendered(world, x, y, z + 1, 6)) {
            renderer.renderFaceZNeg(block, (double)x, (double)y, (double)(z + 1), tex);
        }
        if (allsides || !block.shouldSideBeRendered(world, x, y - 1, z, 6)) {
            renderer.renderFaceYPos(block, (double)x, (double)(y - 1), (double)z, tex);
        }
        if (allsides || !block.shouldSideBeRendered(world, x, y + 1, z, 6)) {
            renderer.renderFaceYNeg(block, (double)x, (double)(y + 1), (double)z, tex);
        }
    }
    
    protected static void renderAllSides(final int x, final int y, final int z, final Block block, final RenderBlocks renderer, final IIcon tex) {
        renderer.renderFaceXPos(block, (double)(x - 1), (double)y, (double)z, tex);
        renderer.renderFaceXNeg(block, (double)(x + 1), (double)y, (double)z, tex);
        renderer.renderFaceZPos(block, (double)x, (double)y, (double)(z - 1), tex);
        renderer.renderFaceZNeg(block, (double)x, (double)y, (double)(z + 1), tex);
        renderer.renderFaceYPos(block, (double)x, (double)(y - 1), (double)z, tex);
        renderer.renderFaceYNeg(block, (double)x, (double)(y + 1), (double)z, tex);
    }
    
    static {
        BlockRenderer.W1 = 0.0625f;
        BlockRenderer.W2 = 0.125f;
        BlockRenderer.W3 = 0.1875f;
        BlockRenderer.W4 = 0.25f;
        BlockRenderer.W5 = 0.3125f;
        BlockRenderer.W6 = 0.375f;
        BlockRenderer.W7 = 0.4375f;
        BlockRenderer.W8 = 0.5f;
        BlockRenderer.W9 = 0.5625f;
        BlockRenderer.W10 = 0.625f;
        BlockRenderer.W11 = 0.6875f;
        BlockRenderer.W12 = 0.75f;
        BlockRenderer.W13 = 0.8125f;
        BlockRenderer.W14 = 0.875f;
        BlockRenderer.W15 = 0.9375f;
    }
}
