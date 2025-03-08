package me.vovari2.interactivesigns.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import me.vovari2.interactivesigns.Permission;
import me.vovari2.interactivesigns.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand {

    public static String PERMISSION = "interactive_signs.help";

    public static void executesPlayer(Player player, CommandArguments arguments){
        player.sendMessage(Text.node("command.help").replacePlaceholderAPI(player).value());
    }
    public static void executes(CommandSender sender, CommandArguments arguments){
        sender.sendMessage(Text.value("command.help"));
    }

    public static boolean hasPermission(CommandSender sender){
        return Permission.hasPermission(sender, PERMISSION);
    }
}
