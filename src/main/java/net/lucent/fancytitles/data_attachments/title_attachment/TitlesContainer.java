package net.lucent.fancytitles.data_attachments.title_attachment;

import net.lucent.fancytitles.FancyTitles;
import net.lucent.fancytitles.api.data.ITitleContainer;
import net.lucent.fancytitles.api.events.TitleEvents;
import net.lucent.fancytitles.api.registries.CommonRegistries;
import net.lucent.fancytitles.api.titles.Title;
import net.lucent.fancytitles.network.client_bound.ModifyTitleContainer;
import net.lucent.fancytitles.network.client_bound.SyncPlayerActiveTitle;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;

public class TitlesContainer implements ITitleContainer {
    private final Player player;
    //should be synced to all players
    private ResourceLocation activeTitle;

    //only sync to the player that owns this
    private final ArrayList<ResourceLocation> unlockedTitles = new ArrayList<>();
    private final ArrayList<ResourceLocation> added = new ArrayList<>();
    private final ArrayList<ResourceLocation> removed = new ArrayList<>();
    public TitlesContainer(Player player) {
        this.player = player;
    }

    /**
     * reads all the tags the player has unlocked and adds them
     * then loops through all titles in the registry and checks if the player satisfies the condition
     * if yes but not added add
     * if no and added remove
     *
     * @param player the player this attachment is attached too
     * @param tag holds the saved skills
     */
    public TitlesContainer(Player player, CompoundTag tag){
        this.player = player;

        Registry<Title> registry = CommonRegistries.TITLE_REGISTRY.get(player.level());

        if(registry == null)return;

        ListTag titles = tag.getList("titles", Tag.TAG_STRING);
        for(int i =0;i<titles.size();i++){
            ResourceLocation identifier = ResourceLocation.parse(titles.getString(i));
            if(registry.containsKey(identifier)) addTitle(identifier);

        }


        for(ResourceLocation titleIdentifier : registry.keySet()){
            Title title = registry.get(titleIdentifier);
            if(title == null) continue;
            if(
                    !title.getCondition().satisfiesUnlockCondition(player,title.getConditionData())
                    && hasTitle(titleIdentifier)
            ){
                removeTitle(titleIdentifier);
            }else if(
                    title.getCondition().satisfiesUnlockCondition(player,title.getConditionData())
                    && !hasTitle(titleIdentifier)
            ){
                addTitle(titleIdentifier);
            }
        }
    }

    /**
     * writes all the titles the player currently has unlocked
     * @param tag
     */
    public void write(CompoundTag tag){
        ListTag titles = new ListTag();
        for (ResourceLocation unlockedTitle : unlockedTitles) {
            titles.add(StringTag.valueOf(unlockedTitle.toString()));
        }
        tag.put("titles",titles);
    }


    @Override
    public boolean hasTitleEquipped() {
        return activeTitle != null;

    }

    @Override
    public ResourceLocation getActiveTitleIdentifier() {
        return activeTitle;
    }

    @Override
    public Title getActiveTitle() {
        if(activeTitle == null)return null;
        return CommonRegistries.TITLE_REGISTRY.get(player.level()).get(activeTitle);
    }

    @Override
    public void setActiveTitle(ResourceLocation identifier) {
        removeActiveTitle();
        this.activeTitle = identifier;
        if(identifier != null){
            NeoForge.EVENT_BUS.post(new TitleEvents.TitleEquippedEvent(identifier,player));
            FancyTitles.LOGGER.info("player set title {} as active",identifier);

        }
    }

    @Override
    public void removeActiveTitle() {
        if(activeTitle == null) return;
        TitleEvents.TitleUnEquippedEvent event = new TitleEvents.TitleUnEquippedEvent(activeTitle,player);
        activeTitle = null;
        NeoForge.EVENT_BUS.post(event);
        FancyTitles.LOGGER.info("player removed active title {}",event.getTitle());
    }

    @Override
    public Collection<ResourceLocation> getUnlockedTitles() {
        return unlockedTitles;
    }

    @Override
    public void addTitle(ResourceLocation title) {
        if(hasTitle(title)) return;
        unlockedTitles.add(title);
        added.add(title);
        NeoForge.EVENT_BUS.post(new TitleEvents.TitleEarnedEvent(title,player));
        FancyTitles.LOGGER.info("player unlocked title {}",title);
    }

    @Override
    public void removeTitle(ResourceLocation title) {
        if(!hasTitle(title)) return;
        unlockedTitles.remove(title);
        removed.add(title);
        NeoForge.EVENT_BUS.post(new TitleEvents.TitleLostEvent(title,player));
        FancyTitles.LOGGER.info("player lost title {}",title);

    }

    @Override
    public boolean hasTitle(ResourceLocation title) {
        return unlockedTitles.contains(title);
    }

    @Override
    public Title getTitle(ResourceLocation tile) {
        return CommonRegistries.TITLE_REGISTRY.get(player.level()).get(tile);
    }

    public void sync(){
        if(!(player instanceof ServerPlayer serverPlayer) || serverPlayer.connection == null) return;

        PacketDistributor.sendToPlayer(serverPlayer,new ModifyTitleContainer(added, removed));
        added.clear();
        removed.clear();


        PacketDistributor.sendToPlayersTrackingEntityAndSelf(serverPlayer,
                new SyncPlayerActiveTitle(serverPlayer.getUUID(),Optional.ofNullable(getActiveTitleIdentifier())));
    }
}
