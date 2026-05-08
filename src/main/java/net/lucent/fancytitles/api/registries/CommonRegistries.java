package net.lucent.fancytitles.api.registries;

import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.titles.Title;
import net.lucent.fancytitles.api.titles.formatting.ITitleChatFormatter;
import net.lucent.fancytitles.api.titles.rendering.ITitleRenderer;
import net.lucent.fancytitles.api.titles.unlock_handling.ITitleUnlockHandler;
import net.minecraft.core.Registry;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = FancyTitles.MOD_ID)
public class CommonRegistries {

    public static RegistryHelper.DataPackRegistry<Title> TITLE_REGISTRY = RegistryHelper.dataPackRegistry("titles",FancyTitles.MOD_ID,null);

    public static Registry<ITitleRenderer> TITLE_RENDERER_REGISTRY = RegistryHelper.registry("title_card_renderer_registry");

    public static Registry<ITitleChatFormatter> TITLE_CHAT_FORMATTER_REGISTRY = RegistryHelper.registry("title_card_renderer_registry");


    public static Registry<ITitleUnlockHandler> TITLE_UNLOCK_HANDLER_REGISTRY = RegistryHelper.registry("title_unlock_handler_registry");


    public static void registerRegistries(NewRegistryEvent event){
        FancyTitles.LOGGER.info("Creating Title Renderer Registry");
        event.register(TITLE_RENDERER_REGISTRY);
        FancyTitles.LOGGER.info("Creating Title Card Formatter Registry");
        event.register(TITLE_CHAT_FORMATTER_REGISTRY);
        FancyTitles.LOGGER.info("Creating Title Unlock Handler Registry");
        event.register(TITLE_UNLOCK_HANDLER_REGISTRY);
    }
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        FancyTitles.LOGGER.info("Creating Title Datapack Registry");
        event.dataPackRegistry(
                TITLE_REGISTRY.key(),
                TITLE_REGISTRY.codec(),
                TITLE_REGISTRY.codec()
        );
    }
}
