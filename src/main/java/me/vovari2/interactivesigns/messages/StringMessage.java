package me.vovari2.interactivesigns.messages;

import me.vovari2.interactivesigns.utils.TextUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class StringMessage implements Message {
    protected final Messages key;
    protected final String message;
    protected final MessageType type;

    protected StringMessage(@NotNull Messages key, @Nullable String message, @NotNull MessageType type) {
        this.key = key;
        this.message = message == null ? "" : message;
        this.type = type;
    }

    public @NotNull String string(){
        return message;
    }
    public @NotNull Component component(){
        return TextUtils.toComponent(message);
    }
    public @NotNull MessageType type(){
        return type;
    }

    public boolean isEmpty(){
        return message.isEmpty();
    }

    public abstract void send(@NotNull CommandSender sender);
    public abstract void send(@NotNull Player player);
}
