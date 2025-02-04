package me.vovari2.interactivesigns.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import me.vovari2.interactivesigns.Permission;
import me.vovari2.interactivesigns.Text;
import me.vovari2.interactivesigns.utils.NamespacedKeyUtils;
import org.bukkit.block.sign.Side;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class ClearCommand {

    public static String PERMISSION = "interactive_signs.clear";
    public static String ARGUMENT_RADIUS = "radius";

    public static void executesPlayer(Player player, CommandArguments arguments){
        double radius = (Double) arguments.getOrDefault(ARGUMENT_RADIUS, 1D);
        for(ItemDisplay display : player.getWorld().getNearbyEntitiesByType(ItemDisplay.class, player.getLocation(), radius, display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign())
                    && (Side.FRONT.name().equals(display.getPersistentDataContainer().get(NamespacedKeyUtils.forItemOnSign(), PersistentDataType.STRING))
                    || Side.BACK.name().equals(display.getPersistentDataContainer().get(NamespacedKeyUtils.forItemOnSign(), PersistentDataType.STRING))))){
            if (display.getItemStack() != null)
                player.getInventory().addItem(display.getItemStack());
            display.remove();
        }
        player.sendMessage(Text.node("command.clear").replace("<%radius%>", String.valueOf(radius)).value());
    }

    public static boolean hasPermission(CommandSender sender){
        return Permission.hasPermission(sender, PERMISSION);
    }
}
