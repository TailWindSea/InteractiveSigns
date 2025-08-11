package me.vovari2.interactivesigns;

import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public record Timer(@NotNull Runnable runnable, @NotNull WrappedTask task) {
    public static @NotNull Timer wait(int wait, @NotNull Timer.WaitOperation waitOperation){
        BukkitRunnable runnable = new BukkitRunnable() {
            public void run() { waitOperation.run();}
        };
        WrappedTask task = InteractiveSigns.getFoliaInstance().getScheduler().runLater(runnable, wait);
        return new Timer(runnable, task);
    }

    public interface PeriodOperation {
        void run(int times);
    }
    public interface WaitOperation {
        void run();
    }
}
