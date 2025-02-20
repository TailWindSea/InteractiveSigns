package me.vovari2.interactivesigns.utils;

import org.bukkit.Location;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ItemDisplayUtils {
    public static boolean isWaxedItemDisplay(ItemDisplay display, Side side){
        return Boolean.TRUE.equals(display.getPersistentDataContainer().get(NamespacedKeyUtils.forItemOnSign(side.name()), PersistentDataType.BOOLEAN));
    }
    public static void setWaxedItemDisplay(ItemDisplay display, Side side, boolean value){
        display.getPersistentDataContainer().set(NamespacedKeyUtils.forItemOnSign(side.name()), PersistentDataType.BOOLEAN, value);
    }

    public static ItemDisplay getItemDisplayOnSign(Location location, Side side){
        Collection<ItemDisplay> list = location.getWorld().getNearbyEntitiesByType(
                ItemDisplay.class,
                location,
                0.1,
                display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign(side.name())));

        if (list.isEmpty())
            return null;

        return list.iterator().next();
    }
    public static @NotNull Collection<ItemDisplay> getItemDisplaysOnSign(Location location){
        return location.getWorld().getNearbyEntitiesByType(
                ItemDisplay.class,
                location,
                0.1,
                display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign(Side.FRONT.name())) || display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign(Side.BACK.name())));
    }

    public static ItemDisplay getItemDisplayOnSignOld(Location location){
        Collection<ItemDisplay> list = location.getWorld().getNearbyEntitiesByType(
                ItemDisplay.class,
                location,
                0.1,
                display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSignOld()));

        if (list.isEmpty())
            return null;

        return list.iterator().next();
    }
    public static @NotNull Collection<ItemDisplay> getItemDisplaysOnSignOld(Location location, double radius){
        return location.getWorld().getNearbyEntitiesByType(
                ItemDisplay.class,
                location,
                radius,
                display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSignOld()));
    }
}
