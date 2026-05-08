package net.lucent.fancytitles.api.data;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

/**
 *
 * stores the active title and the renderer
 *
 *  stores the unlocked titles.
 */
public interface ITitleContainer {


    boolean hasTitleEquipped();

    ResourceLocation getActiveTitleIdentifier();
    //ITitleRenderer getActiveRenderer(); this will be used in
    // ClientTitleContainer but not serverTitleContainer so to better separate client and server logic i will not include it

    void setActiveTitle(ResourceLocation identifier);

    void removeActiveTitle();



    Collection<ResourceLocation> getUnlockedTitles();
    void addTitle(ResourceLocation title);
    void removeTitle(ResourceLocation title);
    boolean hasTitle(ResourceLocation title);

}
