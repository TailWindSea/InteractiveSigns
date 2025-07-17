package me.vovari2.interactivesigns.messages.types;

import me.vovari2.interactivesigns.messages.Message;
import me.vovari2.interactivesigns.utils.TextUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TitleMessage extends Message {
    private final String subtitle;
    private final Title.Times times;

    public TitleMessage(@Nullable String title, @Nullable Sound sound, @Nullable String subtitle, @NotNull Title.Times times) {
        super(title, sound);
        this.subtitle = subtitle == null ? "" : subtitle;
        this.times = times;
    }

    protected void sendInside(@NotNull Audience audience) {
        if (audience instanceof Player player)
            player.showTitle(Title.title(
                component(player),
                TextUtils.toComponent(subtitle),
                times));
        else audience.sendMessage(component());
    }
    protected @NotNull Message replaceInside(@NotNull String placeholder, @NotNull String replacement){
        return new TitleMessage(
                message.replaceAll("<%" + placeholder + "%>", replacement),
                sound,
                subtitle.replaceAll("<%" + placeholder + "%>", replacement),
                times);
    }
}
