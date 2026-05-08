package net.lucent.fancytitles.data_attachments.title_attachment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TitleProvider implements IAttachmentSerializer<CompoundTag,TitlesContainer> {
    @Override
    public TitlesContainer read(@NotNull IAttachmentHolder holder, @NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        if(!(holder instanceof Player player)) return null;
        if(holder instanceof ServerPlayer serverPlayer) return new TitlesContainer(player,tag);
        return new TitlesContainer(player);
    }

    @Override
    public @Nullable CompoundTag write(TitlesContainer container, HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        container.write(tag);
        return tag;
    }
}
