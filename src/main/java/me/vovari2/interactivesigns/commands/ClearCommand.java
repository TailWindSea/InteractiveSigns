package me.vovari2.interactivesigns.commands;

import dev.jorel.commandapi.commandsenders.BukkitPlayer;
import dev.jorel.commandapi.executors.ExecutionInfo;
import me.vovari2.interactivesigns.Permission;
import me.vovari2.interactivesigns.utils.NamespacedKeyUtils;
import org.bukkit.block.sign.Side;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class ClearCommand {

    public static String PERMISSION = "interactive_signs.clear";
    public static String ARGUMENT_RADIUS = "radius";

    private static void execute(Player player, double radius){
        for(ItemDisplay display : player.getWorld().getNearbyEntitiesByType(ItemDisplay.class, player.getLocation(), radius, display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign())
                    && (Side.FRONT.name().equals(display.getPersistentDataContainer().get(NamespacedKeyUtils.forItemOnSign(), PersistentDataType.STRING))
                    || Side.BACK.name().equals(display.getPersistentDataContainer().get(NamespacedKeyUtils.forItemOnSign(), PersistentDataType.STRING))))){
            if (display.getItemStack() != null)
                player.getInventory().addItem(display.getItemStack());
            display.remove();

        }
    }

    public static boolean hasPermission(CommandSender sender){
        return Permission.hasPermission(sender, PERMISSION);
    }
    public static void preExecute(ExecutionInfo<Player, BukkitPlayer> info){
        execute(info.sender(), (Double) info.args().getOrDefault(ARGUMENT_RADIUS, 1));
    }
}
