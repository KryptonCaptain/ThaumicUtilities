package com.saegusa.thu.render;

import net.minecraftforge.client.event.*;
import com.saegusa.thu.settings.*;
import com.saegusa.thu.gui.*;
import net.minecraft.client.*;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.relauncher.*;

public class RenderGuiHandler
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderGui(final RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        if (ConfigurationHandler.Settings.displayVis) {
            new GuiVisStorage(Minecraft.getMinecraft());
        }
    }
}
