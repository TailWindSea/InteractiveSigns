package me.vovari2.interactivesigns.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import me.vovari2.interactivesigns.InteractiveSigns;
import me.vovari2.interactivesigns.Permission;
import me.vovari2.interactivesigns.Text;
import org.bukkit.command.CommandSender;

public class ReloadCommand {

    public static String PERMISSION = "interactive_signs.reload";

    private static void execute(CommandSender sender){
        InteractiveSigns.getInstance().onDisable();
        InteractiveSigns.getInstance().onLoad();
        InteractiveSigns.getInstance().onEnable();
        sender.sendMessage(Text.value("command.reload"));
    }
    public static boolean hasPermission(CommandSender sender){
        return Permission.hasPermission(sender, PERMISSION);
    }
    public static void preExecute(CommandSender sender, CommandArguments arguments){
        execute(sender);
    }
}
