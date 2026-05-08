package net.lucent.fancytitles.built_in.renderers;

import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.registries.CommonRegistries;
import net.lucent.fancytitles.api.registries.RegistryHelper;
import net.lucent.fancytitles.api.titles.rendering.ITitleRenderer;
import net.lucent.fancytitles.built_in.renderers.simple_renderer.SimpleRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BuiltInRenderers {

    public static final DeferredRegister<ITitleRenderer> RENDERERS = DeferredRegister.create(CommonRegistries.TITLE_RENDERER_REGISTRY, FancyTitles.MOD_ID);




    public static final DeferredHolder<ITitleRenderer, SimpleRenderer> SIMPLE_RENDERER = RENDERERS.register("simple_renderer",
            SimpleRenderer::new);





    public static void register(IEventBus eventBus){
        RENDERERS.register(eventBus);
    }
}
