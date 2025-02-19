package me.vovari2.interactivesigns.utils;

import me.vovari2.interactivesigns.Text;
import org.bukkit.NamespacedKey;
import org.bukkit.block.sign.Side;

public class NamespacedKeyUtils {
    public static NamespacedKey forItemOnSign(Side side){
        return NamespacedKey.fromString("%s.%s".formatted(Text.PLUGIN_NAME.toLowerCase(), side.name().toLowerCase()));
    }
}
