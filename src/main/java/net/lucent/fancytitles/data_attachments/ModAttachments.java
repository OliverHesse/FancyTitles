package net.lucent.fancytitles.data_attachments;

import com.mojang.serialization.Codec;
import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.data_attachments.title_attachment.TitleProvider;
import net.lucent.fancytitles.data_attachments.title_attachment.TitlesContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, FancyTitles.MOD_ID);

    public static final Supplier<AttachmentType<TitlesContainer>> PLAYER_TITLES = ATTACHMENT_TYPES.register(
            "player_titles",
            ()->AttachmentType.<TitlesContainer>builder((holder) -> holder instanceof Player player ? new TitlesContainer(player):null)
                    .serialize(new TitleProvider())
                    .copyOnDeath()
                    .build());

    public static void register(IEventBus modEventBus){
        ATTACHMENT_TYPES.register(modEventBus);
    }
}
