package me.vovari2.interactivesigns;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.vovari2.interactivesigns.commands.ClearCommand;
import me.vovari2.interactivesigns.commands.HelpCommand;
import me.vovari2.interactivesigns.commands.ReloadCommand;

public class Executor {
    public static void preInitialize(InteractiveSigns instance){
        CommandTree command = new CommandTree("interactivesigns");
        command.setAliases(new String[]{"ins"});
        command.then(new LiteralArgument("reload")
                .withRequirement(ReloadCommand::hasPermission)
                .executes(ReloadCommand::executes));
        command.register(instance);
    }
    public static void initialize(InteractiveSigns instance) {
        CommandAPI.unregister("interactivesigns");

        CommandAPICommand command = new CommandAPICommand("interactivesigns");
        command.setAliases(new String[]{"ins"});
        command.withSubcommand(new CommandAPICommand("help")
                .withRequirement(HelpCommand::hasPermission)
                .executesPlayer(HelpCommand::executesPlayer)
                .executes(HelpCommand::executes));
        command.withSubcommand(new CommandAPICommand("reload")
                .withRequirement(ReloadCommand::hasPermission)
                .executes(ReloadCommand::executes));

        command.withSubcommand(new CommandAPICommand("clear")
                .withRequirement(ClearCommand::hasPermission)
                .executesPlayer(ClearCommand::executesPlayer)
                .withOptionalArguments(new DoubleArgument(ClearCommand.ARGUMENT_RADIUS))
                .executesPlayer(ClearCommand::executesPlayer));

        command.executes(HelpCommand::executes);
        command.register(instance);
    }
}
