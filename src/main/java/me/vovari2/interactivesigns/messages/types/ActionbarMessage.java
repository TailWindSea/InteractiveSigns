package me.vovari2.interactivesigns.messages.types;

import me.clip.placeholderapi.PlaceholderAPI;
import me.vovari2.interactivesigns.Delay;
import me.vovari2.interactivesigns.InteractiveSigns;
import me.vovari2.interactivesigns.messages.MessageType;
import me.vovari2.interactivesigns.messages.Messages;
import me.vovari2.interactivesigns.messages.StringMessage;
import me.vovari2.interactivesigns.utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ActionbarMessage extends StringMessage {
    public ActionbarMessage(@NotNull Messages key, @Nullable String message) {
        super(key, message, MessageType.ACTIONBAR);
    }

    public void send(@NotNull CommandSender sender) {
        if (isEmpty())
            return;
        sender.sendMessage("Actionbar cannot be displayed!");
    }
    public void send(@NotNull Player player) {
        if (isEmpty())
            return;
        Delay.run(key, player,
                () -> player.sendActionBar(TextUtils.toComponent(
                        InteractiveSigns.Plugins.PlaceholderAPI.isEnabled() ?
                                PlaceholderAPI.setPlaceholders(player, message) :
                                message)));
    }

    public @NotNull StringMessage replace(@NotNull String placeholder, @NotNull String replacement){
        return new ActionbarMessage(key, message.replaceAll("<%" + placeholder + "%>", replacement));
    }
    public @NotNull StringMessage replace(@NotNull String placeholder, long replacement){
        return replace(placeholder, String.valueOf(replacement));
    }
}
