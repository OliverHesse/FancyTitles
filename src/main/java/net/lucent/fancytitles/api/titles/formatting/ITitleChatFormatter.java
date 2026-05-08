package net.lucent.fancytitles.api.titles.formatting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Component;

public interface ITitleChatFormatter {


    /**
     *
     * @param currentMessage the message that was going to be sent
     * @return the message modified by the title
     */
    Component getModifiedChatMessage(Component currentMessage);


    MapCodec<ITitleChatFormatterData> codec();
}
