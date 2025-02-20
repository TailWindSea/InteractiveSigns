package me.vovari2.interactivesigns;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
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
import me.vovari2.interactivesigns.utils.HuskClaimsUtils;
import me.vovari2.interactivesigns.utils.WorldGuardUtils;
import net.kyori.adventure.key.Key;
import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.api.HuskClaimsAPI;
import net.william278.huskclaims.claim.Claim;
import net.william278.huskclaims.libraries.cloplib.operation.OperationType;
import net.william278.huskclaims.trust.TrustTag;
import net.william278.huskclaims.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProtectionPlugins {
    private static List<ProtectionPlugin> plugins;

    public static void load(){
        plugins = new LinkedList<>();
        addPlugin(new WorldGuardProtectionPlugin());
        addPlugin(new GriefPreventionProtectionPlugin());
        addPlugin(new HuskClaimsProtectionPlugin());
        addPlugin(new SuperiorSkyblock2ProtectionPlugin());
        addPlugin(new LandsProtectionPlugin());
        addPlugin(new ChestProtectProtectionPlugin());
        addPlugin(new ResidenceProtectionPlugin());
    }
    public static void addPlugin(ProtectionPlugin plugin){
        if (!plugin.enabled())
            return;
        plugins.add(plugin);
        Text.sendMessageToConsole("<green>Found %s plugin, added support for it".formatted(plugin.name));
    }
    public static boolean canInteractWithSign(Player player, Location location){
        boolean canInteract = true;
        for (ProtectionPlugin plugin : plugins)
            if (!plugin.canInteractWithSign(player, location))
                canInteract = false;
        return canInteract;
    }



    abstract static public class ProtectionPlugin{
        private final String name;
        ProtectionPlugin(String name){
            this.name = name;
        }
        public boolean enabled(){
            return InteractiveSigns.getInstance().getServer().getPluginManager().getPlugin(name) != null;
        }
        public abstract boolean canInteractWithSign(Player player, Location block);
    }
    static class WorldGuardProtectionPlugin extends ProtectionPlugin{
        WorldGuardProtectionPlugin(){
            super("WorldGuard");
        }
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            World world = WorldGuardUtils.adaptWorld(location.getWorld());
            if (!WorldGuardPlugin.inst().getConfigManager().get(world).useRegions)
                return true;

            RegionManager container = InteractiveSigns.getRegionContainer().get(world);
            if (container == null)
                return true;

            for (ProtectedRegion region : container.getApplicableRegions(WorldGuardUtils.adaptLocation(location)).getRegions())
                if(!region.isMember(WorldGuardUtils.adaptPlayer(player)) && !region.getType().equals(RegionType.GLOBAL) && !player.isOp())
                    return false;

            return true;
        }
    }
    static class GriefPreventionProtectionPlugin extends ProtectionPlugin{
        GriefPreventionProtectionPlugin(){
            super("GriefPrevention");
        }
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            return GriefPrevention.instance.allowBuild(player, location) == null;
        }
    }
    static class HuskClaimsProtectionPlugin extends ProtectionPlugin{
        private final Key ITEMS_ON_SIGNS;
        HuskClaimsProtectionPlugin(){
            super("HuskClaims");
            ITEMS_ON_SIGNS = Key.key(Config.HUSKCLAIMS_FLAG_ID);
            BukkitHuskClaimsAPI.getInstance().registerTrustTag(new ItemOnSignsFlag(Config.HUSKCLAIMS_FLAG_ID, ""));
        }
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            Claim claim;
            try {
                claim = BukkitHuskClaimsAPI.getInstance().getClaimAt(HuskClaimsUtils.adaptPosition(location)).orElseThrow();
                for(OperationType operation : claim.getUserTrustLevel(HuskClaimsUtils.adaptPlayer(player), HuskClaimsAPI.getInstance().getPlugin()).orElseThrow().getFlags()){
                    if (ITEMS_ON_SIGNS.equals(operation.getKey()))
                        return true;
                }
                Text.sendMessageToConsole(OperationType.BLOCK_BREAK.getKey().value());
            } catch(NoSuchElementException e){ return false; }
            return false;
        }
        private class ItemOnSignsFlag extends TrustTag {
            public ItemOnSignsFlag(@NotNull String name, @NotNull String description) {
                super(name, description);
            }

            @Override
            public boolean includes(@NotNull User user) {
                return false;
            }
        }
    }
    static class SuperiorSkyblock2ProtectionPlugin extends ProtectionPlugin{
        SuperiorSkyblock2ProtectionPlugin(){
            super("SuperiorSkyblock2");
        }
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            return SuperiorSkyblockAPI.getIslandAt(location).isMember(SuperiorSkyblockAPI.getPlayer(player));
        }
    }
    static class LandsProtectionPlugin extends ProtectionPlugin{
        private LandsIntegration instance;
        private RoleFlag flag;
        LandsProtectionPlugin(){
            super("Lands");
            if (!enabled())
                return;

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
    static class ChestProtectProtectionPlugin extends ProtectionPlugin{
        ChestProtectAPI instance;
        ChestProtectProtectionPlugin(){
            super("ChestProtect");
            if (!enabled())
                return;

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
    static class ResidenceProtectionPlugin extends ProtectionPlugin{
        ResidenceProtectionPlugin(){
            super("Residence");
        }
        @Override
        public boolean canInteractWithSign(Player player, Location location) {
            ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(location);
            if (res == null)
                return true;

            return res.getPermissions().playerHas(player, Flags.container, true);
        }
    }
}
