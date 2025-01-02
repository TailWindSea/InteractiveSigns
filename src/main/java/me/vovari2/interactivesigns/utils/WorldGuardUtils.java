package me.vovari2.interactivesigns.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionType;
import me.vovari2.interactivesigns.InteractiveSigns;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardUtils {

    public static boolean canInteractWithSign(Location location, Player player){
        World world = adaptWorld(location.getWorld());
        if (!InteractiveSigns.getWorldConfiguration(world).useRegions)
            return true;

        RegionManager container = InteractiveSigns.getRegionContainer().get(world);
        if (container == null)
            return true;

        for (ProtectedRegion region : container.getApplicableRegions(adaptLocation(location)).getRegions())
            if(!region.isMember(adaptPlayer(player)) && !region.getType().equals(RegionType.GLOBAL) && !player.isOp())
                return false;

        return true;
    }

    private static World adaptWorld(org.bukkit.World world){
        return BukkitAdapter.adapt(world);
    }
    private static BlockVector3 adaptLocation(Location location){
        return BukkitAdapter.asBlockVector(location);
    }
    private static LocalPlayer adaptPlayer(Player player){
        return InteractiveSigns.getWorldGuardPlugin().wrapPlayer(player);
    }
}
