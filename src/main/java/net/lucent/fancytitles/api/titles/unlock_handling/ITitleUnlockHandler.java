package net.lucent.fancytitles.api.titles.unlock_handling;

/**
 * handles the conditions for unlocking a title,
 * also called when we try to read a title from save data to make sure
 * the player still has it unlocked
 */
public interface ITitleUnlockHandler {



    //pass either raw json or a compound tag or smth
    ITitleUnlockData buildTitleUnlockData();
}
