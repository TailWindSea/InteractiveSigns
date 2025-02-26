package me.vovari2.interactivesigns;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.vovari2.interactivesigns.exceptions.ComponentException;
import me.vovari2.interactivesigns.listeners.BreakListener;
import me.vovari2.interactivesigns.listeners.ExplodeListener;
import me.vovari2.interactivesigns.listeners.GrowListener;
import me.vovari2.interactivesigns.listeners.InteractListener;
import me.vovari2.interactivesigns.sign.SignRotations;
import me.vovari2.interactivesigns.sign.SignTypes;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class InteractiveSigns extends JavaPlugin {

    private static InteractiveSigns instance;
    private static CoreProtectAPI coreProtectAPI;

    public boolean isLoaded;
    public boolean isEnabled;

    @Override
    public void onLoad() {
        isLoaded = true;

        instance = this;
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(false));

        try {
            Text.initialize(getServer().getConsoleSender());
            Config.initialize();

            ProtectionPlugins.load();
        } catch (ComponentException e) {
            Text.sendMessageToConsole(e.getComponentMessage());
            isLoaded = false;
        }

        if (isLoaded)
            Text.sendMessageToConsole("<green>Plugin %s %s loaded!".formatted(Text.PLUGIN_NAME, Text.VERSION), true);
        else Text.sendMessageToConsole("<yellow>Plugin %s %s is not loaded!".formatted(Text.PLUGIN_NAME, Text.VERSION), true);
    }

    @Override
    public void onEnable() {
        isEnabled = true;
        CommandAPI.onEnable();

        Permission.initialize();
        Executor.preInitialize(instance);

        if (isLoaded){
            ProtectionPlugins.initialize();

            Delay.initialize();
            SignTypes.initialize();
            SignRotations.initialize();

            coreProtectAPI = setupCoreProtect();
            Executor.initialize(instance);

            getServer().getPluginManager().registerEvents(new InteractListener(), this);
            getServer().getPluginManager().registerEvents(new BreakListener(), this);
            getServer().getPluginManager().registerEvents(new ExplodeListener(), this);
            getServer().getPluginManager().registerEvents(new GrowListener(), this);
        }
        if (isLoaded && isEnabled)
            Text.sendMessageToConsole("<green>Plugin %s %s enabled!".formatted(Text.PLUGIN_NAME, Text.VERSION), true);
        else Text.sendMessageToConsole("<yellow>Plugin %s %s is not enabled! There was an error in the console above!".formatted(Text.PLUGIN_NAME, Text.VERSION), true);
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        HandlerList.unregisterAll(this);
        getScheduler().cancelTasks(this);
        Text.sendMessageToConsole("<red>Plugin %s %s disabled!".formatted(Text.PLUGIN_NAME, Text.VERSION), true);
    }

    public static CoreProtectAPI setupCoreProtect(){
        Plugin plugin = instance.getServer().getPluginManager().getPlugin("CoreProtect");
        if (!(plugin instanceof CoreProtect coreProtect))
            return null;
        Text.sendMessageToConsole("<green>Found CoreProtect plugin.");

        CoreProtectAPI api = coreProtect.getAPI();
        if (!api.isEnabled()) {
            Text.sendMessageToConsole("<red>CoreProtect plugin API is not enabled!");
            return null;
        }

        if (api.APIVersion() < 10) {
            Text.sendMessageToConsole("<red>CoreProtect plugin unsupported version v%s (needed v22.4+)!".formatted(coreProtect.getDescription().getVersion()));
            return null;
        }
        Text.sendMessageToConsole("<green>Full support for CoreProtect plugin!");

        return api;
    }

    public static InteractiveSigns getInstance(){
        return instance;
    }
    public static CoreProtectAPI getCoreProtectAPI(){
        return coreProtectAPI;
    }
    public static BukkitScheduler getScheduler(){
        return instance.getServer().getScheduler();
    }

    public static RegionContainer getRegionContainer(){
        return WorldGuard.getInstance().getPlatform().getRegionContainer();
    }

}
