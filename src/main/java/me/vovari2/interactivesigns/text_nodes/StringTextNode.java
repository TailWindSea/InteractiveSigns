package me.vovari2.interactivesigns.text_nodes;

import me.clip.placeholderapi.PlaceholderAPI;
import me.vovari2.interactivesigns.InteractiveSigns;
import me.vovari2.interactivesigns.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StringTextNode {
    private final String value;
    public StringTextNode(String value) {
        this.value = value;
    }

    public StringTextNode replacePlaceholderAPI(Player player){
        if (!InteractiveSigns.hasPlaceholderAPI())
            return this;
        return new StringTextNode(PlaceholderAPI.setPlaceholders(player, value));
    }
    public StringTextNode replace(String placeholder, String placeholderValue) {
        return new StringTextNode(value.replace("<%" + placeholder + "%>", placeholderValue));
    }

    public void send(CommandSender sender){
        if (value.isEmpty())
            return;
        if (sender instanceof Player player)
            player.sendMessage(Text.toComponent(replacePlaceholderAPI(player).value));
        else sender.sendMessage(Text.toComponent(value));
    }
    public Component value(){
        return Text.toComponent(value);
    }
}
