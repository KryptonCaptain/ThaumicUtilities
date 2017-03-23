package com.saegusa.thu.core;

import cpw.mods.fml.common.event.*;

public interface IProxy
{
    void registerKeybindings();
    
    int particleCount(final int p0);
    
    void preInit(final FMLPreInitializationEvent p0);
    
    void init(final FMLInitializationEvent p0);
    
    void postInit(final FMLPostInitializationEvent p0);
}
