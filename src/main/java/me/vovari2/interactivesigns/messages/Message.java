package me.vovari2.interactivesigns.messages;

import me.vovari2.interactivesigns.utils.PlaceholderUtils;
import me.vovari2.interactivesigns.utils.TextUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Message {
    protected final String message;
    protected final Sound sound;

    protected Message(@Nullable String message, @Nullable Sound sound) {
        this.message = message == null ? "" : message;
        this.sound = sound;
    }
    public @NotNull String string(){
        return message;
    }
    public @NotNull Component component(){
        return TextUtils.toComponent(message);
    }
    public @NotNull String string(@NotNull Player player){
        return PlaceholderUtils.replacePlaceholders(player, message);
    }
    public @NotNull Component component(@NotNull Player player){
        return TextUtils.toComponent(string(player));
    }

    private void playSound(@NotNull Audience audience){
        if(sound != null)
            audience.playSound(sound);
    }

    protected abstract void sendInside(@NotNull Audience audience);
    public void send(@NotNull Audience audience){
        playSound(audience);
        if (message.isEmpty())
            return;
        sendInside(audience);
    }

    protected abstract @NotNull Message replaceInside(@NotNull String placeholder, @NotNull String replacement);
    public @NotNull Message replace(@NotNull String placeholder, @NotNull String replacement){
        return replaceInside(placeholder, replacement);
    }
    public @NotNull Message replace(@NotNull String placeholder, int replacement) {
        return replaceInside(placeholder, String.valueOf(replacement));
    }
    public @NotNull Message replace(@NotNull String placeholder, long replacement){
        return replaceInside(placeholder, String.valueOf(replacement));
    }
}
