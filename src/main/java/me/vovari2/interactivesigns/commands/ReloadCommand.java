package me.vovari2.interactivesigns.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import me.vovari2.interactivesigns.InteractiveSigns;
import me.vovari2.interactivesigns.Permission;
import me.vovari2.interactivesigns.utils.TextUtils;
import org.bukkit.command.CommandSender;

public class ReloadCommand {

    public static String PERMISSION = "interactive_signs.reload";

    public static void executes(CommandSender sender, CommandArguments ignored){
        InteractiveSigns.getInstance().onDisable();
        InteractiveSigns.getInstance().onLoad();
        InteractiveSigns.getInstance().onEnable();
        sender.sendMessage(TextUtils.toComponent("<gradient:#54B435:#82CD47>The plugin has been reloaded!"));
    }
    public static boolean hasPermission(CommandSender sender){
        return Permission.hasPermission(sender, PERMISSION);
    }
}
