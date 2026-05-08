package net.lucent.fancytitles.built_in.textures.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lucent.fancytitles.api.textures.ITextureData;
import net.lucent.fancytitles.built_in.formatters.simple_formatter.SimpleFormatterData;
import net.lucent.fancytitles.built_in.renderers.simple_renderer.SimpleRendererData;
import net.lucent.fancytitles.built_in.textures.BuiltInTextures;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record TextureData(ResourceLocation type,ResourceLocation identifier,int textureWidth,int textureHeight,int width,int height,int u,int v) implements ITextureData {
    @Override
    public ResourceLocation getTextureType() {
        return type;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return identifier;
    }

    @Override
    public int getTextureWidth() {
        return textureWidth;
    }

    @Override
    public int getTextureHeight() {
        return textureHeight;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getU() {
        return u;
    }

    @Override
    public int getV() {
        return v;
    }

    public static Codec<ITextureData> CODEC = RecordCodecBuilder.<TextureData>create(
            instance ->
                    instance.group(
                            ResourceLocation.CODEC.optionalFieldOf("type", BuiltInTextures.TEXTURE.getId()).forGetter(TextureData::type),
                            ResourceLocation.CODEC.fieldOf("texture").forGetter(TextureData::identifier),
                            Codec.INT.fieldOf("texture_width").forGetter(TextureData::textureWidth),
                            Codec.INT.fieldOf("texture_height").forGetter(TextureData::textureHeight),
                            Codec.INT.optionalFieldOf("u",0).forGetter(TextureData::u),
                            Codec.INT.optionalFieldOf("v",0).forGetter(TextureData::v),
                            Codec.INT.optionalFieldOf("width").forGetter(val-> Optional.of(val.width)),
                            Codec.INT.optionalFieldOf("height").forGetter(val-> Optional.of(val.height))

                    ).apply(instance, (type,texture,tWidth,tHeight,u,v,width,height)->
                            new TextureData(type,texture,tWidth,tHeight,width.orElse(tWidth),height.orElse(tHeight),u,v)
                            )
            ).xmap(data->data,data->(TextureData) data);
}
