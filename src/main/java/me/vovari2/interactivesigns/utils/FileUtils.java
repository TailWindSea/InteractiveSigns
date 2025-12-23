package me.vovari2.interactivesigns.utils;

import me.vovari2.interactivesigns.Console;
import me.vovari2.interactivesigns.InteractiveSigns;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static @NotNull Path getPluginDirectory(){
        return InteractiveSigns.getInstance().getDataPath();
    }

    public static void createDirectory(@NotNull Path directory){
        if (directory.getParent() != null && !Files.exists(directory.getParent()))
            createDirectory(directory.getParent());

        if (Files.exists(directory))
            return;

        try{
            Files.createDirectory(directory);
        }
        catch(IOException e){
            Console.warn(("The directory '%s' cannot be created!").formatted(directory.getFileName()));
        }

    }
    public static @NotNull Path createDirectory(@NotNull Path parent, @NotNull String name){
        createDirectory(parent);

        Path directory = Paths.get(parent.toString(), name);
        if (Files.exists(directory))
            return directory;

        try {
            Files.createDirectory(directory);
        }
        catch(IOException e){
            Console.warn("The directory '%s' cannot be created!".formatted(directory.getFileName()));
        }
        return directory;
    }

    public static void createFile(@NotNull Path file){
        if (file.getParent() != null && !Files.exists(file.getParent()))
            createDirectory(file.getParent());

        if(Files.exists(file))
            return;

        try {
            Files.createFile(file);
        }
        catch(IOException e){
            Console.warn("The file '%s' cannot be created!".formatted(file.getFileName()));
        }
    }
    public static @NotNull Path createFile(@NotNull Path directory, @NotNull String name){
        createDirectory(directory);

        Path file = Path.of(directory.toString(), name);
        if (Files.exists(file))
            return file;

        try {
            Files.createFile(file);
        }
        catch(IOException e){
            Console.warn("The file '%s' cannot be created in the directory '%s'!".formatted(file.getFileName(), directory.getFileName()));
        }
        return file;
    }

    public static void saveFile(Path file, YamlConfiguration configuration){
        createDirectory(getPluginDirectory());

        if (!Files.exists(file))
            createFile(file);

        try{
            configuration.save(file.toFile());
        }
        catch(IOException e){
            Console.warn("The configuration file '%s' could not be saved!".formatted(file.getFileName()));
        }
    }

    public static @NotNull YamlConfiguration loadResource(String resource){
        Path file = Path.of(getPluginDirectory().toString(), resource);
        YamlConfiguration configuration = new YamlConfiguration();
        try{
            configuration.load(file.toFile());
            return configuration;
        }
        catch(IOException | InvalidConfigurationException e){
            Console.warn("The error when loading resource '%s': %s".formatted(resource, e.getMessage()));
        }
        return configuration;
    }
    public static void saveResource(String resource){
        createDirectory(getPluginDirectory());

        Path file = Path.of(getPluginDirectory().toString(), resource);
        if (!Files.exists(file)){
            createFile(file);

            URL url = InteractiveSigns.class.getClassLoader().getResource(resource);
            if (url == null){
                Console.warn("The file '%s' cannot be read!".formatted(resource));
                return;
            }

            URLConnection connection;
            try{
                connection = url.openConnection();
                connection.setUseCaches(false);
            } catch (IOException e) {
                Console.warn("The file '%s' cannot be read!".formatted(resource));
                return;
            }

            try(InputStream in = connection.getInputStream(); OutputStream out = new FileOutputStream(file.toFile())){
                byte[] buf = new byte[1024];

                int len;
                while((len = in.read(buf)) > 0)
                    out.write(buf, 0, len);
            }
            catch(IOException e){
                Console.warn("The file '%s' cannot be written!".formatted(resource));
            }
        }
    }
}
