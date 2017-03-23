package com.saegusa.thu.render;

import com.saegusa.thu.ThaumicUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import java.awt.Color;
import net.minecraft.init.Blocks;
import com.saegusa.thu.blocks.BlockNetherOre;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.block.Block;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockOreRenderer extends BlockRenderer implements ISimpleBlockRenderingHandler
{
    public static final int[] colors;
    
    public void renderInventoryBlock(final Block block, final int metadata, final int modelID, final RenderBlocks renderer) {
        block.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        renderer.setRenderBoundsFromBlock(block);
        if (metadata == 6) {
            BlockRenderer.drawFaces(renderer, block, ((BlockNetherOre)block).icon[2], false);
        }
        else if (metadata < 6) {
            BlockRenderer.drawFaces(renderer, block, Blocks.netherrack.getIcon(0, 0), false);
            final Color c = new Color(BlockOreRenderer.colors[metadata]);
            final float r = c.getRed() / 255.0f;
            final float g = c.getGreen() / 255.0f;
            final float b = c.getBlue() / 255.0f;
            GL11.glColor3f(r, g, b);
            renderer.clearOverrideBlockTexture();
            block.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            renderer.setRenderBoundsFromBlock(block);
            BlockRenderer.drawFaces(renderer, block, ((BlockNetherOre)block).icon[1], false);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
        }
    }
    
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
        final int bb = BlockRenderer.setBrightness(world, x, y, z, block);
        final int metadata = world.getBlockMetadata(x, y, z);
        block.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);
        if (metadata != 6 && metadata < 6) {
            final Tessellator t = Tessellator.instance;
            t.setColorOpaque_I(BlockOreRenderer.colors[metadata]);
            t.setBrightness(Math.max(bb, 160));
            BlockRenderer.renderAllSides(world, x, y, z, block, renderer, ((BlockNetherOre)block).icon[1], false);
            if (Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1) {
                block.setBlockBounds(0.005f, 0.005f, 0.005f, 0.995f, 0.995f, 0.995f);
                renderer.setRenderBoundsFromBlock(block);
                t.setBrightness(bb);
                BlockRenderer.renderAllSides(world, x, y, z, block, renderer, ((BlockNetherOre)block).icon[0], false);
            }
        }
        renderer.clearOverrideBlockTexture();
        block.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        renderer.setRenderBoundsFromBlock(block);
        return true;
    }
    
    public boolean shouldRender3DInInventory(final int modelId) {
        return true;
    }
    
    public int getRenderId() {
        return ThaumicUtilities.netherOre_renderID;
    }
    
    static {
        colors = new int[] { 16777086, 16727041, 37119, 40960, 15650047, 5592439 };
    }
}
