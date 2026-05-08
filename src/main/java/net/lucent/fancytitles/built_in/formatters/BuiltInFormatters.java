package net.lucent.fancytitles.built_in.formatters;

import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.registries.CommonRegistries;
import net.lucent.fancytitles.api.titles.formatting.ITitleChatFormatter;
import net.lucent.fancytitles.api.titles.rendering.ITitleRenderer;
import net.lucent.fancytitles.built_in.formatters.simple_formatter.SimpleFormatter;
import net.lucent.fancytitles.built_in.renderers.simple_renderer.SimpleRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BuiltInFormatters {
    public static final DeferredRegister<ITitleChatFormatter> FORMATTER = DeferredRegister.create(CommonRegistries.TITLE_CHAT_FORMATTER_REGISTRY, FancyTitles.MOD_ID);




    public static final DeferredHolder<ITitleChatFormatter, SimpleFormatter> SIMPLE_RENDERER = FORMATTER.register("simple_formatter",
            SimpleFormatter::new);











    public static void register(IEventBus eventBus){
       FORMATTER.register(eventBus);
    }
}
