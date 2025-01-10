package me.vovari2.interactivesigns.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import me.vovari2.interactivesigns.Permission;
import me.vovari2.interactivesigns.Text;
import org.bukkit.command.CommandSender;

public class HelpCommand {

    public static String PERMISSION = "interactive_signs.help";

    private static void execute(CommandSender sender){
        sender.sendMessage(Text.value("command.help"));
    }

    public static boolean hasPermission(CommandSender sender){
        return Permission.hasPermission(sender, PERMISSION);
    }
    public static void preExecute(CommandSender sender, CommandArguments arguments){
        execute(sender);
    }
}
