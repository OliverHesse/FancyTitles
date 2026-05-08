package net.lucent.fancytitles.api.titles.rendering;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.GuiGraphics;

public interface ITitleRenderer {
    //pose should already be adjusted
    void render(GuiGraphics guiGraphics);


    MapCodec<ITitleRendererData> codec();
}
