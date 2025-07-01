package me.vovari2.interactivesigns.messages.types;

import me.clip.placeholderapi.PlaceholderAPI;
import me.vovari2.interactivesigns.Delay;
import me.vovari2.interactivesigns.InteractiveSigns;
import me.vovari2.interactivesigns.messages.MessageType;
import me.vovari2.interactivesigns.messages.Messages;
import me.vovari2.interactivesigns.messages.StringMessage;
import me.vovari2.interactivesigns.utils.TextUtils;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TitleMessage extends StringMessage {
    private final String subtitle;
    private final Title.Times times;

    public TitleMessage(@NotNull Messages key, @Nullable String title, @Nullable String subtitle, @NotNull Title.Times times) {
        super(key, title, MessageType.TITLE);
        this.subtitle = subtitle == null ? "" : subtitle;
        this.times = times;
    }

    public void send(@NotNull CommandSender sender) {
        if (isEmpty())
            return;
        sender.sendMessage("Title cannot be displayed!");
    }
    public void send(@NotNull Player player) {
        if (isEmpty())
            return;

        Delay.run(key, player, () ->
                player.showTitle(Title.title(
                        TextUtils.toComponent(InteractiveSigns.Plugins.PlaceholderAPI.isEnabled() ?
                                PlaceholderAPI.setPlaceholders(player, message) :
                                message),
                        TextUtils.toComponent(subtitle),
                        times))
        );
    }

    public @NotNull StringMessage replace(@NotNull String placeholder, @NotNull String replacement){
        return new TitleMessage(
                key,
                message.replaceAll("<%" + placeholder + "%>", replacement),
                subtitle.replaceAll("<%" + placeholder + "%>", replacement),
                times);
    }
    public @NotNull StringMessage replace(@NotNull String placeholder, long replacement){
        return replace(placeholder, String.valueOf(replacement));
    }
}
