package me.vovari2.interactivesigns;

import me.vovari2.interactivesigns.exceptions.ComponentException;
import me.vovari2.interactivesigns.utils.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static boolean PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS;
    public static String PERMISSION_CAN_USE_SIGNS;

    public static void initialize() throws ComponentException {
        new Config().initializeInside();
    }

    private FileConfiguration fileConfig;
    private void initializeInside() throws ComponentException {
        FileUtils.createPluginFileInDataFolder("config.yml");
        fileConfig = FileUtils.getYamlConfiguration("config.yml");

        PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS = getBoolean("player_need_to_have_permission_to_use_signs");
        PERMISSION_CAN_USE_SIGNS = getString("permission_can_use_permission");
    }

    private boolean getBoolean(String path) throws ComponentException{
        Object object = fileConfig.get(path);
        if (!(object instanceof Boolean))
            throw new ComponentException("Value " + path + " is not a boolean!");
        return (boolean) object;
    }
    private String getString(String path) throws ComponentException{
        Object object = fileConfig.get(path);
        if (!(object instanceof String ))
            throw new ComponentException("Value " + path + " is not an string!");
        return (String) object;
    }

}
