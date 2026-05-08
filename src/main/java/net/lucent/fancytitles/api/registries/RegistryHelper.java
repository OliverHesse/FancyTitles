package net.lucent.fancytitles.api.registries;

import com.mojang.serialization.Codec;
import net.lucent.fancytitles.FancyTitles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.Optional;
import java.util.function.Supplier;

public class RegistryHelper {



    public record DataPackRegistry<T>(ResourceKey<Registry<T>> key, Supplier<Codec<T>> codec){

        public Registry<T> get(Level level){
            Optional<Registry<T>> registry = level.registryAccess().registry(key);
            if(registry.isEmpty()){
                FancyTitles.LOGGER.error("error when trying to access registry with key {}", key.location());
                return null;
            }
            return level.registryAccess().registry(key).orElse(null);
        }
    }

    public static <T> DataPackRegistry<T> dataPackRegistry(String key, String modId, Supplier<Codec<T>> codec){
        return new DataPackRegistry<>(key(key,modId),codec);
    }

    public static <T> ResourceKey<Registry<T>> key(String key,String modId){
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(modId,key));
    }


    public static <T> Registry<T> registry(String key){
        return registry(key,"none");
    }
    public static <T> Registry<T> registry(String key,String defaultKey){
        ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(FancyTitles.MOD_ID,key);
        ResourceLocation defaultIdentifier = ResourceLocation.fromNamespaceAndPath(FancyTitles.MOD_ID,defaultKey);

        ResourceKey<Registry<T>> registryKey = ResourceKey.createRegistryKey(identifier);
        return new RegistryBuilder<>(registryKey)
                .defaultKey(defaultIdentifier)
                .create();
    }

}
