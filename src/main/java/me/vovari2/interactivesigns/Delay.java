package me.vovari2.interactivesigns;

import me.vovari2.interactivesigns.messages.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Delay {
    private static final List<Record> records = new LinkedList<>();
    private record Record(@NotNull Messages key, @NotNull CommandSender sender) {
        private boolean equals(Messages key, CommandSender sender) {
            return this.key.equals(key) && this.sender.equals(sender);
        }
    }

    private static boolean is(@NotNull Messages key, @NotNull CommandSender sender){
        for (Record record : records)
            if (record.equals(key, sender))
                return true;
        return false;
    }
    private static void add(@NotNull Messages key, @NotNull CommandSender sender, long delay){
        Record record = new Record(key, sender);
        records.add(record);
        InteractiveSigns.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(InteractiveSigns.getInstance(), () -> records.remove(record), delay);
    }
    public static void run(@NotNull Messages key, @NotNull CommandSender sender, long delay, @NotNull Operation operation){
        if (!is(key, sender)){
            operation.run();
            add(key, sender, delay);
        }
    }
    public static void run(@NotNull Messages key, @NotNull CommandSender sender, @NotNull Operation operation){
        run(key, sender, 20, operation);
    }

    public interface Operation {
        void run();
    }
}
