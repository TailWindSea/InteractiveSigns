package me.vovari2.interactivesigns.messages;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Message {
    @NotNull String string();
    @NotNull Component component();
    @NotNull MessageType type();
    boolean isEmpty();

    @NotNull StringMessage replace(@NotNull String placeholder, @NotNull String replacement);
    @NotNull StringMessage replace(@NotNull String placeholder, long replacement);

    void send(@NotNull CommandSender sender);
    void send(@NotNull Player player);
}
