package me.vovari2.interactivesigns.utils;

import me.vovari2.interactivesigns.InteractiveSigns;
import net.coreprotect.consumer.Queue;
import org.bukkit.Location;
import org.bukkit.Material;

public class CoreProtectUtils extends Queue{
    public static boolean isCoreProtect(){
        return InteractiveSigns.getCoreProtectAPI() != null;
    }
    public static void logPuttingItemOnSign(String user, Location location, Material material){
        queueSignText(user, location, 1, 0, false, "PUT :::: %s".formatted(material.toString()), "","","", 0);
    }
    public static void logTakingItemOnSign(String user, Location location, Material material){
        queueSignText(user, location, 1, 0, false, "TAKE :::: %s".formatted(material.toString()), "","","", 0);
    }
}
