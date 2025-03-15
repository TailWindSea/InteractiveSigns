package me.vovari2.interactivesigns.text_nodes;

import me.clip.placeholderapi.PlaceholderAPI;
import me.vovari2.interactivesigns.ProtectionPlugins;
import me.vovari2.interactivesigns.utils.TextUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class StringTextNode{
    private final String value;
    public StringTextNode(String value) {
        this.value = value;
    }

    public StringTextNode replacePlaceholderAPI(Player player){
        if (!ProtectionPlugins.isEnabledPlugin("PlaceholderAPI"))
            return this;
        return new StringTextNode(PlaceholderAPI.setPlaceholders(player, value));
    }
    public StringTextNode replace(String placeholder, String placeholderValue) {
        return new StringTextNode(value.replace("<%" + placeholder + "%>", placeholderValue));
    }

    public boolean isEmpty(){
        return value.isEmpty();
    }
    public Component value() {
        return TextUtils.toComponent(value);
    }
}
