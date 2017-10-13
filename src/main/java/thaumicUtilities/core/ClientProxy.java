package thaumicUtilities.core;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import thaumicUtilities.tweaks.VisAmuletTooltipTweaker;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.client.FMLClientHandler;

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
