package thaumicUtilities.core;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public interface IProxy
{
    void registerKeybindings();
    
    int particleCount(final int p0);
    
    void preInit(final FMLPreInitializationEvent p0);
    
    void init(final FMLInitializationEvent p0);
    
    void postInit(final FMLPostInitializationEvent p0);
}
