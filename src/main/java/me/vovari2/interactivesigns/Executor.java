package me.vovari2.interactivesigns;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.DoubleArgument;
import me.vovari2.interactivesigns.commands.ClearCommand;
import me.vovari2.interactivesigns.commands.HelpCommand;
import me.vovari2.interactivesigns.commands.ReloadCommand;

public class Executor {
    public static void initialize(InteractiveSigns instance) {
        CommandAPICommand command = new CommandAPICommand("interactivesigns");
        command.setAliases(new String[]{"ins"});
        command.withSubcommand(new CommandAPICommand("help")
                .withRequirement(HelpCommand::hasPermission)
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
