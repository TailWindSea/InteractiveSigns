package me.vovari2.interactivesigns.utils;

import net.kyori.adventure.key.Key;
import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.position.Position;
import net.william278.huskclaims.position.World;
import net.william278.huskclaims.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HuskClaimsUtils {
    public static Key ITEMS_IN_SIGNS_PUT = Key.key("items_in_signs_put");

    public static World adaptWorld(org.bukkit.World world){
        return BukkitHuskClaimsAPI.getInstance().getWorld(world);
    }
    public static User adaptPlayer(Player player){
        return BukkitHuskClaimsAPI.getInstance().getOnlineUser(player);
    }
    public static Position adaptPosition(Location block){
        return BukkitHuskClaimsAPI.getInstance().getPosition(block);
    }
}
