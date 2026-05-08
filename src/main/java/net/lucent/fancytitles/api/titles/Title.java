package net.lucent.fancytitles.api.titles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lucent.fancytitles.api.registries.CommonRegistries;
import net.lucent.fancytitles.api.titles.formatting.ITitleChatFormatter;
import net.lucent.fancytitles.api.titles.formatting.ITitleChatFormatterData;
import net.lucent.fancytitles.api.titles.rendering.ITitleRenderer;
import net.lucent.fancytitles.api.titles.rendering.ITitleRendererData;

public class Title {

    public record DataInstance<T,V>(T factory,V data){}

    private final String name;
    private final String description;


    private final DataInstance<ITitleRenderer,ITitleRendererData> titleRendererData;
    private final DataInstance<ITitleChatFormatter,ITitleChatFormatterData> titleChatFormatterData;


    public Title(String name, String description, DataInstance<ITitleRenderer,ITitleRendererData> titleRendererData, DataInstance<ITitleChatFormatter, ITitleChatFormatterData> titleChatFormatterData) {
        this.name = name;
        this.description = description;
        this.titleRendererData = titleRendererData;
        this.titleChatFormatterData = titleChatFormatterData;
    }

    private static Codec<DataInstance<ITitleRenderer,ITitleRendererData>> RENDERER_CODEC = CommonRegistries.TITLE_RENDERER_REGISTRY.byNameCodec().dispatch(
            "type",
            DataInstance::factory,
            renderer -> renderer.codec().xmap(
                    data -> new DataInstance<>(renderer, data),
                    instance -> instance.data
            )
    );
    private static Codec<DataInstance<ITitleChatFormatter,ITitleChatFormatterData>> FORMATTER_CODEC = CommonRegistries.TITLE_CHAT_FORMATTER_REGISTRY.byNameCodec().dispatch(
            "type",
            DataInstance::factory,
            renderer -> renderer.codec().xmap(
                    data -> new DataInstance<>(renderer, data),
                    instance -> instance.data
            )
    );




    // 2. The Title Codec
    public static final Codec<Title> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("name").forGetter(t -> t.name),
                    Codec.STRING.fieldOf("description").forGetter(t -> t.description),
                    RENDERER_CODEC.fieldOf("renderer").forGetter(t -> t.titleRendererData),
                    FORMATTER_CODEC.fieldOf("formatter").forGetter(t->t.titleChatFormatterData)
            ).apply(instance, Title::new)
    );

}
