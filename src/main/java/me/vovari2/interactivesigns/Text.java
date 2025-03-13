package me.vovari2.interactivesigns;

import me.vovari2.interactivesigns.exceptions.ComponentException;
import me.vovari2.interactivesigns.text_nodes.StringTextNode;
import me.vovari2.interactivesigns.utils.FileUtils;
import me.vovari2.interactivesigns.utils.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class Text {
    public static final String PLUGIN_NAME = InteractiveSigns.getInstance().getName();
    public static final String VERSION = InteractiveSigns.getInstance().getPluginMeta().getVersion();
    private static final Component MESSAGE_BEGIN = MiniMessage.miniMessage().deserialize("<gray>[" + PLUGIN_NAME + "]<reset> ");

    protected static void initialize() throws ComponentException {
        new Text().initializeInside();
    }

    private FileConfiguration fileTexts;
    private void initializeInside() throws ComponentException {
        FileUtils.createPluginFileInDataFolder("text.yml");
        fileTexts = FileUtils.getYamlConfiguration("text.yml");

        texts = new HashMap<>();
        for (String key : fileTexts.getKeys(true))
            texts.put(key, new StringTextNode(checkComponent(key)));
    }
    private String checkComponent(String path) throws ComponentException{
        String value = fileTexts.getString(path);
        if (value == null)
            throw new ComponentException("<red>Value " + path + " is null!");
        if (value.contains("&") || value.contains("§"))
            throw new ComponentException("<red>Value " + path + " must not have char \"&\" or \"§\"!");
        return value;
    }


    private static HashMap<String, StringTextNode> texts;
    public static boolean isEmpty(String key){
        return node(key).isEmpty();
    }
    public static Component value(String key){
        return node(key).value();
    }
    public static StringTextNode node(String key){
        StringTextNode node = texts.get(key);
        if (node == null) {
            sendMessageToConsole("<red>Text value " + key + " does not exist!");
            return new StringTextNode(key);
        }
        return node;
    }

    public static void sendMessageToConsole(String message){
        sendMessageToConsole(TextUtils.toComponent(message), true);
    }
    public static void sendMessageToConsole(String message, boolean hasMessageBegin){
        sendMessageToConsole(TextUtils.toComponent(message), hasMessageBegin);
    }
    public static void sendMessageToConsole(Component message){
        sendMessageToConsole(message, true);
    }
    public static void sendMessageToConsole(Component message, boolean hasMessageBegin){
        InteractiveSigns.getInstance().getServer().getConsoleSender().sendMessage(hasMessageBegin ? MESSAGE_BEGIN.append(message) : message);
    }
}
