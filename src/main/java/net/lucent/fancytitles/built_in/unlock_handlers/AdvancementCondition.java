package net.lucent.fancytitles.built_in.unlock_handlers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.data.ITitleContainer;
import net.lucent.fancytitles.api.registries.CommonRegistries;
import net.lucent.fancytitles.api.titles.Title;
import net.lucent.fancytitles.api.titles.rendering.ITitleRendererData;
import net.lucent.fancytitles.api.titles.unlock_handling.ITitleUnlockCondition;
import net.lucent.fancytitles.api.titles.unlock_handling.ITitleUnlockData;
import net.lucent.fancytitles.built_in.renderers.simple_renderer.SimpleRendererData;
import net.lucent.fancytitles.built_in.textures.data.TextureData;
import net.lucent.fancytitles.data_attachments.ModAttachments;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = FancyTitles.MOD_ID)
public class AdvancementCondition implements ITitleUnlockCondition {



    public record Data(ResourceLocation advancementId) implements ITitleUnlockData {

    }
    private static final MapCodec<ITitleUnlockData> CODEC  =
    RecordCodecBuilder.<Data>mapCodec(instance ->
            instance.group(

            ResourceLocation.CODEC.fieldOf("advancement").forGetter(Data::advancementId)
            ).apply(instance, Data::new)
        ).xmap(data->data,data -> (Data) data);

    @Override
    public boolean satisfiesUnlockCondition(Player player, ITitleUnlockData data) {
        if(!(data instanceof Data advancementData)) return false;

        if(advancementData.advancementId() == null){
            return false;
        }
        if(!(player instanceof ServerPlayer serverPlayer)) return false; //this should only be called on the server
        AdvancementHolder holder = player.getServer().getAdvancements().get(advancementData.advancementId);
        if(holder == null) return false;

        if(!serverPlayer.getAdvancements().progress.containsKey(holder))return false;

        return serverPlayer.getAdvancements().progress.get(holder).isDone();
    }

    @Override
    public MapCodec<ITitleUnlockData> codec() {
        return CODEC;
    }

    @SubscribeEvent
    public static void onAdvancementEarned(AdvancementEvent.AdvancementEarnEvent event){
        Registry<Title> titleRegistry = CommonRegistries.TITLE_REGISTRY.get(event.getEntity().level());
        System.out.println("achievement " + event.getAdvancement().toString()+ "gained checking conditions");

        List<Title> titles = titleRegistry.stream().toList();
        for (Title title : titles){
            if(!(title.getCondition() instanceof AdvancementCondition advancementCondition)) continue;

            if(!(title.getConditionData() instanceof Data data)) continue;
            if(!event.getAdvancement().id().equals(data.advancementId)) continue;

            if(!advancementCondition.satisfiesUnlockCondition(event.getEntity(),title.getConditionData()))continue;
            System.out.println("player satisfies condition");
            ITitleContainer container = event.getEntity().getData(ModAttachments.PLAYER_TITLES);
            ResourceLocation titleIdentifier = titleRegistry.getKey(title);
            if(container.hasTitle(titleIdentifier)) continue;

            container.addTitle(titleIdentifier);
            System.out.println("title added");

        }
    }
}
