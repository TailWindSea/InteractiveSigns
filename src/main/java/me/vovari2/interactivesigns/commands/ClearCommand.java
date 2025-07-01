package me.vovari2.interactivesigns.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import me.vovari2.interactivesigns.messages.Messages;
import me.vovari2.interactivesigns.utils.NamespacedKeyUtils;
import org.bukkit.block.sign.Side;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

public class ClearCommand {
    public static String ARGUMENT_RADIUS = "radius";

    public static void executesPlayer(Player player, CommandArguments arguments){
        double radius = (Double) arguments.getOrDefault(ARGUMENT_RADIUS, 1D);
        for(ItemDisplay display : player.getWorld().getNearbyEntitiesByType(ItemDisplay.class, player.getLocation(), radius,
                display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign(Side.FRONT.name()))
                        || display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign(Side.BACK.name())))){
            if (display.getItemStack() != null)
                player.getInventory().addItem(display.getItemStack());
            display.remove();
        }
        Messages.COMMAND_CLEAR
                .replace("radius", String.valueOf(radius))
                .send(player);
    }
}
