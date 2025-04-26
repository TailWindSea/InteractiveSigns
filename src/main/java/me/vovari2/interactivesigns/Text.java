package me.vovari2.interactivesigns;

import me.vovari2.interactivesigns.text_nodes.StringTextNode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class Text extends me.vovari2.foamlib.Text {
    public static final String PLUGIN_NAME = InteractiveSigns.getInstance().getName();
    public static final String VERSION = InteractiveSigns.getInstance().getPluginMeta().getVersion();
    protected static void initialize() throws Exception {
        instance = InteractiveSigns.getInstance();
        new Text().initializeInside();
    }


    private FileConfiguration fileTexts;
    private void initializeInside() throws Exception {
        me.vovari2.foamlib.utils.FileUtils.createResourceFileInFolder(InteractiveSigns.getInstance(), "text.yml");
        fileTexts = me.vovari2.foamlib.utils.FileUtils.getContentFromYamlFile(me.vovari2.foamlib.utils.FileUtils.getPluginFolder(InteractiveSigns.getInstance()), "text.yml");

        texts = new HashMap<>();
        for (String key : fileTexts.getKeys(true))
            texts.put(key, new StringTextNode(checkComponent(key)));
    }
    private String checkComponent(String path) throws Exception{
        String value = fileTexts.getString(path);
        if (value == null)
            throw new Exception("<red>Value " + path + " is null!");
        if (value.contains("&") || value.contains("§"))
            throw new Exception("<red>Value " + path + " must not have char \"&\" or \"§\"!");
        return value;
    }

    private static HashMap<String, StringTextNode> texts;
    public static StringTextNode node(String key){
        StringTextNode node = texts.get(key);
        if (node == null) {
            error("Text value " + key + " does not exist!");
            return new StringTextNode(key);
        }
        return node;
    }
    public static void send(String key, CommandSender sender){
        node(key).send(sender);
    }
}
