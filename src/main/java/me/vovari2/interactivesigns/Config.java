package me.vovari2.interactivesigns;

import me.vovari2.foamlib.utils.FileUtils;
import org.bukkit.Material;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Config extends me.vovari2.foamlib.Config{
    public static boolean ENABLE_ITEMS_VOLUME;

    public static List<Material> DISALLOW_SIGN_ITEM_PLACEMENT;

    public static boolean PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS;
    public static String PERMISSION_CAN_USE_SIGNS;

    public static String HUSKCLAIMS_FLAG_ID;

    public static String LANDS_FLAG_ID;
    public static String LANDS_FLAG_NAME;
    public static Material LANDS_FLAG_MATERIAL;
    public static String LANDS_FLAG_DESCRIPTION;

    public static void initialize() throws Exception {
        new Config().initializeInside();
    }

    private void initializeInside() throws Exception {
        FileUtils.createResourceFileInFolder(InteractiveSigns.getInstance(), "config.yml");
        fileConfig = FileUtils.getContentFromYamlFile(FileUtils.getPluginFolder(InteractiveSigns.getInstance()), "config.yml");

        ENABLE_ITEMS_VOLUME = getBoolean("enable_items_volume", false);

        DISALLOW_SIGN_ITEM_PLACEMENT = getMaterialList("blacklist_of_items");

        PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS = getBoolean("player_need_to_have_permission_to_use_signs");
        PERMISSION_CAN_USE_SIGNS = getString("permission_can_use_signs");

        HUSKCLAIMS_FLAG_ID = getString("huskclaims.flag_id");

        LANDS_FLAG_ID = fileConfig.getString("lands.flag_id");
        LANDS_FLAG_NAME = getString("lands.flag_name");
        LANDS_FLAG_MATERIAL = getMaterial("lands.flag_material");
        LANDS_FLAG_DESCRIPTION = getString("lands.flag_description");
    }

    private List<Material> getMaterialList(String path) throws Exception{
        Iterator<String> iterator = fileConfig.getStringList(path).iterator();
        List<Material> materialList = new LinkedList<>();
        for(int i = 1; iterator.hasNext(); i++)
            materialList.add(getMaterial(iterator.next(), "%s.%s".formatted(path, String.valueOf(i))));
        return materialList;
    }
}
