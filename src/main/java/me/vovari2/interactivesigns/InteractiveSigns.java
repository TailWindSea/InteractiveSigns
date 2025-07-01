package me.vovari2.interactivesigns;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.vovari2.interactivesigns.bstats.Metrics;
import me.vovari2.interactivesigns.listeners.BreakListener;
import me.vovari2.interactivesigns.listeners.ExplodeListener;
import me.vovari2.interactivesigns.listeners.GrowListener;
import me.vovari2.interactivesigns.listeners.InteractListener;
import me.vovari2.interactivesigns.loader.ConfigurationLoader;
import me.vovari2.interactivesigns.messages.MessagesLoader;
import me.vovari2.interactivesigns.sign.SignTypes;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class InteractiveSigns extends JavaPlugin {
    private static InteractiveSigns INSTANCE;

    private String PLUGIN_NAME;
    private String VERSION;
    private String AUTHOR;

    public void onLoad() {
        INSTANCE = this;
        Console.LOGGER = getComponentLogger();

        PLUGIN_NAME = INSTANCE.getName();
        VERSION = INSTANCE.getPluginMeta().getVersion();
        AUTHOR = INSTANCE.getPluginMeta().getAuthors().get(0);

        ProtectionPlugins.load();

        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(false));
    }

    private boolean isLoaded = false;
    public void onEnable() {
        long time = System.currentTimeMillis();
        CommandAPI.onEnable();

        Plugins.initialize();
        ProtectionPlugins.initialize();
        Executor.pre_register(this);
        isLoaded = MessagesLoader.initialize()
                && ConfigurationLoader.initialize();
        initializeListeners();

        new Metrics(this, 26326);

        if (isLoaded){
            SignTypes.initialize();
            Executor.register(this);
            Console.info("<green>Plugin {} {} enabled! ({} ms)", PLUGIN_NAME, VERSION, System.currentTimeMillis() - time);
        }
        else Console.warn("Plugin {} {} is not enabled! There was an error in the console above!", PLUGIN_NAME, VERSION);
    }

    public void onDisable() {
        CommandAPI.onDisable();
        HandlerList.unregisterAll(this);
        Console.info("<red>Plugin {} {} disabled!", PLUGIN_NAME, VERSION);
    }

    public void onReload() {
        long time = System.currentTimeMillis();

        Executor.pre_register(this);
        isLoaded = MessagesLoader.initialize()
                && ConfigurationLoader.initialize();
        HandlerList.unregisterAll(this);
        initializeListeners();
        if (isLoaded){
            SignTypes.initialize();
            Executor.register(this);
            Console.info("<green>Plugin {} {} reloaded! ({} ms)", PLUGIN_NAME, VERSION, System.currentTimeMillis() - time);
        }
        else Console.warn("Plugin {} {} is not reloaded! There was an error in the console above!", PLUGIN_NAME, VERSION);
    }

    private void initializeListeners(){
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getServer().getPluginManager().registerEvents(new BreakListener(), this);
        getServer().getPluginManager().registerEvents(new ExplodeListener(), this);
        getServer().getPluginManager().registerEvents(new GrowListener(), this);
    }

    public static InteractiveSigns getInstance(){
        return INSTANCE;
    }
    public static String getPluginName(){
        return INSTANCE.PLUGIN_NAME;
    }
    public static String getVersion(){
        return INSTANCE.VERSION;
    }
    public static String getAuthor(){
        return INSTANCE.AUTHOR;
    }

    public enum Plugins {
        CoreProtect("CoreProtect", plugin -> {
            Plugin javaPlugin = INSTANCE.getServer().getPluginManager().getPlugin("CoreProtect");
            if (!(javaPlugin instanceof CoreProtect coreProtect))
                return false;
            Console.info("Found CoreProtect plugin!");

            CoreProtectAPI api = coreProtect.getAPI();
            if (!api.isEnabled()) {
                Console.error("CoreProtect plugin API is not enabled!"); return false;}

            if (api.APIVersion() < 10) {
                Console.error("CoreProtect plugin unsupported version v%s (needed v22.4+)!".formatted(coreProtect.getDescription().getVersion()));return false;}

            Console.info("Full support for CoreProtect plugin!");
            return true;
        }, () -> {}),
        PlaceholderAPI("PlaceholderAPI", plugin -> {

            if (InteractiveSigns.getInstance().getServer().getPluginManager().getPlugin(plugin.name) == null)
                return false;

            Console.info("Found and connected to PlaceholderAPI plugin!");
            return true;
        }, () -> {});

        static void initialize(){
            for(Plugins plugin : Plugins.values()){
                plugin.enabled = plugin.enableOperation.isEnable(plugin);
                if (plugin.isEnabled())
                    plugin.initializationOperation.run();
            }

        }

        private final String name;
        private final EnableOperation enableOperation;
        private final InitializationOperation initializationOperation;

        private boolean enabled;
        Plugins(String name, EnableOperation enableOperation, InitializationOperation initializationOperation){
            this.name = name;
            this.enableOperation = enableOperation;
            this.initializationOperation = initializationOperation;
        }

        public String getName(){
            return name;
        }
        public boolean isEnabled(){
            return enabled;
        }

        private interface InitializationOperation {
            void run();
        }
        private interface EnableOperation {
            boolean isEnable(@NotNull Plugins plugin);
        }
    }

}
