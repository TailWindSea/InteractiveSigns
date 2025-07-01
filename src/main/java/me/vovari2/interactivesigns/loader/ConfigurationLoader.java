package me.vovari2.interactivesigns.loader;

import me.vovari2.interactivesigns.Console;
import me.vovari2.interactivesigns.utils.FileUtils;
import org.bukkit.Material;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ConfigurationLoader extends Loader{
    public static boolean ENABLE_ITEMS_VOLUME;

    public static List<Material> DISALLOW_SIGN_ITEM_PLACEMENT;

    public static boolean PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS;
    public static String PERMISSION_CAN_USE_SIGNS;

    public static String HUSKCLAIMS_FLAG_ID;

    public static String LANDS_FLAG_ID;
    public static String LANDS_FLAG_NAME;
    public static Material LANDS_FLAG_MATERIAL;
    public static String LANDS_FLAG_DESCRIPTION;

    public static boolean initialize() {
        try { new ConfigurationLoader("config.yml"); return true; }
        catch(Exception e){ Console.error("Error loading configuration: " + e.getMessage()); return false; }
    }
    private ConfigurationLoader(String resourcePath) throws Exception {
        FileUtils.createResourceFileInFolder(resourcePath);
        configuration = FileUtils.getContentFromYamlFile(FileUtils.getPluginFolder(), resourcePath);

        ENABLE_ITEMS_VOLUME = getBoolean("enable_items_volume", false);

        DISALLOW_SIGN_ITEM_PLACEMENT = getMaterialList("blacklist_of_items");

        PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS = getBoolean("player_need_to_have_permission_to_use_signs");
        PERMISSION_CAN_USE_SIGNS = getString("permission_can_use_signs");

        HUSKCLAIMS_FLAG_ID = getString("huskclaims.flag_id");

        LANDS_FLAG_ID = configuration.getString("lands.flag_id");
        LANDS_FLAG_NAME = getString("lands.flag_name");
        LANDS_FLAG_MATERIAL = getMaterial("lands.flag_material", Material.STONE);
        LANDS_FLAG_DESCRIPTION = getString("lands.flag_description");
    }

    private List<Material> getMaterialList(String path) throws Exception{
        Iterator<String> iterator = configuration.getStringList(path).iterator();
        List<Material> materialList = new LinkedList<>();
        for(int i = 1; iterator.hasNext(); i++)
            materialList.add(getMaterial(iterator.next(), "%s.%s".formatted(path, String.valueOf(i))));
        return materialList;
    }
}
