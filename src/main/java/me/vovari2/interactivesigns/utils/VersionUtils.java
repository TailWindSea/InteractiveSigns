package me.vovari2.interactivesigns.utils;

import me.vovari2.interactivesigns.InteractiveSigns;
import org.bukkit.Location;

public class VersionUtils {
    public static Location getBlockCenter(Location blockLocation){
        String currentVersion = InteractiveSigns.getInstance().getServer().getMinecraftVersion();
        return currentVersion.matches("|^1\\.21(|\\.1|2)$")
                ? blockLocation : blockLocation.clone().add(0.5,0.5,0.5);
    }
}
