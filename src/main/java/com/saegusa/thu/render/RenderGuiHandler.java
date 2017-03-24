package com.saegusa.thu.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import com.saegusa.thu.gui.GuiVisStorage;
import com.saegusa.thu.settings.ConfigHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class RenderGuiHandler
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderGui(final RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        if (ConfigHandler.displayVis) {
            new GuiVisStorage(Minecraft.getMinecraft());
        }
    }
}
