package net.lucent.fancytitles.network.server_bound;

import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record UpdateSelectedTitle(Optional<ResourceLocation> title) {
}
