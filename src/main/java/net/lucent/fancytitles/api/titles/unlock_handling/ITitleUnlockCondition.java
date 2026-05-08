package net.lucent.fancytitles.api.titles.unlock_handling;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;

/**
 * handles the conditions for unlocking a title,
 * also called when we try to read a title from save data to make sure
 * the player still has it unlocked
 */
public interface ITitleUnlockCondition {


    boolean satisfiesUnlockCondition(Player player,ITitleUnlockData data);

    MapCodec<ITitleUnlockData> codec();
}
