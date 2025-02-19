package me.vovari2.interactivesigns.utils;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class SoundUtils {
    public static void playPasteItemOnSign(Location location){
        location.getWorld().playSound(location, Sound.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1F, 0.6F);
    }
    public static void playRemoveItemOnSign(Location location){
        location.getWorld().playSound(location, Sound.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1F, 0.6F);
    }

    public static void playWaxOnItemOnSign(Location location){
        location.getWorld().playSound(location, Sound.ITEM_HONEYCOMB_WAX_ON, SoundCategory.BLOCKS, 1F, 1F);
    }
    public static void playWaxOffItemOnSign(Location location){
        location.getWorld().playSound(location, Sound.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1F, 0.9F);
    }

    public static void playToolBreak(Player player){
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1F, 1F);
    }
}
