package net.lucent.fancytitles.built_in.renderers.simple_renderer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lucent.fancytitles.api.textures.ITextureData;
import net.lucent.fancytitles.api.titles.rendering.ITitleRendererData;

import java.util.Optional;

public record SimpleRendererData(String prefix, Optional<ITextureData> textureData) implements ITitleRendererData {


}
