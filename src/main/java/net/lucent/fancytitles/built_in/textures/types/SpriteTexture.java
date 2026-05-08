package net.lucent.fancytitles.built_in.textures.types;

import com.mojang.serialization.MapCodec;
import net.lucent.fancytitles.api.textures.ITextureData;
import net.lucent.fancytitles.api.textures.ITextureType;
import net.lucent.fancytitles.built_in.textures.data.TextureData;
import net.minecraft.client.gui.GuiGraphics;

public class SpriteTexture implements ITextureType {


    @Override
    public void render(GuiGraphics graphics, ITextureData textureData) {
        renderAt(graphics,textureData,0,0);
    }

    @Override
    public void render(GuiGraphics graphics, ITextureData textureData, int width, int height) {
        renderAt(graphics,textureData,0,0,width,height);
    }

    @Override
    public void renderAt(GuiGraphics graphics, ITextureData textureData, int x, int y) {
        renderAt(graphics,textureData,x,y,textureData.getWidth(),textureData.getHeight());
    }

    @Override
    public void renderAt(GuiGraphics graphics, ITextureData textureData, int x, int y, int width, int height) {
        graphics.blitSprite(
                textureData.getIdentifier(),
                textureData.getTextureWidth(),
                textureData.getTextureHeight(),
                textureData.getU(),
                textureData.getV(),
                x,y,
                width,height
        );
    }
}
