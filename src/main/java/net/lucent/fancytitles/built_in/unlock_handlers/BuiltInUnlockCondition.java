package net.lucent.fancytitles.built_in.unlock_handlers;

import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.registries.CommonRegistries;
import net.lucent.fancytitles.api.titles.rendering.ITitleRenderer;
import net.lucent.fancytitles.api.titles.unlock_handling.ITitleUnlockCondition;
import net.lucent.fancytitles.built_in.renderers.simple_renderer.SimpleRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BuiltInUnlockCondition {
    public static final DeferredRegister<ITitleUnlockCondition> UNLOCK_CONDITION = DeferredRegister.create(CommonRegistries.TITLE_UNLOCK_CONDITION_REGISTRY, FancyTitles.MOD_ID);



    public static final DeferredHolder<ITitleUnlockCondition, AdvancementCondition> ADVANCEMENT_CONDITION = UNLOCK_CONDITION.register("advancement_condition",
            AdvancementCondition::new);





    public static void register(IEventBus eventBus){
        UNLOCK_CONDITION.register(eventBus);
    }
}
