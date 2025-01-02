package me.vovari2.interactivesigns;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.DoubleArgument;
import me.vovari2.interactivesigns.commands.ClearCommand;
import me.vovari2.interactivesigns.commands.HelpCommand;
import me.vovari2.interactivesigns.commands.ReloadCommand;

public class Executor {
    public static void initialize(InteractiveSigns instance) {
        CommandAPICommand command = new CommandAPICommand("interactivesigns");
        command.setAliases(new String[]{"is"});
        command.withSubcommand(new CommandAPICommand("help")
                .withRequirement(HelpCommand::hasPermission)
                .executes(HelpCommand::preExecute));
        command.withSubcommand(new CommandAPICommand("reload")
                .withRequirement(ReloadCommand::hasPermission)
                .executes(ReloadCommand::preExecute));

        command.withSubcommand(new CommandAPICommand("clear")
                .withRequirement(ClearCommand::hasPermission)
                .executesPlayer(ClearCommand::preExecute)
                .withArguments(new DoubleArgument(ClearCommand.ARGUMENT_RADIUS))
                .executesPlayer(ClearCommand::preExecute));
        command.executes(HelpCommand::preExecute);
        command.register(instance);
    }
}
