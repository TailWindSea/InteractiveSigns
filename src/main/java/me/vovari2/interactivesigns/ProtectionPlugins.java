package me.vovari2.interactivesigns;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.griefdefender.api.Core;
import com.griefdefender.api.GriefDefender;
import com.griefdefender.api.claim.TrustTypes;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionType;
import me.angeschossen.chestprotect.api.ChestProtectAPI;
import me.angeschossen.chestprotect.api.protection.block.BlockProtection;
import me.angeschossen.chestprotect.api.protection.world.ProtectionWorld;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.enums.FlagTarget;
import me.angeschossen.lands.api.flags.enums.RoleFlagCategory;
import me.angeschossen.lands.api.flags.type.RoleFlag;
import me.angeschossen.lands.api.land.LandWorld;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.api.HuskClaimsAPI;
import net.william278.huskclaims.claim.Claim;
import net.william278.huskclaims.libraries.cloplib.operation.OperationType;
import net.william278.huskclaims.position.Position;
import net.william278.huskclaims.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProtectionPlugins {
    private static List<ProtectionPlugin> plugins;

    public static void load(){
        plugins = new LinkedList<>();
        addPlugin(WorldGuardProtectionPlugin.class, "WorldGuard");
        addPlugin(LandsProtectionPlugin.class, "Lands");
        addPlugin(ResidenceProtectionPlugin.class, "Residence");
        addPlugin(GriefPreventionProtectionPlugin.class, "GriefPrevention");
        addPlugin(SuperiorSkyblock2ProtectionPlugin.class, "SuperiorSkyblock2");
        addPlugin(ChestProtectProtectionPlugin.class, "ChestProtect");
        addPlugin(GriefDefenderProtectionPlugin.class, "GriefDefender");
    }
    public static void initialize(){
        addPlugin(HuskClaimsProtectionPlugin.class, "HuskClaims");
    }
    private static void addPlugin(Class<? extends ProtectionPlugin> plugin, String name){
        if (!isEnabledPlugin(name))
            return;

        boolean isFullEnabled = true;
        try{ plugins.add(plugin.getDeclaredConstructor().newInstance());}
        catch(Exception ignored){isFullEnabled = false;}

        if (isFullEnabled)
            Text.info("Found %s plugin! Plugin support is fully enabled!".formatted(name));
        else Text.warning("Found %s plugin! Plugin support is not enabled due to a bug!".formatted(name));
    }
    public static boolean isEnabledPlugin(String name){
        return InteractiveSigns.getInstance().getServer().getPluginManager().getPlugin(name) != null;
    }
    public static boolean canInteractWithSign(Player player, Location location){
        boolean canInteract = true;
        for (ProtectionPlugin plugin : plugins)
            if (!plugin.canInteractWithSign(player, location))
                canInteract = false;
        return canInteract;
    }


    private abstract static class ProtectionPlugin{
        public abstract boolean canInteractWithSign(Player player, Location block);
    }
    private static class WorldGuardProtectionPlugin extends ProtectionPlugin{
        private final StateFlag USES_ITEMS_ON_SIGNS;
        WorldGuardProtectionPlugin(){
            FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
            Flag<?> flag = registry.get("uses-items-on-signs");
            if (flag == null){
                USES_ITEMS_ON_SIGNS = new StateFlag("uses-items-on-signs", false);
                registry.register(USES_ITEMS_ON_SIGNS);
            }
            else USES_ITEMS_ON_SIGNS = (StateFlag) flag;
        }

        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            World world = BukkitAdapter.adapt(location.getWorld());
            if (!WorldGuardPlugin.inst().getConfigManager().get(world).useRegions)
                return true;

            RegionManager container = WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
            if (container == null)
                return true;

            BlockVector3 blockVector3 = BukkitAdapter.asBlockVector(location);
            LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
            for (ProtectedRegion region : container.getApplicableRegions(blockVector3).getRegions())
                if(!region.isMember(localPlayer) && !StateFlag.State.ALLOW.equals(region.getFlag(USES_ITEMS_ON_SIGNS)) && !region.getType().equals(RegionType.GLOBAL) && !player.isOp())
                    return false;

            return true;
        }
    }
    private static class LandsProtectionPlugin extends ProtectionPlugin{
        private final LandsIntegration instance;
        private final RoleFlag flag;
        LandsProtectionPlugin(){
            instance = LandsIntegration.of(InteractiveSigns.getInstance());
            flag = RoleFlag.of(instance, FlagTarget.PLAYER, RoleFlagCategory.ACTION, Config.LANDS_FLAG_ID);
            flag.setDisplayName(Config.LANDS_FLAG_NAME).setIcon(new ItemStack(Config.LANDS_FLAG_MATERIAL)).setDescription(Config.LANDS_FLAG_DESCRIPTION);
        }
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            LandWorld world = instance.getWorld(location.getWorld());
            if (world == null)
                return true;
            return world.hasRoleFlag(player.getUniqueId(), location, flag);
        }
    }
    private static class ResidenceProtectionPlugin extends ProtectionPlugin{
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(location);
            if (res == null)
                return true;

            return res.getPermissions().playerHas(player, Flags.container, true);
        }
    }
    private static class HuskClaimsProtectionPlugin extends ProtectionPlugin{
        private final String ITEMS_ON_SIGNS;
        HuskClaimsProtectionPlugin(){
            ITEMS_ON_SIGNS = Config.HUSKCLAIMS_FLAG_ID;
        }
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            Claim claim;
            try{
                Position position = BukkitHuskClaimsAPI.getInstance().getPosition(location);
                claim = BukkitHuskClaimsAPI.getInstance().getClaimAt(position).orElseThrow();
                try{
                    User user = BukkitHuskClaimsAPI.getInstance().getOnlineUser(player);
                    for(OperationType operation : claim.getUserTrustLevel(user, HuskClaimsAPI.getInstance().getPlugin()).orElseThrow().getFlags())
                        if (ITEMS_ON_SIGNS.equals(operation.getKey().value()))
                            return true;
                }
                catch(NoSuchElementException e){ return false; }
            }
            catch (NoSuchElementException e){return true;}
            return false;
        }
    }
    private static class GriefPreventionProtectionPlugin extends ProtectionPlugin{
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            return GriefPrevention.instance.allowBuild(player, location) == null;
        }
    }
    private static class SuperiorSkyblock2ProtectionPlugin extends ProtectionPlugin{
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            org.bukkit.World world = location.getWorld();
            if (world == null)
                return false;

            if (!world.getName().equals("superiorworld"))
                return true;

            location.getWorld().getName();


            Island island = SuperiorSkyblockAPI.getIslandAt(location);
            if(island == null)
                return false;
            return island.isMember(SuperiorSkyblockAPI.getPlayer(player));
        }
    }
    private static class ChestProtectProtectionPlugin extends ProtectionPlugin{
        private final ChestProtectAPI instance;
        ChestProtectProtectionPlugin(){
            instance = ChestProtectAPI.getInstance();
        }
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            ProtectionWorld world = instance.getProtectionWorld(location.getWorld());
            if (world == null)
                return true;

            BlockProtection block = world.getBlockProtection(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            if (block == null)
                return true;

            return block.getTrusted().contains(player.getUniqueId());
        }
    }
    private static class GriefDefenderProtectionPlugin extends ProtectionPlugin{
        private final Core instance;
        GriefDefenderProtectionPlugin(){
            instance = GriefDefender.getCore();
        }
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            com.griefdefender.api.claim.Claim claim = instance.getClaimAt(location);
            if (claim == null)
                return false;

            if (claim.isWilderness())
                return false;

            return claim.canUseBlock(null, location, instance.getUser(player.getUniqueId()), TrustTypes.BUILDER);
        }
    }
}
