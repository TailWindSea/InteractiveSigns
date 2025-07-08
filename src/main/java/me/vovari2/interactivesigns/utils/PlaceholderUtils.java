package me.vovari2.interactivesigns.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.vovari2.interactivesigns.InteractiveSigns;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderUtils {
    public static @NotNull String replacePlaceholders(@NotNull Player player, @NotNull String message){
        return InteractiveSigns.Plugins.PlaceholderAPI.isEnabled()
                ? PlaceholderAPI.setPlaceholders(player, message) : message;
    }
}
