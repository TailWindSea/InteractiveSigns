package me.vovari2.interactivesigns;


public enum Plugins {
    PlaceholderAPI("PlaceholderAPI");

    public static void initialize(){
        for(Plugins plugin : Plugins.values())
            plugin.enabled = InteractiveSigns.getInstance().getServer().getPluginManager().getPlugin(plugin.name) != null;

    }

    private final String name;
    private boolean enabled;
    Plugins(String name){
        this.name = name;
    }

    public boolean isEnabled(){
        return enabled;
    }
}
