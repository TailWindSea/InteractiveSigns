package me.vovari2.interactivesigns;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Delay {
    private record DelayOfPlayer(Player player, String name) {
        private boolean equals(Player player, String name) {
            return this.player.equals(player) && this.name.equals(name);
        }
    }

    private static List<DelayOfPlayer> delaysOfPlayers;
    public static void initialize(){
        delaysOfPlayers = new ArrayList<>();
    }
    public static void run(Function func, Player player, String name, long delay){
        if (!is(player, name)){
            func.run();
            add(player, name, delay);
        }
    }
    public static boolean is(Player player, String name){
        for (DelayOfPlayer delayOfPlayer : delaysOfPlayers)
            if (delayOfPlayer.equals(player, name))
                return true;
        return false;
    }
    public static void add(Player player, String name, long delay){
        DelayOfPlayer delayOfPlayer = new DelayOfPlayer(player, name);
        delaysOfPlayers.add(delayOfPlayer);
        InteractiveSigns.getScheduler().runTaskLater(InteractiveSigns.getInstance(), () -> delaysOfPlayers.remove(delayOfPlayer), delay);
    }
}
