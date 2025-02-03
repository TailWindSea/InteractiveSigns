package me.vovari2.interactivesigns;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.BukkitWorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
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
    private static boolean enabledWG;
    private static WorldGuardPlugin instanceWG;

    @Override
    public void onLoad(){
        instance = this;

        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();
        enabledWG = this.getServer().getPluginManager().isPluginEnabled("WorldGuard");
        if (enabledWG)
            instanceWG = WorldGuardPlugin.inst();

        try{
            Text.initialize(getServer().getConsoleSender());
            Delay.initialize();
            SignRotations.initialize();

            Config.initialize();

            Permission.initialize();
            Executor.initialize(instance);

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
        CommandAPI.onDisable();
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

    public static boolean isWorldGuard(){
        return enabledWG;
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
