package me.vovari2.interactivesigns;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.BukkitWorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.vovari2.interactivesigns.exceptions.ComponentException;
import me.vovari2.interactivesigns.listeners.BreakListener;
import me.vovari2.interactivesigns.listeners.ExplodeListener;
import me.vovari2.interactivesigns.listeners.GrowListener;
import me.vovari2.interactivesigns.listeners.InteractListener;
import me.vovari2.interactivesigns.sign.SignRotations;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class InteractiveSigns extends JavaPlugin {

    private static InteractiveSigns instance;
    private static WorldGuardPlugin instanceWG;

    @Override
    public void onEnable() {
        instance = this;
        instanceWG = WorldGuardPlugin.inst();

        try{
            Text.initialize(getServer().getConsoleSender());
            Delay.initialize();
            SignRotations.initialize();

            getServer().getPluginManager().registerEvents(new InteractListener(), this);
            getServer().getPluginManager().registerEvents(new BreakListener(), this);
            getServer().getPluginManager().registerEvents(new ExplodeListener(), this);
            getServer().getPluginManager().registerEvents(new GrowListener(), this);
        }
        catch (ComponentException e) {
            Text.sendMessageToConsole(e.getComponentMessage());
        }

        Text.sendMessageToConsole("<green>Plugin %s %s enabled!".formatted(Text.PLUGIN_NAME, Text.VERSION), true);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        getScheduler().cancelTasks(this);
        Text.sendMessageToConsole("<red>Plugin %s %s disabled!".formatted(Text.PLUGIN_NAME, Text.VERSION), true);
    }

    public static InteractiveSigns getInstance(){
        return instance;
    }
    public static BukkitScheduler getScheduler(){
        return instance.getServer().getScheduler();
    }

    public static WorldGuardPlugin getWorldGuardPlugin(){
        return instanceWG;
    }
    public static RegionContainer getRegionContainer(){
        return WorldGuard.getInstance().getPlatform().getRegionContainer();
    }

    public static BukkitWorldConfiguration getWorldConfiguration(World world){
        return getWorldGuardPlugin().getConfigManager().get(world);
    }
}
