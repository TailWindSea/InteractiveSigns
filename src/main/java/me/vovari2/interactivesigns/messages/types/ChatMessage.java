package me.vovari2.interactivesigns.messages.types;

import me.vovari2.interactivesigns.Delay;
import me.vovari2.interactivesigns.messages.MessageType;
import me.vovari2.interactivesigns.messages.Messages;
import me.vovari2.interactivesigns.messages.StringMessage;
import me.vovari2.interactivesigns.utils.PlaceholderUtils;
import me.vovari2.interactivesigns.utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChatMessage extends StringMessage {
    public ChatMessage(@NotNull Messages key, @Nullable String message){
        super(key, message, MessageType.CHAT);
    }

    public void send(@NotNull CommandSender sender) {
        if (isEmpty())
            return;
        sender.sendMessage(TextUtils.toComponent(message));
    }
    public void send(@NotNull Player player) {
        if (isEmpty())
            return;

        Delay.run(key, player, () ->
                player.sendMessage(PlaceholderUtils.replacePlaceholders(player, message)));
    }

    public @NotNull StringMessage replace(@NotNull String placeholder, @NotNull String replacement){
        return new ChatMessage(key, message.replaceAll("<%" + placeholder + "%>", replacement));
    }
    public @NotNull StringMessage replace(@NotNull String placeholder, long replacement){
        return replace(placeholder, String.valueOf(replacement));
    }
}
