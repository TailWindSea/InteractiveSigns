package me.vovari2.interactivesigns.messages.types;

import me.vovari2.interactivesigns.messages.Message;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ActionbarMessage extends Message {
    public ActionbarMessage(@Nullable String message, @Nullable Sound sound) {
        super(message, sound);
    }

    protected void sendInside(@NotNull Audience audience) {
        if (audience instanceof Player player)
            player.sendMessage(component(player));
        else audience.sendMessage(component());
    }
    protected @NotNull Message replaceInside(@NotNull String placeholder, @NotNull String replacement){
        return new ActionbarMessage(
                message.replaceAll("<%" + placeholder + "%>", replacement),
                sound);
    }
}
