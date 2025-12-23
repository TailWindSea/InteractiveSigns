package me.vovari2.interactivesigns;

import com.tcoded.folialib.wrapper.task.WrappedTask;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class Scheduler {
    public static @NotNull Scheduler waitInLocation(@NotNull Location loc, @NotNull Runnable runnable) {
        WrappedTask task = InteractiveSigns.getFoliaInstance().getScheduler().runAtLocationLater(loc, runnable, 0);
        return new Scheduler(runnable, task);
    }

    private final Runnable runnable;
    private final WrappedTask task;
    public Scheduler(@NotNull Runnable runnable, @NotNull WrappedTask task){
        this.runnable = runnable;
        this.task = task;
    }
    public void run(){
        runnable.run();
    }
    public void cancel(){
        if(!task.isCancelled())
            task.cancel();
    }
}
