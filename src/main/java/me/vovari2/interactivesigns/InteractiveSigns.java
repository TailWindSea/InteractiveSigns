package me.vovari2.interactivesigns;

import com.tcoded.folialib.FoliaLib;
import me.vovari2.interactivesigns.configuration.Configuration;
import me.vovari2.interactivesigns.listeners.*;
import me.vovari2.interactivesigns.messages.Messages;
import me.vovari2.interactivesigns.sign.SignTypes;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.AdvancedPie;
import org.bukkit.Server;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class InteractiveSigns extends JavaPlugin {
    private static InteractiveSigns INSTANCE;
    private static FoliaLib FOLIA_INSTANCE;

    private String PLUGIN_NAME;
    private String VERSION;

    private boolean isLoaded = false;
    public void onLoad() {
        INSTANCE = this;
        Console.LOGGER = getComponentLogger();

        PLUGIN_NAME = INSTANCE.getName();
        VERSION = INSTANCE.getPluginMeta().getVersion();

        isLoaded = Messages.load()
                && Configuration.load();

        Plugins.load();
        ProtectionPlugins.load();
    }
    public void onEnable() {
        long time = System.currentTimeMillis();
        FOLIA_INSTANCE = new FoliaLib(this);

        Plugins.enable();
        Executor.register(this);
        ProtectionPlugins.enable();

        registerListeners();
        registerMetrics();

        SignTypes.enable();

        if (isLoaded)
            Console.info("<green>Plugin {} {} enabled! ({} ms)", PLUGIN_NAME, VERSION, System.currentTimeMillis() - time);
        else Console.warn("Plugin {} {} is not enabled! There was an error in the console above!", PLUGIN_NAME, VERSION);
    }
    public void onDisable() {
        unregisterListeners();
        Console.info("<red>Plugin {} {} disabled!", PLUGIN_NAME, VERSION);
    }
    public void onReload() {
        long time = System.currentTimeMillis();

        isLoaded = Messages.load()
                && Configuration.load();

        unregisterListeners();
        registerListeners();

        SignTypes.enable();
        if (isLoaded)
            Console.info("<green>Plugin {} {} reloaded! ({} ms)", PLUGIN_NAME, VERSION, System.currentTimeMillis() - time);
        else Console.warn("Plugin {} {} is not reloaded! There was an error in the console above!", PLUGIN_NAME, VERSION);
    }

    private void registerMetrics(){
        Metrics metrics = new Metrics(this, 26326);
        metrics.addCustomChart(new AdvancedPie("protection_plugins_used", () -> {
            Map<String, Integer> valueMap = new HashMap<>();
            boolean hasAnyProtectionPlugin = false;
            for (ProtectionPlugins.ProtectionPlugin plugin : ProtectionPlugins.plugins()){
                hasAnyProtectionPlugin = true;
                valueMap.put(plugin.name, 1);
            }
            if (!hasAnyProtectionPlugin)
                valueMap.put("<none>", 1);
            return valueMap;
        }));
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getServer().getPluginManager().registerEvents(new BreakListener(), this);
        getServer().getPluginManager().registerEvents(new ExplodeListener(), this);
        getServer().getPluginManager().registerEvents(new GrowListener(), this);
    }
    private void unregisterListeners(){
        HandlerList.unregisterAll(this);
    }

    public static InteractiveSigns getInstance(){
        return INSTANCE;
    }
    public static FoliaLib getFoliaInstance(){
        return FOLIA_INSTANCE;
    }
    public static String getPluginName(){
        return INSTANCE.PLUGIN_NAME;
    }
    public static String getVersion(){
        return INSTANCE.VERSION;
    }
    }
