package net.lucent.fancytitles.built_in.renderers.simple_renderer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lucent.fancytitles.api.titles.rendering.ITitleRenderer;
import net.lucent.fancytitles.api.titles.rendering.ITitleRendererData;
import net.lucent.fancytitles.built_in.textures.data.TextureData;
import net.minecraft.client.gui.GuiGraphics;

public class SimpleRenderer implements ITitleRenderer {

    private static final MapCodec<ITitleRendererData> CODEC =
        RecordCodecBuilder.<SimpleRendererData>mapCodec(instance ->
            instance.group(

                    Codec.STRING.fieldOf("prefix").forGetter(SimpleRendererData::prefix),
                    TextureData.CODEC.optionalFieldOf("texture_data").forGetter(SimpleRendererData::textureData)
            ).apply(instance, SimpleRendererData::new)
        ).xmap(data->data,rendererData -> (SimpleRendererData) rendererData);

    @Override
    public void render(GuiGraphics guiGraphics,ITitleRendererData rendererData) {

    }

    @Override
    public MapCodec<ITitleRendererData> codec() {
        return  CODEC;
    }
}
