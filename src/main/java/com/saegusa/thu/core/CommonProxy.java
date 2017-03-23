package com.saegusa.thu.core;

import cpw.mods.fml.common.event.*;

public abstract class CommonProxy implements IProxy
{
    @Override
    public int particleCount(final int base) {
        return 0;
    }
    
    @Override
    public void preInit(final FMLPreInitializationEvent event) {
    }
    
    @Override
    public void init(final FMLInitializationEvent event) {
    }
    
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
    }
}
