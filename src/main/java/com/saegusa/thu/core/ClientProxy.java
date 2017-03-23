package com.saegusa.thu.core;

import cpw.mods.fml.client.*;
import net.minecraftforge.common.*;
import com.saegusa.thu.tweaks.*;
import cpw.mods.fml.common.event.*;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerKeybindings() {
    }
    
    @Override
    public int particleCount(final int base) {
        if (FMLClientHandler.instance().getClient().gameSettings.particleSetting == 2) {
            return 0;
        }
        return (FMLClientHandler.instance().getClient().gameSettings.particleSetting == 1) ? (base * 1) : (base * 2);
    }
    
    @Override
    public void preInit(final FMLPreInitializationEvent event) {
    }
    
    @Override
    public void init(final FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)new VisAmuletTooltipTweaker());
    }
    
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
    }
}
