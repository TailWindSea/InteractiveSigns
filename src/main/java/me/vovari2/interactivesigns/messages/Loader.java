package me.vovari2.interactivesigns.messages;

import me.vovari2.interactivesigns.messages.types.ChatMessage;
import me.vovari2.interactivesigns.utils.FileUtils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Loader {
    private static final String RESOURCE_NAME = "messages.yml";
    private static final String OLD_RESOURCE_NAME = "text.yml";

    private final String fileName;
    private final YamlConfiguration configuration;
    Loader() throws Exception {
        this.fileName = RESOURCE_NAME;
        Path file = Path.of(FileUtils.getPluginDirectory().toString(), RESOURCE_NAME);

        Path oldFile = Path.of(FileUtils.getPluginDirectory().toString(), OLD_RESOURCE_NAME);
        if (Files.exists(oldFile))
            Files.move(oldFile, file, StandardCopyOption.ATOMIC_MOVE);

        if (!Files.exists(file)){
            configuration = new YamlConfiguration();
            for (Messages message : Messages.values())
                configuration.set(message.name().replaceAll("_","-").toLowerCase(), message.string());

            FileUtils.saveFile(file, configuration);
        }
        else {
            configuration = FileUtils.loadResource(RESOURCE_NAME);
            for (Messages object : Messages.values()){
                String path = object.name().replaceAll("_", "-").toLowerCase();
                ConfigurationSection section = configuration.getConfigurationSection(path);
                if (section == null){
                    object.set(new ChatMessage(getStringFromSection(path), null)); continue;}

                String message = getStringFromSection("%s.message".formatted(path));
                Sound sound = getSound("%s.sound".formatted(path));
                object.set(new ChatMessage(message, sound));
            }
        }
    }

    private @NotNull String getString(@NotNull String path) throws Exception{
        String value = configuration.getString(path);
        if (value == null)
            throw new Exception("Value '%s' in the '%s' must not be empty!".formatted(path, fileName));
        return value;
    }

    private @NotNull Sound getSound(@NotNull String path) throws Exception {
        return Sound.sound(
                Key.key(getString("%s.key".formatted(path))),
                getSoundSource("%s.source".formatted(path)),
                (float) configuration.getDouble("%s.volume".formatted(path)),
                (float) configuration.getDouble("%s.pitch".formatted(path)));
    }
    private @NotNull Sound.Source getSoundSource(@NotNull String path) throws Exception {
        return toSoundSource(configuration.getString(path), path);
    }
    private @NotNull Sound.Source toSoundSource(@Nullable String value, @NotNull String path) throws Exception {
        if (value == null)
            throw new Exception("Значение '%s' в '%s' не должно быть пустым!".formatted(path, RESOURCE_NAME));
        try { return Sound.Source.valueOf(value.toUpperCase());}
        catch(IllegalArgumentException ignored){ throw new Exception("Значение '%s' в '%s' должно быть источником звука!".formatted(path, RESOURCE_NAME)); }
    }

    private @Nullable String getStringFromSection(@NotNull String path) {
        List<String> messages = configuration.getStringList(path);
        return messages.isEmpty() ? configuration.getString(path) : String.join("<newline>", messages);
    }
}
