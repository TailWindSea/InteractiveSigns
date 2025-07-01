package me.vovari2.interactivesigns;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.DoubleArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.vovari2.interactivesigns.commands.ClearCommand;
import me.vovari2.interactivesigns.commands.HelpCommand;
import me.vovari2.interactivesigns.commands.ReloadCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Executor {
    private static final String PERMISSION = "interactive_signs.*";
    public static boolean hasPermission(@NotNull Player player){
        return player.hasPermission(PERMISSION);
    }

    public static void pre_register(InteractiveSigns instance){
        CommandTree command = new CommandTree("interactivesigns");
        command.setAliases(new String[]{"ins"});
        command.then(new LiteralArgument("reload")
                .withPermission(PERMISSION)
                .executes(ReloadCommand::executes));
        command.register(instance);
    }
    public static void register(InteractiveSigns instance) {
        CommandAPI.unregister("interactivesigns");

        CommandAPICommand command = new CommandAPICommand("interactivesigns");
        command.setAliases(new String[]{"ins"});
        command.withSubcommand(new CommandAPICommand("help")
                .withPermission(PERMISSION)
                .executesPlayer(HelpCommand::executesPlayer)
                .executes(HelpCommand::executes));
        command.withSubcommand(new CommandAPICommand("reload")
                .withPermission(PERMISSION)
                .executes(ReloadCommand::executes));

        command.withSubcommand(new CommandAPICommand("clear")
                .withPermission(PERMISSION)
                .executesPlayer(ClearCommand::executesPlayer)
                .withOptionalArguments(new DoubleArgument(ClearCommand.ARGUMENT_RADIUS))
                .executesPlayer(ClearCommand::executesPlayer));

        command.executes(HelpCommand::executes);
        command.register(instance);
    }
}
