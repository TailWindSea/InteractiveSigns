package me.vovari2.interactivesigns.utils;

import me.vovari2.interactivesigns.Text;
import org.bukkit.NamespacedKey;

public class NamespacedKeyUtils {
    public static NamespacedKey forItemOnSign(String side){
        return NamespacedKey.fromString("%s.%s".formatted(Text.PLUGIN_NAME.toLowerCase(), side.toLowerCase()));
    }
    public static NamespacedKey forItemOnSignOld(){
        return NamespacedKey.fromString("%s.%s".formatted(Text.PLUGIN_NAME.toLowerCase(), "item"));
    }
}
