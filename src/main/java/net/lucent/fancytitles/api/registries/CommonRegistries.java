package net.lucent.fancytitles.api.registries;

import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.textures.ITextureType;
import net.lucent.fancytitles.api.titles.Title;
import net.lucent.fancytitles.api.titles.formatting.ITitleChatFormatter;
import net.lucent.fancytitles.api.titles.rendering.ITitleRenderer;
import net.lucent.fancytitles.api.titles.unlock_handling.ITitleUnlockCondition;
import net.minecraft.core.Registry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = FancyTitles.MOD_ID)
public class CommonRegistries {

    public static RegistryHelper.DataPackRegistry<Title> TITLE_REGISTRY = RegistryHelper.dataPackRegistry("titles",FancyTitles.MOD_ID,Title::codec);

    public static Registry<ITitleRenderer> TITLE_RENDERER_REGISTRY = RegistryHelper.registry("title_renderer_registry");

    public static Registry<ITitleChatFormatter> TITLE_CHAT_FORMATTER_REGISTRY = RegistryHelper.registry("title_chat_formatter_registry");

    public static Registry<ITitleUnlockCondition> TITLE_UNLOCK_CONDITION_REGISTRY = RegistryHelper.registry("title_unlock_handler_registry");

    public static Registry<ITextureType> TEXTURE_TYPE = RegistryHelper.registry("texture_type");

    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event){
        FancyTitles.LOGGER.info("Creating Title Renderer Registry");
        event.register(TITLE_RENDERER_REGISTRY);
        FancyTitles.LOGGER.info("Creating Title Card Formatter Registry");
        event.register(TITLE_CHAT_FORMATTER_REGISTRY);
        FancyTitles.LOGGER.info("Creating Title Unlock Handler Registry");
        event.register(TITLE_UNLOCK_CONDITION_REGISTRY);
        FancyTitles.LOGGER.info("Creating Texture Type Registry");
        event.register(TEXTURE_TYPE);
    }
    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        FancyTitles.LOGGER.info("Creating Title Datapack Registry");
        event.dataPackRegistry(
                TITLE_REGISTRY.key(),
                TITLE_REGISTRY.codec().get(),
                TITLE_REGISTRY.codec().get()
        );
    }
}
