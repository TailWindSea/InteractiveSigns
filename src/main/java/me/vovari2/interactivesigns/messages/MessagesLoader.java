package me.vovari2.interactivesigns.messages;

import me.vovari2.interactivesigns.Console;
import me.vovari2.interactivesigns.loader.Loader;
import me.vovari2.interactivesigns.messages.types.ActionbarMessage;
import me.vovari2.interactivesigns.messages.types.BossbarMessage;
import me.vovari2.interactivesigns.messages.types.ChatMessage;
import me.vovari2.interactivesigns.messages.types.TitleMessage;
import me.vovari2.interactivesigns.utils.FileUtils;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.title.Title;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MessagesLoader extends Loader {
    public static boolean initialize() {
        try { new MessagesLoader("messages.yml"); return true; }
        catch(Exception e){ Console.error("Error loading messages: " + e.getMessage()); return false; }
    }

    private MessagesLoader(@NotNull String fileName) throws Exception {
        File pluginFolder = FileUtils.getPluginFolder(),
                file = new File(pluginFolder, fileName);

        if (!file.exists()){
            configuration = new YamlConfiguration();
            for (Messages message : Messages.values())
                configuration.set(message.name().replaceAll("_","-").toLowerCase(), message.string());

            FileUtils.createFolder(pluginFolder);
            FileUtils.createFileInFolder(pluginFolder, file);
            FileUtils.setContentInYamlFile(pluginFolder, fileName, configuration);
        }
        else {
            configuration = FileUtils.getContentFromYamlFile(pluginFolder, fileName);
            for (Messages object : Messages.values()){
                String path = object.name().replaceAll("_", "-").toLowerCase();
                ConfigurationSection section = configuration.getConfigurationSection(path);
                if (section == null){
                    object.setMessage(new ChatMessage(object, getStringFromSection(path))); continue;}

                String message = getStringFromSection("%s.message".formatted(path));
                MessageType type = getMessageType("%s.type".formatted(path), MessageType.CHAT);

                object.setMessage(switch(type){
                    case CHAT -> new ChatMessage(object, message);
                    case ACTIONBAR -> new ActionbarMessage(object, message);
                    case BOSSBAR -> {
                        BossBar.Color color = getBossbarColor("%s.color".formatted(path), BossBar.Color.YELLOW);
                        BossBar.Overlay overlay = getBossbarOverlay("%s.overlay".formatted(path), BossBar.Overlay.NOTCHED_6);
                        yield new BossbarMessage(object, message, color, overlay, configuration.getInt("%s.stay-time", 100));
                    }
                    case TITLE -> {
                        String subtitle = getString("%s.subtitle".formatted(path), "");
                        Duration fadeIn = getDuration("%s.fade-in".formatted(path), Duration.of(500, ChronoUnit.MILLIS)),
                                stay = getDuration("%s.stay".formatted(path), Duration.of(3500, ChronoUnit.MILLIS)),
                                fadeOut = getDuration("%s.fade-out".formatted(path), Duration.of(1000, ChronoUnit.MILLIS));
                        yield new TitleMessage(object, message, subtitle, Title.Times.times(fadeIn, stay, fadeOut));
                    }
                });
            }
        }
    }


    private @Nullable String getStringFromSection(@NotNull String path) throws Exception {
        List<String> messages = configuration.getStringList(path);
        return messages.isEmpty() ? super.getString(path, null) : String.join("\n", messages);
    }
    private @NotNull MessageType getMessageType(@NotNull String path, @NotNull MessageType def){
        try{ return MessageType.valueOf(getString(path).toUpperCase()); }
        catch(Exception e){ return def; }
    }
    private @NotNull Duration getDuration(@NotNull String path, @NotNull Duration def) throws Exception {
        String strValue = configuration.getString(path);
        if (strValue == null)
            return def;

        if (!strValue.matches("^\\d+[stm]$"))
            throw new Exception("<red>Value '%s' has invalid format!".formatted(path));

        int intValue = Integer.parseInt(strValue.substring(0, strValue.length()-1));
        intValue *= switch (strValue.substring(strValue.length()-1)){
            case "s" -> 1000;
            case "t" -> 50;
            default -> 1;
        };

        return Duration.of(intValue, ChronoUnit.MILLIS);
    }

    private @NotNull BossBar.Color getBossbarColor(@NotNull String path, @NotNull BossBar.Color def){
        try{ return BossBar.Color.valueOf(getString(path).toUpperCase()); }
        catch(Exception e){ return def; }
    }
    private @NotNull BossBar.Overlay getBossbarOverlay(@NotNull String path, @NotNull BossBar.Overlay def){
        try{ return BossBar.Overlay.valueOf(getString(path).toUpperCase()); }
        catch(Exception e){ return def; }
    }
}
