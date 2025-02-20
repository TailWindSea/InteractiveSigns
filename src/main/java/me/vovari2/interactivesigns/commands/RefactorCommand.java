package me.vovari2.interactivesigns.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import me.vovari2.interactivesigns.Permission;
import me.vovari2.interactivesigns.utils.ItemDisplayUtils;
import me.vovari2.interactivesigns.utils.NamespacedKeyUtils;
import me.vovari2.interactivesigns.utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RefactorCommand {
    public static String PERMISSION = "interactive_signs.refactor";

    public static void executesPlayer(Player player, CommandArguments arguments){
        int amount = 0;
        for (ItemDisplay display : ItemDisplayUtils.getItemDisplaysOnSignOld(player.getLocation(), 10)){
            PersistentDataContainer container = display.getPersistentDataContainer();
            String side = container.get(NamespacedKeyUtils.forItemOnSignOld(), PersistentDataType.STRING);
            if (side == null){
                player.sendMessage(TextUtils.toComponent("<red>Problem with the sign refactor (%s)!".formatted(TextUtils.toString(display.getLocation()))));
                continue;
            }
            container.remove(NamespacedKeyUtils.forItemOnSignOld());
            container.set(NamespacedKeyUtils.forItemOnSign(side), PersistentDataType.BOOLEAN, false);
            amount++;
        }

        if (amount == 0)
            player.sendMessage(TextUtils.toComponent("<green>The old signs are not found!"));
        else player.sendMessage(TextUtils.toComponent("<green>The nearest signs (within a 10 block radius) have been refactored! (%s signs)".formatted(String.valueOf(amount))));
    }
    public static boolean hasPermission(CommandSender sender){
        return Permission.hasPermission(sender, PERMISSION);
    }
}
