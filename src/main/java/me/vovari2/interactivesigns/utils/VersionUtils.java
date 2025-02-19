package me.vovari2.interactivesigns.utils;

import me.vovari2.interactivesigns.InteractiveSigns;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class VersionUtils {
    public static Location getBlockCenter(Location blockLocation){
        return InteractiveSigns.getInstance().getServer().getMinecraftVersion().equals("1.21.4") ? blockLocation.clone().add(0.5, 0.5, 0.5) : blockLocation;
    }
}
