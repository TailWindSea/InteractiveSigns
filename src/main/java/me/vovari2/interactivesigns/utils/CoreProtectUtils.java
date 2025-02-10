package me.vovari2.interactivesigns.utils;

import me.vovari2.interactivesigns.InteractiveSigns;
import me.vovari2.interactivesigns.Text;
import net.coreprotect.CoreProtect;
import net.coreprotect.listener.player.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class CoreProtectUtils {
    public static boolean isCoreProtect(){
        return InteractiveSigns.getCoreProtectAPI() != null;
    }
    public static void logPuttingAnItem(Player player, Location location, ItemStack itemStack){
        InteractiveSigns.getCoreProtectAPI().logInteraction(player.getName(), location);
    }
    public static void logTakingAnItem(Player player, Location location, ItemStack itemStack){

    }
}
