package net.lucent.fancytitles.built_in.textures;

import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.registries.CommonRegistries;
import net.lucent.fancytitles.api.textures.ITextureType;
import net.lucent.fancytitles.built_in.textures.data.TextureData;
import net.lucent.fancytitles.built_in.textures.types.SpriteTexture;
import net.lucent.fancytitles.built_in.textures.types.Texture;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BuiltInTextures {
    public static final DeferredRegister<ITextureType> TEXTURE_TYPES = DeferredRegister.create(CommonRegistries.TEXTURE_TYPE, FancyTitles.MOD_ID);




    public static final DeferredHolder<ITextureType, Texture> TEXTURE = TEXTURE_TYPES.register("texture",
            Texture::new);
    public static final DeferredHolder<ITextureType, SpriteTexture> SPRITE_TEXTURE = TEXTURE_TYPES.register("sprite_texture",
            SpriteTexture::new);





    public static void register(IEventBus eventBus){
        TEXTURE_TYPES.register(eventBus);
    }
}
