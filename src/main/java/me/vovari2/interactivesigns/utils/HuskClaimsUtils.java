package me.vovari2.interactivesigns.utils;

import net.william278.huskclaims.api.BukkitHuskClaimsAPI;
import net.william278.huskclaims.position.Position;
import net.william278.huskclaims.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HuskClaimsUtils {
    public static User adaptPlayer(Player player){
        return BukkitHuskClaimsAPI.getInstance().getOnlineUser(player);
    }
    public static Position adaptPosition(Location block){
        return BukkitHuskClaimsAPI.getInstance().getPosition(block);
    }
}
