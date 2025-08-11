package me.vovari2.interactivesigns.messages.types;

import me.vovari2.interactivesigns.Timer;
import me.vovari2.interactivesigns.messages.Message;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class BossbarMessage extends Message {
    private final BossBar.Color color;
    private final BossBar.Overlay overlay;
    private final int stayTime;
    public BossbarMessage(@Nullable String message, @Nullable Sound sound, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, int stayTime){
        super(message, sound);
        this.color = color;
        this.overlay = overlay;
        this.stayTime = stayTime;
    }
    protected void sendInside(@NotNull Audience audience){
        if (audience instanceof Player player){
            BossBar bossBar = BossBar.bossBar(
                    component(player),
                    1f,
                    color,
                    overlay);
            player.showBossBar(bossBar);
            SendBossbarMessages.start(player.getUniqueId(), () -> player.hideBossBar(bossBar));
        }
        else audience.sendMessage(component());
    }
    protected @NotNull Message replaceInside(@NotNull String placeholder, @NotNull String replacement){
        return new BossbarMessage(
                message.replaceAll("<%" + placeholder + "%>", replacement),
                sound,
                color,
                overlay,
                stayTime);
    }

    public static class SendBossbarMessages {
        private static final HashMap<UUID, Timer> timers = new HashMap<>();
        public static void start(@NotNull UUID player, @NotNull Timer.WaitOperation operation){
            if (timers.containsKey(player)){
                Timer timer = timers.get(player);
                timer.runnable().run();
                timer.task().cancel();
            }
            timers.put(player, Timer.wait(40 * 50, operation));
        }
    }
}
