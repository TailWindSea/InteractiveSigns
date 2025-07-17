package me.vovari2.interactivesigns;

import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public record Timer(@NotNull Runnable runnable, @NotNull WrappedTask task) {
    public static @NotNull Timer period(int wait, int period, int times, @NotNull Timer.PeriodOperation periodOperation){
        BukkitRunnable runnable = new BukkitRunnable() {
            int currentTimes = 0;
            public void run() {
                periodOperation.run(currentTimes);
                currentTimes++;
                if (currentTimes == times)
                    cancel();
            }
        };
        WrappedTask task = InteractiveSigns.getFoliaInstance().getScheduler().runTimer(runnable, wait * 50L, period * 50L);
        return new Timer(runnable, task);
    }
    public static @NotNull Timer wait(int wait, @NotNull Timer.WaitOperation waitOperation){
        BukkitRunnable runnable = new BukkitRunnable() {
            public void run() { waitOperation.run();}
        };
        WrappedTask task = InteractiveSigns.getFoliaInstance().getScheduler().runLater(runnable, wait * 50L);
        return new Timer(runnable, task);
    }
    public interface PeriodOperation {
        void run(int times);
    }
    public interface WaitOperation {
        void run();
    }
}
