package me.vovari2.interactivesigns.utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;

public class SoundUtils {
    public static void playPasteItemOnSign(Location location){
        location.getWorld().playSound(location, Sound.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1F, 0.6F);
    }
    public static void playRemoveItemOnSign(Location location){
        location.getWorld().playSound(location, Sound.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1F, 0.6F);
    }
}
