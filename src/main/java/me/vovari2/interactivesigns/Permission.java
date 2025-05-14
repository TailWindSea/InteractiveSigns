package me.vovari2.interactivesigns;

import me.vovari2.interactivesigns.commands.ClearCommand;
import me.vovari2.interactivesigns.commands.HelpCommand;
import me.vovari2.interactivesigns.commands.ReloadCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Permission {
    private static Permission imp;
    public static void initialize(){
        imp = new Permission();
        imp.put("interactive_signs.*", List.of(
                HelpCommand.PERMISSION,
                ReloadCommand.PERMISSION,
                ClearCommand.PERMISSION));
        imp.put(HelpCommand.PERMISSION);
        imp.put(ReloadCommand.PERMISSION);
        imp.put(ClearCommand.PERMISSION);
    }
    public static boolean hasPermission(CommandSender sender, String permission){
        if (sender == null)
            return false;

        if (!imp.permissions.containsKey(permission))
            return sender.hasPermission(permission);
        return imp.permissions.get(permission).hasPermission(sender);
    }

    private final HashMap<String, PermissionNode> permissions;
    private Permission(){
        permissions = new HashMap<>();
    }
    private void put(@NotNull String key){
        permissions.put(key, new PermissionNode(key));
    }
    private void put(@NotNull String key, @NotNull List<String> parent){
        imp.put(key);
        for (String targetKey : parent){
            if (permissions.containsKey(targetKey))
                permissions.get(targetKey).addParent(key);
            else permissions.put(targetKey, new PermissionNode(targetKey, List.of(key)));
        }
    }

    public static class PermissionNode{
        private final String key;
        private final List<String> parents;
        PermissionNode(String key) {
            this.key = key;
            this.parents = new ArrayList<>();
        }
        PermissionNode(String key, List<String> parents) {
            this.key = key;
            this.parents = new ArrayList<>();
            this.parents.addAll(parents);
        }

        public void addParent(String key){
            parents.add(key);
        }
        public boolean hasPermission(@NotNull CommandSender player){
            for (String parent : parents)
                if (player.hasPermission(parent))
                    return true;
            return player.hasPermission(key);
        }
    }
}
