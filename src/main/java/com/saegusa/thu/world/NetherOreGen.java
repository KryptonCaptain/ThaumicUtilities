package com.saegusa.thu.world;

import net.minecraft.block.Block;
import com.saegusa.thu.utils.ThaumcraftUtils;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;
import com.saegusa.thu.ThaumicUtilities;
import com.saegusa.thu.settings.ConfigurationHandler;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import java.util.Random;
import cpw.mods.fml.common.IWorldGenerator;

public class NetherOreGen implements IWorldGenerator
{
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world, final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            case -1: {
                if (ConfigurationHandler.Settings.generateOresInNether) {
                    this.generateNether(world, random, chunkX, chunkZ);
                    break;
                }
                break;
            }
        }
    }
    
    private void generateNether(final World world, final Random random, final int x, final int z) {
        if (ConfigurationHandler.Settings.generateInfusedOresInNether) {
            for (int k = 0; k < ConfigurationHandler.Settings.netherOreRarity; ++k) {
                final int Xcoord = x * 16 + random.nextInt(16);
                final int Ycoord = 10 + random.nextInt(128);
                final int Zcoord = z * 16 + random.nextInt(16);
                new WorldGenMinable(ThaumicUtilities.blockNetherOre, 0, 6, Blocks.netherrack).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
            for (int k = 0; k < ConfigurationHandler.Settings.netherOreRarity; ++k) {
                final int Xcoord = x * 16 + random.nextInt(16);
                final int Ycoord = 10 + random.nextInt(128);
                final int Zcoord = z * 16 + random.nextInt(16);
                new WorldGenMinable(ThaumicUtilities.blockNetherOre, 1, 6, Blocks.netherrack).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
            for (int k = 0; k < ConfigurationHandler.Settings.netherOreRarity; ++k) {
                final int Xcoord = x * 16 + random.nextInt(16);
                final int Ycoord = 10 + random.nextInt(128);
                final int Zcoord = z * 16 + random.nextInt(16);
                new WorldGenMinable(ThaumicUtilities.blockNetherOre, 2, 6, Blocks.netherrack).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
            for (int k = 0; k < ConfigurationHandler.Settings.netherOreRarity; ++k) {
                final int Xcoord = x * 16 + random.nextInt(16);
                final int Ycoord = 10 + random.nextInt(128);
                final int Zcoord = z * 16 + random.nextInt(16);
                new WorldGenMinable(ThaumicUtilities.blockNetherOre, 3, 6, Blocks.netherrack).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
            for (int k = 0; k < ConfigurationHandler.Settings.netherOreRarity; ++k) {
                final int Xcoord = x * 16 + random.nextInt(16);
                final int Ycoord = 10 + random.nextInt(128);
                final int Zcoord = z * 16 + random.nextInt(16);
                new WorldGenMinable(ThaumicUtilities.blockNetherOre, 4, 6, Blocks.netherrack).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
            for (int k = 0; k < ConfigurationHandler.Settings.netherOreRarity; ++k) {
                final int Xcoord = x * 16 + random.nextInt(16);
                final int Ycoord = 10 + random.nextInt(128);
                final int Zcoord = z * 16 + random.nextInt(16);
                new WorldGenMinable(ThaumicUtilities.blockNetherOre, 5, 6, Blocks.netherrack).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
        }
        if (ConfigurationHandler.Settings.generateOtherOresInNether) {
            for (int k = 0; k < ConfigurationHandler.Settings.netherOreRarity; ++k) {
                final int Xcoord = x * 16 + random.nextInt(16);
                final int Ycoord = 10 + random.nextInt(128);
                final int Zcoord = z * 16 + random.nextInt(16);
                new WorldGenMinable(ThaumicUtilities.blockNetherOre, 6, 2, Blocks.netherrack).generate(world, random, Xcoord, Ycoord, Zcoord);
            }
            if (ThaumcraftUtils.getBlock("blockCustomOre") != null && ThaumcraftUtils.getBlock("blockCustomOre") instanceof Block) {
                for (int k = 0; k < ConfigurationHandler.Settings.netherOreRarity; ++k) {
                    final int Xcoord = x * 16 + random.nextInt(16);
                    final int Ycoord = 10 + random.nextInt(128);
                    final int Zcoord = z * 16 + random.nextInt(16);
                    new WorldGenMinable(ThaumcraftUtils.getBlock("blockCustomOre"), 0, 1, Blocks.netherrack).generate(world, random, Xcoord, Ycoord, Zcoord);
                }
            }
        }
    }
}
