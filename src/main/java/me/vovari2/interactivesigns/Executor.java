package me.vovari2.interactivesigns;

import me.vovari2.interactivesigns.messages.Messages;
import me.vovari2.interactivesigns.utils.NamespacedKeyUtils;
import me.vovari2.interactivesigns.utils.TextUtils;
import org.bukkit.block.sign.Side;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Executor {
    private static final String PERMISSION = "interactive_signs.*";
    public static boolean hasPermission(@NotNull Player player){
        return player.hasPermission(PERMISSION);
    }
    public static void register(InteractiveSigns instance){
        instance.getServer().getCommandMap().register(
                InteractiveSigns.getPluginName(),
                new InteractiveSignsCommand("interactivesings", "", "/ins <args>", List.of("ins"))
        );
    }

    private static class InteractiveSignsCommand extends BukkitCommand {
        public InteractiveSignsCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
            super(name, description, usageMessage, aliases);
        }

        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String @NotNull [] args) {
            if (args.length < 1) {
                sender.sendMessage(TextUtils.toComponent("<red>Invalid command entered!")); return true;}

            if (!sender.hasPermission(PERMISSION) && !sender.isOp()) {
                sender.sendMessage(TextUtils.toComponent("<red>You don't have permission!")); return true;}

            switch (args[0]) {
                case "help" -> Messages.COMMAND_HELP.send(sender);
                case "reload" -> {
                    if (args.length == 1) {
                        Messages.COMMAND_RELOAD.send(sender);
                        return true;
                    }

                    if (args.length == 2 && args[1].equals("confirm")) {
                        InteractiveSigns.getInstance().onReload();
                        Messages.COMMAND_RELOAD_CONFIRM.send(sender);
                        return true;
                    }
                }
                case "clear" -> {
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage(TextUtils.toComponent("<yellow>This command can only be executed by the player!"));
                        return true;
                    }

                    if (args.length > 2) {
                        sender.sendMessage("<yellow>Too many arguments!");
                        return true;
                    }

                    double radius = 1D;
                    if (args.length == 2) {
                        try {
                            radius = Double.parseDouble(args[1]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage(TextUtils.toComponent("<yellow>Invalid format for cleaning radius!"));
                            return true;
                        }
                    }

                    for (ItemDisplay display : player.getWorld().getNearbyEntitiesByType(ItemDisplay.class, player.getLocation(), radius,
                            display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign(Side.FRONT.name()))
                                    || display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign(Side.BACK.name())))) {
                        player.getInventory().addItem(display.getItemStack());
                        display.remove();
                    }
                    Messages.COMMAND_CLEAR.replace("radius", String.valueOf(radius)).send(player);
                }
                default -> sender.sendMessage(TextUtils.toComponent("<red>Invalid command entered!"));
            }
            return true;
        }

        @Override
        public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) throws IllegalArgumentException {
            if (args.length == 1)
                return List.of("help", "reload", "clear");

            if (args.length == 2)
                return switch (args[0]) {
                    case "reload" -> List.of("confirm");
                    case "clear" -> List.of("1", "5", "10");
                    default -> List.of();
                };
            return List.of();
        }
    }
}
