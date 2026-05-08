package net.lucent.fancytitles.api.titles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.registries.CommonRegistries;
import net.lucent.fancytitles.api.titles.formatting.ITitleChatFormatter;
import net.lucent.fancytitles.api.titles.formatting.ITitleChatFormatterData;
import net.lucent.fancytitles.api.titles.rendering.ITitleRenderer;
import net.lucent.fancytitles.api.titles.rendering.ITitleRendererData;
import net.lucent.fancytitles.api.titles.unlock_handling.ITitleUnlockData;
import net.lucent.fancytitles.api.titles.unlock_handling.ITitleUnlockCondition;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

public class Title {

    public record DataInstance<T,V>(T factory,V data){}

    private final String name;
    private final String description;


    private final DataInstance<ITitleRenderer, ITitleRendererData> renderer;
    private final DataInstance<ITitleChatFormatter,ITitleChatFormatterData> chatFormatter;
    private final DataInstance<ITitleUnlockCondition, ITitleUnlockData> unlockCondition;


    public Title(String name, String description,
                 DataInstance<ITitleRenderer, ITitleRendererData> renderer,
                 DataInstance<ITitleChatFormatter,ITitleChatFormatterData> chatFormatter,
                 DataInstance<ITitleUnlockCondition, ITitleUnlockData> unlockCondition) {
        this.name = name;
        this.description = description;
        this.renderer = renderer;
        this.chatFormatter = chatFormatter;
        this.unlockCondition = unlockCondition;

        FancyTitles.LOGGER.info("CREATED TITLE {}",name);
    }

    public String getName(){return name;}
    public String getDescription(){return description;}
    public ITitleRenderer getRenderer(){return renderer.factory;}
    public ITitleRendererData getRendererData(){return renderer.data;}
    public ITitleChatFormatter getFormatter(){return chatFormatter.factory;}
    public ITitleChatFormatterData getFormatterData(){return chatFormatter.data;}
    public ITitleUnlockCondition getCondition(){return unlockCondition.factory;}
    public ITitleUnlockData getConditionData(){return unlockCondition.data;}

    //TODO might remove these methods in favour of events

    /**
     * ran both on first acquired and when loaded on login
     * @param player the player the title is added to
     */
    public void onAdded(Player player){

    }
    /**
     * ran when removed from the player
     * it is also ran if the player lost access (e.g whilst logged of lost an advancement so when loading titles it is removed)
     *
     * @param player the player the title is removed from
     */
    public void onRemoved(Player player){

    }

    public static Codec<Title> codec(){
        Codec<DataInstance<ITitleRenderer,ITitleRendererData>> RENDERER_CODEC = CommonRegistries.TITLE_RENDERER_REGISTRY
                .byNameCodec().dispatch(
                        "type",
                        DataInstance::factory,
                        renderer -> renderer.codec().xmap(
                                data -> new DataInstance<>(renderer, data),
                                instance -> instance.data
                        )
                );
        Codec<DataInstance<ITitleChatFormatter,ITitleChatFormatterData>> FORMATTER_CODEC = CommonRegistries.TITLE_CHAT_FORMATTER_REGISTRY.byNameCodec().dispatch(
                "type",
                DataInstance::factory,
                renderer -> renderer.codec().xmap(
                        data -> new DataInstance<>(renderer, data),
                        instance -> instance.data
                ));
        Codec<DataInstance<ITitleUnlockCondition,ITitleUnlockData>> UNLOCK_CONDITION_CODEC = CommonRegistries.TITLE_UNLOCK_CONDITION_REGISTRY.byNameCodec().dispatch(
                "type",
                DataInstance::factory,
                renderer -> renderer.codec().xmap(
                        data -> new DataInstance<>(renderer, data),
                        instance -> instance.data
                ));
        return RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.fieldOf("name").forGetter(t -> t.name),
                        Codec.STRING.fieldOf("description").forGetter(t -> t.description),
                        RENDERER_CODEC.fieldOf("renderer").forGetter(t -> t.renderer),
                        FORMATTER_CODEC.fieldOf("formatter").forGetter(t->t.chatFormatter),
                        UNLOCK_CONDITION_CODEC.fieldOf("unlock_condition").forGetter(t->t.unlockCondition)
                ).apply(instance, Title::new)
        );
    }
}
