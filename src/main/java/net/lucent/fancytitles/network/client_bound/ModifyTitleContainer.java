package net.lucent.fancytitles.network.client_bound;

import io.netty.buffer.ByteBuf;
import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.data.ITitleContainer;
import net.lucent.fancytitles.data_attachments.ModAttachments;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public record ModifyTitleContainer(List<ResourceLocation> toAdd, List<ResourceLocation> toRemove) implements CustomPacketPayload {
    public static final Type<ModifyTitleContainer> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(FancyTitles.MOD_ID, "modify_title_container"));
    public static final StreamCodec<ByteBuf, ModifyTitleContainer> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(
                    ArrayList::new,
                    ResourceLocation.STREAM_CODEC
            ),
            ModifyTitleContainer::toAdd,
            ByteBufCodecs.collection(
                    ArrayList::new,
                    ResourceLocation.STREAM_CODEC
            ),
            ModifyTitleContainer::toRemove,
            ModifyTitleContainer::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static void handlePayload(ModifyTitleContainer payload, IPayloadContext context) {

        context.enqueueWork(()->{
            ITitleContainer titleContainer = context.player().getData(ModAttachments.PLAYER_TITLES);

            for(ResourceLocation title : payload.toAdd()) titleContainer.addTitle(title);
            for(ResourceLocation title : payload.toRemove()) titleContainer.removeTitle(title);
        });
    }
}