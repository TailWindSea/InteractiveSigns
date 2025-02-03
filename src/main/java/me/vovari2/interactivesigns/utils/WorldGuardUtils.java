package me.vovari2.interactivesigns.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionType;
import me.vovari2.interactivesigns.InteractiveSigns;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardUtils {
    public static World adaptWorld(org.bukkit.World world){
        return BukkitAdapter.adapt(world);
    }
    public static BlockVector3 adaptLocation(Location location){
        return BukkitAdapter.asBlockVector(location);
    }
    public static LocalPlayer adaptPlayer(Player player){
        return WorldGuardPlugin.inst().wrapPlayer(player);
    }
}
