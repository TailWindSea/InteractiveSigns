package me.vovari2.interactivesigns.configuration;

import me.vovari2.interactivesigns.Console;
import org.bukkit.Material;

import java.util.List;

public class Configuration {
    public static boolean ENABLE_ITEMS_VOLUME;

    public static List<Material> BLACKLIST_OF_ITEMS;

    public static boolean PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS;
    public static String PERMISSION_CAN_USE_SIGNS;

    public static class HUSKCLAIMS{
        public static String FLAG_ID;
    }

    public static class LANDS {
        public static String FLAG_ID;
        public static String FLAG_NAME;
        public static Material FLAG_MATERIAL;
        public static String FLAG_DESCRIPTION;
    }

    public static boolean load(){
        try { new Loader(); return true;}
        catch(Exception e){ Console.error("The error when loading configuration: %s".formatted(e.getMessage())); return false;}
    }
}
