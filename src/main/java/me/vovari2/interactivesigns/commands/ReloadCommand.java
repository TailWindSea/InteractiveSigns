package me.vovari2.interactivesigns.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import me.vovari2.interactivesigns.InteractiveSigns;
import me.vovari2.interactivesigns.messages.Messages;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
    public static void executes(CommandSender sender, CommandArguments ignored){
        InteractiveSigns.getInstance().onReload();
        Messages.COMMAND_RELOAD.send(sender);
    }
}
