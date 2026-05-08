package net.lucent.fancytitles.network.client_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.data.ITitleContainer;
import net.lucent.fancytitles.data_attachments.ModAttachments;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * syncs the active title of a player to the client
 * @param playerId the player it is being synced for, this could include tracked players not just the player themselves
 * @param title the title that is active, if empty remove the current active title
 */
public record SyncPlayerActiveTitle(UUID playerId, Optional<ResourceLocation> title) implements CustomPacketPayload {
    public static final Type<SyncPlayerActiveTitle> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(FancyTitles.MOD_ID, "sync_player_active_title"));
    public static final StreamCodec<ByteBuf, SyncPlayerActiveTitle> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC,
            SyncPlayerActiveTitle::playerId,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC),
            SyncPlayerActiveTitle::title,
            SyncPlayerActiveTitle::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static void handlePayload(SyncPlayerActiveTitle payload, IPayloadContext context) {

        context.enqueueWork(()->{
            Player player = context.player();
            if(!player.getUUID().equals(payload.playerId)){
                player = player.level().getPlayerByUUID(payload.playerId);
            }
            ITitleContainer titleContainer = player.getData(ModAttachments.PLAYER_TITLES);

            if(payload.title.isEmpty()) titleContainer.removeActiveTitle();
            else titleContainer.setActiveTitle(payload.title.get());
        });
    }
}