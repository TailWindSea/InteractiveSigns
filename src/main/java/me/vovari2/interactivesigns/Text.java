package me.vovari2.interactivesigns;

import me.vovari2.interactivesigns.exceptions.ComponentException;
import me.vovari2.interactivesigns.utils.FileUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Locale;

public class Text {
    public static final String PLUGIN_NAME = InteractiveSigns.getInstance().getName();
    public static final String VERSION = InteractiveSigns.getInstance().getPluginMeta().getVersion();
    private static final Component MESSAGE_BEGIN = MiniMessage.miniMessage().deserialize("<gray>[" + PLUGIN_NAME + "]<reset> ");

    private static ConsoleCommandSender sender;
    protected static void initialize(ConsoleCommandSender newSender) throws ComponentException {
        sender = newSender;
        new Text().initializeInside();
    }

    private FileConfiguration fileTexts;
    private void initializeInside() throws ComponentException {
        FileUtils.createPluginFileInDataFolder("text.yml");
        fileTexts = FileUtils.getYamlConfiguration("text.yml");

        texts = new HashMap<>();
        for (String key : fileTexts.getKeys(true))
            texts.put(key, new TextNode(getComponentFromYaml(key)));
    }
    private Component getComponentFromYaml(String path) throws ComponentException{
        String value = fileTexts.getString(path);
        if (value == null)
            throw new ComponentException("<red>Value " + path + " is null!");
        if (value.isEmpty())
            throw new ComponentException("<red>Value " + path + " is empty!");
        if (value.contains("&") || value.contains("§"))
            throw new ComponentException("<red>Value " + path + " must not have char \"&\" or \"§\"!");
        return MiniMessage.miniMessage().deserialize(value);
    }


    private static HashMap<String, TextNode> texts;
    public static Component value(String key){
        TextNode node = texts.get(key);
        if (node == null) {
            sendMessageToConsole("<red>Text value " + key + " does not exist!");
            return MiniMessage.miniMessage().deserialize(key);
        }
        return node.value;
    }
    public static Component value(String key, Locale locale){
        TextNode node = texts.get(key);
        if (node == null) {
            sendMessageToConsole("<red>Text value " + key + " does not exist!");
            return MiniMessage.miniMessage().deserialize(key);
        }
        return node.value;
    }
    public static TextNode node(String key){
        TextNode node = texts.get(key);
        if (node == null) {
            sendMessageToConsole("<red>Text value " + key + " does not exist!");
            return new TextNode(MiniMessage.miniMessage().deserialize(key));
        }
        return node;
    }
    public static TextNode node(String key, Locale locale){
        TextNode node = texts.get(key);
        if (node == null) {
            sendMessageToConsole("<red>Text value " + key + " does not exist!");
            return new TextNode(MiniMessage.miniMessage().deserialize(key));
        }
        return node;
    }

    public static class TextNode{
        private final Component value;
        private TextNode(Component value){
            this.value = value;
        }
        public TextNode replace(String placeholder, String placeholderValue){
            return new TextNode(this.value.replaceText(TextReplacementConfig.builder().matchLiteral(placeholder).replacement(placeholderValue).build()));
        }
        public Component value(){
            return value;
        }
    }

    public static void sendMessageToConsole(String message){
        sendMessageToConsole(message, true);
    }
    public static void sendMessageToConsole(String message, boolean hasStartMessage){
        sender.sendMessage(
                (hasStartMessage ? MESSAGE_BEGIN : Component.empty())
                        .append(MiniMessage.miniMessage().deserialize(message)));
    }
    public static void sendMessageToConsole(Component message){
        sendMessageToConsole(message, true);
    }
    public static void sendMessageToConsole(Component message, boolean hasMessageBegin){
        sender.sendMessage(hasMessageBegin ? MESSAGE_BEGIN.append(message) : message);
    }
}
