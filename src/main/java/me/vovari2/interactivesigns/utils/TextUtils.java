package me.vovari2.interactivesigns.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;

public class TextUtils {
    public static Component toComponent(String value){
        value = value.replaceAll("§.", "");
        value = value.replaceAll("&.", "");
        return MiniMessage.miniMessage().deserialize(value);
    }
    public static String toString(Location location){
        return "[" + location.getWorld().getName() + " " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ() + "]";
    }

}

