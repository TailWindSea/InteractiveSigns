package me.vovari2.interactivesigns;

import me.vovari2.interactivesigns.exceptions.ComponentException;
import me.vovari2.interactivesigns.utils.FileUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static boolean ENABLE_ITEMS_VOLUME;

    public static boolean PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS;
    public static String PERMISSION_CAN_USE_SIGNS;

    public static String HUSKCLAIMS_FLAG_ID;

    public static String LANDS_FLAG_ID;
    public static String LANDS_FLAG_NAME;
    public static Material LANDS_FLAG_MATERIAL;
    public static String LANDS_FLAG_DESCRIPTION;

    public static void initialize() throws ComponentException {
        new Config().initializeInside();
    }

    private FileConfiguration fileConfig;
    private void initializeInside() throws ComponentException {
        FileUtils.createPluginFileInDataFolder("config.yml");
        fileConfig = FileUtils.getYamlConfiguration("config.yml");

        ENABLE_ITEMS_VOLUME = getBoolean("enable_items_volume", false);

        PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS = getBoolean("player_need_to_have_permission_to_use_signs");
        PERMISSION_CAN_USE_SIGNS = getString("permission_can_use_signs");

        HUSKCLAIMS_FLAG_ID = getString("huskclaims.flag_id");

        LANDS_FLAG_ID = getString("lands.flag_id");
        LANDS_FLAG_NAME = getString("lands.flag_name");
        LANDS_FLAG_MATERIAL = getMaterial("lands.flag_material");
        LANDS_FLAG_DESCRIPTION = getString("lands.flag_description");
    }

    private boolean getBoolean(String path) throws ComponentException{
        Object object = fileConfig.get(path);
        if (!(object instanceof Boolean))
            throw new ComponentException("Value " + path + " is not a boolean!");
        return (boolean) object;
    }
    private boolean getBoolean(String path, boolean def){
        return fileConfig.getBoolean(path, def);
    }

    private Material getMaterial(String path) throws ComponentException{
        String str = getString(path);
        try { return Material.valueOf(str.toUpperCase()); }
        catch(IllegalArgumentException e){ throw new ComponentException("Value " + path + " is not a material!");}
    }
    private String getString(String path) throws ComponentException{
        Object object = fileConfig.get(path);
        if (!(object instanceof String ))
            throw new ComponentException("Value " + path + " is not an string!");
        return (String) object;
    }

}
