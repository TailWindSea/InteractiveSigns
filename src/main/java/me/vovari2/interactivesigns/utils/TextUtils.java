package me.vovari2.interactivesigns.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TextUtils {
    public static Component toComponent(String value){
        value = value.replaceAll("§.", "");
        value = value.replaceAll("&.", "");
        return MiniMessage.miniMessage().deserialize(value);
    }

}

