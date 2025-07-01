package me.vovari2.interactivesigns.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import me.vovari2.interactivesigns.messages.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand {
    public static void executesPlayer(Player player, CommandArguments ignored){
        Messages.COMMAND_HELP.send(player);
    }
    public static void executes(CommandSender sender, CommandArguments ignored){
        Messages.COMMAND_HELP.send(sender);
    }
}
