package me.vovari2.interactivesigns.messages.types;

import me.clip.placeholderapi.PlaceholderAPI;
import me.vovari2.interactivesigns.Delay;
import me.vovari2.interactivesigns.InteractiveSigns;
import me.vovari2.interactivesigns.messages.MessageType;
import me.vovari2.interactivesigns.messages.Messages;
import me.vovari2.interactivesigns.messages.StringMessage;
import me.vovari2.interactivesigns.utils.TextUtils;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class BossbarMessage extends StringMessage {
    private final BossBar.Color color;
    private final BossBar.Overlay overlay;
    private final int stayTime;
    public BossbarMessage(@NotNull Messages key, @Nullable String message, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, int stayTime){
        super(key, message, MessageType.BOSSBAR);
        this.color = color;
        this.overlay = overlay;
        this.stayTime = stayTime;
    }

    public void send(@NotNull CommandSender sender){
        if (isEmpty())
            return;
        sender.sendMessage("Bossbar cannot be displayed!");
    }
    public void send(@NotNull Player player){
        send(player,1f);
    }
    public void send(@NotNull Player player, float bossbarValue){
        if (isEmpty())
            return;

        Delay.run(key, player, () -> {
            BossBar bossBar = BossBar.bossBar(
                    TextUtils.toComponent(InteractiveSigns.Plugins.PlaceholderAPI.isEnabled() ?
                            PlaceholderAPI.setPlaceholders(player, message) :
                            message),
                    bossbarValue,
                    color,
                    overlay);
            player.showBossBar(bossBar);
            AutoHideBossbarMessage.start(player.getUniqueId(), new BukkitRunnable() {
                public void run() {
                    player.hideBossBar(bossBar);
                }
            });
        });
    }

    public @NotNull StringMessage replace(@NotNull String placeholder, @NotNull String replacement){
        return new BossbarMessage(key, message.replaceAll("<%" + placeholder + "%>", replacement), color, overlay, stayTime);
    }
    public @NotNull StringMessage replace(@NotNull String placeholder, long replacement){
        return replace(placeholder, String.valueOf(replacement));
    }

    public static class AutoHideBossbarMessage {
        private static final HashMap<UUID, RunnableAndTask> tasks = new HashMap<>();
        public static void start(@NotNull UUID player, @NotNull BukkitRunnable runnable){
            if (tasks.containsKey(player)){
                RunnableAndTask task = tasks.get(player);
                task.runnable.run();
                task.task.cancel();
            }
            tasks.put(player, new RunnableAndTask(runnable, runnable.runTaskLater(InteractiveSigns.getInstance(), 100)));
        }

        private record RunnableAndTask(BukkitRunnable runnable, BukkitTask task){}
    }
}
