package net.lucent.fancytitles.api.titles.rendering;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface ITitleRenderer {
    //pose should already be adjusted
    @OnlyIn(Dist.CLIENT)
    void render(GuiGraphics guiGraphics,ITitleRendererData rendererData);


    MapCodec<ITitleRendererData> codec();
}
