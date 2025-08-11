package me.vovari2.interactivesigns;

import at.pcgamingfreaks.Minepacks.Bukkit.API.MinepacksPlugin;
import com.fastasyncworldedit.core.configuration.Settings;
import me.vovari2.interactivesigns.listeners.WorldEditListener;
import me.vovari2.interactivesigns.utils.FileUtils;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public enum Plugins {
    CoreProtect,
    PlaceholderAPI,
    FastAsyncWorldEdit,
    WorldEdit,
    Minepacks;

    static void load(){
        PluginManager manager = InteractiveSigns.getInstance().getServer().getPluginManager();

        for (Plugins plugin : Plugins.values()){
            plugin.plugin = manager.getPlugin(plugin.name());
            if (plugin.plugin != null)
                plugin.loadInside();
        }
    }
    static void enable(){
        if (CoreProtect.loaded){
            if (!(CoreProtect.plugin instanceof CoreProtect coreProtect))
                return;

            CoreProtectAPI api = coreProtect.getAPI();
            if (!api.isEnabled()) {
                Console.error("CoreProtect plugin API is not enabled!"); return;}

            if (api.APIVersion() < 10) {
                Console.error("CoreProtect plugin unsupported version v%s (needed v22.4+)!".formatted(coreProtect.getDescription().getVersion())); return;}

            if (FastAsyncWorldEdit.loaded)
                addAllowedPluginInFAWE("net.coreprotect.worldedit.CoreProtectLogger");
            CoreProtect.enableInside();
        }

        if (PlaceholderAPI.loaded)
            PlaceholderAPI.enableInside();

        if (FastAsyncWorldEdit.loaded){
            addAllowedPluginInFAWE("me.vovari2.interactivesigns.listeners.WorldEditListener");
            FastAsyncWorldEdit.enableInside();
        }

        if (WorldEdit.loaded){
            com.sk89q.worldedit.WorldEdit.getInstance().getEventBus().register(new WorldEditListener());
            WorldEdit.enableInside();
        }
        if (Minepacks.loaded){
            if (!(Minepacks.plugin instanceof MinepacksPlugin))
                return;
            Minepacks.enableInside();
        }
    }
    private static void addAllowedPluginInFAWE(@NotNull String classPath){
        File config = new File(new File(FileUtils.getPluginFolder().getParentFile(), "FastAsyncWorldEdit"), "config.yml");
        Settings.settings().load(config);
        if (!Settings.settings().EXTENT.ALLOWED_PLUGINS.contains(classPath)){
            Settings.settings().EXTENT.ALLOWED_PLUGINS.add(classPath);
            Settings.settings().save(config);
        }
    }

    private Plugin plugin;
    private boolean loaded = false;
    private boolean enabled = false;

    public boolean isEnabled(){
        return enabled;
    }
    public @NotNull Plugin getPlugin(){
        return plugin;
    }

    void loadInside(){
        this.loaded = true;
        Console.info("Found %s plugin!".formatted(name()));
    }
    void enableInside(){
        this.enabled = true;
        Console.info("Connected %s plugin!".formatted(name()));
    }
}
