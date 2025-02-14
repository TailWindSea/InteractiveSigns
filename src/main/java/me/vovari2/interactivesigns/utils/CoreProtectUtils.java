package me.vovari2.interactivesigns.utils;

import net.coreprotect.consumer.Queue;
import org.bukkit.Location;
import org.bukkit.Material;

public class CoreProtectUtils extends Queue{
    public static void logPuttingItemOnSign(String user, Location location, Material material){
        queueSignText(user, location, 1, 0, 0, false, false, true, true,"PUT :::: %s".formatted(material.toString()),"","", "", "", "", "", "", 0);
    }
    public static void logTakingItemOnSign(String user, Location location, Material material){
        queueSignText(user, location, 1, 0, 0, false, false, true, true, "TAKE :::: %s".formatted(material.toString()),"","", "", "", "", "", "", 0);
    }
}
