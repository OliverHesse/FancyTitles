package net.lucent.fancytitles.event_handlers;

import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.data.ITitleContainer;
import net.lucent.fancytitles.data_attachments.ModAttachments;
import net.lucent.fancytitles.network.client_bound.SyncPlayerActiveTitle;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;

@EventBusSubscriber(modid = FancyTitles.MOD_ID)
public class TrackingHandler {
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if (!(event.getTarget() instanceof ServerPlayer tracked) || !(event.getEntity() instanceof ServerPlayer tracker)) {
            return;
        }

        ITitleContainer titleContainer = tracked.getData(ModAttachments.PLAYER_TITLES);

        PacketDistributor.sendToPlayer(
                tracker,
                new SyncPlayerActiveTitle(
                        tracked.getUUID(),
                        Optional.ofNullable(titleContainer.getActiveTitleIdentifier())
                )
        );
    }
}
