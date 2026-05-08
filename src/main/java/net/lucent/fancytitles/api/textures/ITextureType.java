package net.lucent.fancytitles.api.textures;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Mainly to make future texture types easier, i could just have one type
 * that holds everything and just defaults on omitted fields(which i have already done)
 * but i might want to add stuff like stitching or animated textures which then need more data
 */
public interface ITextureType{

    void render(GuiGraphics graphics,ITextureData textureData);
    void render(GuiGraphics graphics,ITextureData textureData,int width,int height);

    void renderAt(GuiGraphics graphics,ITextureData textureData,int x,int y);
    void renderAt(GuiGraphics graphics,ITextureData textureData,int x,int y,int width,int height);


}
