package net.lucent.fancytitles.api.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

public abstract class TitleEvents extends Event {
    private final ResourceLocation title;
    private final Player player;

    public TitleEvents(ResourceLocation title, Player player) {
        this.title = title;
        this.player = player;
    }
    public ResourceLocation getTitle(){return title;}
    public Player getPlayer(){return player;}


    public static class TitleEarnedEvent extends TitleEvents{

        public TitleEarnedEvent(ResourceLocation title, Player player) {
            super(title, player);
        }
    }
    public static class TitleLostEvent extends TitleEvents{

        public TitleLostEvent(ResourceLocation title, Player player) {
            super(title, player);
        }
    }
    public static class TitleEquippedEvent extends TitleEvents{

        public TitleEquippedEvent(ResourceLocation title, Player player) {
            super(title, player);
        }
    }
    public static class TitleUnEquippedEvent extends TitleEvents{

        public TitleUnEquippedEvent(ResourceLocation title, Player player) {
            super(title, player);
        }
    }
}
