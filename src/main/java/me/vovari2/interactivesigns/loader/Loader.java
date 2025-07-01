package me.vovari2.interactivesigns.loader;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class Loader {
    protected YamlConfiguration configuration;
    protected Loader(){}

    protected @NotNull Object getObject(@NotNull String path) throws Exception{
        Object object = configuration.get(path);
        if (object == null)
            throw new Exception("Value '%s' must not be a null!".formatted(path));
        return object;
    }
    protected @Nullable Object getObject(@NotNull String path, @Nullable Object def){
        Object object = configuration.get(path);
        return object == null ? def : object;
    }

    protected boolean getBoolean(@NotNull String path) throws Exception{
        Object object = getObject(path);
        if (!(object instanceof Boolean value))
            throw new Exception("<red>Value '%s' must be true or false!".formatted(path));
        return value;
    }
    protected boolean getBoolean(@NotNull String path, boolean def) throws Exception{
        Object object = getObject(path);
        return object instanceof Boolean value ? value : def;
    }

    protected int getInt(@NotNull String path) throws Exception{
        Object object = getObject(path);
        if (!(object instanceof Integer value))
            throw new Exception("Value '%s' must be an integer!".formatted(path));
        return value;
    }
    protected int getInt(@NotNull String path, int min) throws Exception{
        int value = getInt(path);
        if (value < min)
            throw new Exception("Value '%s' must be greater than %s!".formatted(path, min));
        return value;
    }

    protected double getDouble(@NotNull String path) throws Exception{
        Object object = getObject(path);
        if (!(object instanceof Double value))
            throw new Exception("<red>Value '%s' must be a double!".formatted(path));
        return value;
    }

    protected @NotNull String getString(@NotNull String path) throws Exception{
        String value = configuration.getString(path);
        if (value == null)
            throw new Exception("Value '%s' must not be a null!".formatted(path));
        return value;
    }
    protected @Nullable String getString(@NotNull String path, @Nullable String def) throws Exception {
        String value = configuration.getString(path);
        return value == null ? def : value;
    }

    protected @Nullable Material getMaterial(@NotNull String path, @Nullable Material def){
        String value = configuration.getString(path, null);
        if (value == null)
            return def;
        try{ return Material.valueOf(value.toUpperCase());}
        catch(IllegalArgumentException e){ return def; }
    }
    protected @NotNull Material getMaterial(@NotNull String value, @NotNull String path) throws Exception {
        try{ return Material.valueOf(value.toUpperCase());}
        catch(IllegalArgumentException e){ throw new Exception("<red>Value '%s' must be a material!".formatted(path));}
    }

    protected @Nullable EntityType getEntityType(@NotNull String path, @Nullable EntityType def) throws Exception {
        String value = getString(path, null);
        if (value == null)
            return def;
        try{ return EntityType.valueOf(value.toUpperCase());}
        catch(IllegalArgumentException e){ return def; }
    }
    protected @NotNull EntityType getEntityType(@NotNull String value, @NotNull String path) throws Exception {
        try{ return EntityType.valueOf(value.toUpperCase());}
        catch(IllegalArgumentException e){ throw new Exception("<red>Value '%s' must be an entity type!".formatted(path));}
    }
}
