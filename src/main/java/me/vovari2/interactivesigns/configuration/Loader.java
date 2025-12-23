package me.vovari2.interactivesigns.configuration;

import me.vovari2.interactivesigns.utils.FileUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Loader {
    private static final String RESOURCE_NAME = "config.yml";

    private final String fileName;
    private final YamlConfiguration configuration;
    Loader() throws Exception {
        this.fileName = RESOURCE_NAME;
        FileUtils.saveResource(RESOURCE_NAME);
        configuration = FileUtils.loadResource(RESOURCE_NAME);

        Configuration.ENABLE_ITEMS_VOLUME = getBoolean("enable_items_volume");
        Configuration.BLACKLIST_OF_ITEMS = getMaterialList("blacklist_of_items");

        Configuration.PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS = configuration.getBoolean("player_need_to_have_permission_to_use_signs", false);
        Configuration.PERMISSION_CAN_USE_SIGNS = getString("permission_can_use_signs");

        Configuration.WORLDEDIT.AUTO_DROP_ITEMS = configuration.getBoolean("worldedit.auto_drop_items", false);

        Configuration.HUSKCLAIMS.FLAG_ID = getString("huskclaims.flag_id", "items_in_signs");

        Configuration.LANDS.FLAG_ID = getString("lands.flag_id", "items_in_signs");
        Configuration.LANDS.FLAG_NAME = getString("lands.flag_name", "Items in signs");
        Configuration.LANDS.FLAG_MATERIAL = getMaterial("lands.flag_material", Material.SPRUCE_HANGING_SIGN);
        Configuration.LANDS.FLAG_DESCRIPTION = getString("lands.flag_description", "Flag that allows you to insert and take items from sings");

        Configuration.DOMINION.FLAG_ID = getString("dominion.flag_id", "items_in_signs");
        Configuration.DOMINION.FLAG_NAME = getString("dominion.flag_name", "Items in signs");
        Configuration.DOMINION.FLAG_MATERIAL = getMaterial("dominion.flag_material", Material.SPRUCE_HANGING_SIGN);
        Configuration.DOMINION.FLAG_DESCRIPTION = getString("dominion.flag_description", "Whether can insert and take items from sings");
    }

    private @NotNull Object getObject(@NotNull String path) throws Exception{
        Object object = configuration.get(path);
        if (object == null)
            throw new Exception("Value '%s' in the '%s' must not be empty!".formatted(path, fileName));
        return object;
    }
    private boolean getBoolean(@NotNull String path) throws Exception{
        Object object = getObject(path);
        if (!(object instanceof Boolean value))
            throw new Exception("<red>Value '%s' in the '%s' must be true or false!".formatted(path, fileName));
        return value;
    }

    private @NotNull String getString(@NotNull String path) throws Exception{
        String value = configuration.getString(path);
        if (value == null)
            throw new Exception("Value '%s' in the '%s' must not be empty!".formatted(path, fileName));
        return value;
    }
    private @NotNull String getString(@NotNull String path, @NotNull String def){
        String value = configuration.getString(path);
        if (value == null)
            return def;
        return value;
    }

    protected @Nullable Material getMaterial(@NotNull String path, @Nullable Material def){
        String value = configuration.getString(path, null);
        if (value == null)
            return def;
        try{ return Material.valueOf(value.toUpperCase());}
        catch(IllegalArgumentException e){ return def; }
    }

    private List<Material> getMaterialList(String path) throws Exception{
        Iterator<String> iterator = configuration.getStringList(path).iterator();
        List<Material> materialList = new LinkedList<>();

        for(int i = 1; iterator.hasNext(); i++)
            materialList.add(toMaterial(iterator.next(), "%s.%s".formatted(path, String.valueOf(i))));
        return materialList;
    }
    private @NotNull Material toMaterial(@NotNull String value, @NotNull String path) throws Exception {
        try{ return Material.valueOf(value.toUpperCase());}
        catch(IllegalArgumentException e){ throw new Exception("<red>Value '%s' must be a material!".formatted(path));}
    }


}
