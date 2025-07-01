package me.vovari2.interactivesigns.utils;

import me.vovari2.interactivesigns.InteractiveSigns;
import org.bukkit.NamespacedKey;

public class NamespacedKeyUtils {
    public static NamespacedKey forItemOnSign(String side){
        return NamespacedKey.fromString("%s.%s".formatted(InteractiveSigns.getPluginName().toLowerCase(), side.toLowerCase()));
    }
    public static NamespacedKey forItemOnSignOld(){
        return NamespacedKey.fromString("%s.%s".formatted(InteractiveSigns.getPluginName().toLowerCase(), "item"));
    }
}
