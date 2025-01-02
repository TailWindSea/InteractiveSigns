package me.vovari2.interactivesigns.utils;

import org.bukkit.Location;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ItemDisplayUtils {
    public static ItemDisplay getItemDisplayOnSign(Location location, Side side){
        Collection<ItemDisplay> list = location.getWorld().getNearbyEntitiesByType(
                ItemDisplay.class,
                location,
                0.1,
                display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign())
                        && side.name().equals(display.getPersistentDataContainer().get(NamespacedKeyUtils.forItemOnSign(), PersistentDataType.STRING)));

        if (list.isEmpty())
            return null;

        return list.iterator().next();
    }
    public static @NotNull Collection<ItemDisplay> getItemDisplaysOnSign(Location location){
        return location.getWorld().getNearbyEntitiesByType(
                ItemDisplay.class,
                location,
                0.1,
                display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign())
                        && (Side.FRONT.name().equals(display.getPersistentDataContainer().get(NamespacedKeyUtils.forItemOnSign(), PersistentDataType.STRING))
                        || Side.BACK.name().equals(display.getPersistentDataContainer().get(NamespacedKeyUtils.forItemOnSign(), PersistentDataType.STRING))));
    }
}
