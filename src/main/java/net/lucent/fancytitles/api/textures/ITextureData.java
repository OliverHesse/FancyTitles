package net.lucent.fancytitles.api.textures;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;

/**
 * hold data needed to render a texture
 * done like this since I want to be able to differentiate between raw texture
 * and sprites
 */
public interface ITextureData {
    ResourceLocation getTextureType();
    ResourceLocation getIdentifier();

    int getTextureWidth();
    int getTextureHeight();
    int getHeight();
    int getWidth();
    int getU();
    int getV();


}
