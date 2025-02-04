package me.vovari2.interactivesigns;

import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionType;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.vovari2.interactivesigns.utils.HuskClaimsUtils;
import me.vovari2.interactivesigns.utils.WorldGuardUtils;
import net.kyori.adventure.key.Key;
import net.william278.huskclaims.HuskClaims;
import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.api.HuskClaimsAPI;
import net.william278.huskclaims.claim.Claim;
import net.william278.huskclaims.libraries.cloplib.operation.OperationType;
import net.william278.huskclaims.trust.TrustLevel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProtectionPlugins {
    private static List<ProtectionPlugin> plugins;

    public static void initialize(){
        plugins = new LinkedList<>();
        plugins.add(new WorldGuardProtectionPlugin());
        plugins.add(new GriefPreventionProtectionPlugin());
        plugins.add(new HuskClaimsProtectionPlugin());
    }
    public static boolean canInteractWithSign(Player player, Location block){
        for (ProtectionPlugin plugin : plugins)
            if (plugin.is() && plugin.canInteractWithSign(player, block))
                return true;
        return false;
    }



    abstract static class ProtectionPlugin{
        private final boolean enabled;
        ProtectionPlugin(boolean enabled){
            this.enabled = enabled;
        }
        public boolean is(){
            return enabled;
        }
        public abstract boolean canInteractWithSign(Player player, Location block);
    }

    static class WorldGuardProtectionPlugin extends ProtectionPlugin{
        WorldGuardProtectionPlugin(){
            super(InteractiveSigns.getInstance().getServer().getPluginManager().isPluginEnabled("WorldGuard"));
        }
        @Override
        public boolean canInteractWithSign(Player player, Location block) {
            World world = WorldGuardUtils.adaptWorld(block.getWorld());
            if (!WorldGuardPlugin.inst().getConfigManager().get(world).useRegions)
                return true;

            RegionManager container = InteractiveSigns.getRegionContainer().get(world);
            if (container == null)
                return true;

            for (ProtectedRegion region : container.getApplicableRegions(WorldGuardUtils.adaptLocation(block)).getRegions())
                if(!region.isMember(WorldGuardUtils.adaptPlayer(player)) && !region.getType().equals(RegionType.GLOBAL) && !player.isOp())
                    return false;

            return true;
        }
    }

    static class GriefPreventionProtectionPlugin extends ProtectionPlugin{
        GriefPreventionProtectionPlugin(){
            super(InteractiveSigns.getInstance().getServer().getPluginManager().isPluginEnabled("GriefPrevention"));
        }
        @Override
        public boolean canInteractWithSign(Player player, Location block) {
            return GriefPrevention.instance.allowBuild(player, block) == null;
        }
    }
    static class HuskClaimsProtectionPlugin extends ProtectionPlugin{
        HuskClaimsProtectionPlugin(){
            super(InteractiveSigns.getInstance().getServer().getPluginManager().isPluginEnabled("HuskClaims"));
        }
        @Override
        public boolean canInteractWithSign(Player player, Location block) {
            Claim claim;
            try {
                claim = BukkitHuskClaimsAPI.getInstance().getClaimAt(HuskClaimsUtils.adaptPosition(block)).orElseThrow();
                for(OperationType operation : claim.getUserTrustLevel(HuskClaimsUtils.adaptPlayer(player), HuskClaimsAPI.getInstance().getPlugin()).orElseThrow().getFlags()){
                    if (HuskClaimsUtils.ITEMS_IN_SIGNS_PUT.equals(operation.getKey()))
                        return true;
                }
            } catch(NoSuchElementException e){ return false; }
            return false;
        }
    }
}
