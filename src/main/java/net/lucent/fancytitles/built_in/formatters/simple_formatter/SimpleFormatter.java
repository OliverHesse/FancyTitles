package net.lucent.fancytitles.built_in.formatters.simple_formatter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lucent.fancytitles.api.titles.formatting.ITitleChatFormatter;
import net.lucent.fancytitles.api.titles.formatting.ITitleChatFormatterData;
import net.lucent.fancytitles.api.titles.rendering.ITitleRendererData;
import net.lucent.fancytitles.built_in.renderers.simple_renderer.SimpleRendererData;
import net.minecraft.network.chat.Component;

public class SimpleFormatter implements ITitleChatFormatter {

    private static final MapCodec<ITitleChatFormatterData> CODEC =
            RecordCodecBuilder.<SimpleFormatterData>mapCodec(instance ->
                    instance.group(
                            Codec.STRING.fieldOf("prefix").forGetter(SimpleFormatterData::prefix)
                    ).apply(instance, SimpleFormatterData::new)
            ).xmap(data->data,data->(SimpleFormatterData) data);

    @Override
    public Component getModifiedChatMessage(Component currentMessage, ITitleChatFormatterData formatterData) {
        return currentMessage;
    }

    @Override
    public MapCodec<ITitleChatFormatterData> codec() {
        return CODEC;
    }
}
