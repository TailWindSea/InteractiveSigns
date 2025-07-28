package me.vovari2.interactivesigns.utils;

import org.bukkit.Location;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class ItemDisplayUtils {
    public static @Nullable ItemDisplay getItemDisplayOnSign(Location location, Side side){
        Collection<ItemDisplay> list = location.getWorld().getNearbyEntitiesByType(
                ItemDisplay.class,
                location,
                0.1,
                display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSign(side.name())));

        if (list.isEmpty())
            return null;

        return list.iterator().next();
    }
    public static void dropItemFromDisplay(Location location, Side side){
        ItemDisplay display = ItemDisplayUtils.getItemDisplayOnSign(location, side);
        if (display == null)
            return;

        display.remove();
        if (display.getItemStack() != null)
            location.getWorld().dropItemNaturally(location, display.getItemStack());
    }

    public static @Nullable ItemDisplay getItemDisplayOnSignOld(Location location){
        Collection<ItemDisplay> list = location.getWorld().getNearbyEntitiesByType(
                ItemDisplay.class,
                location,
                0.1,
                display -> display.getPersistentDataContainer().has(NamespacedKeyUtils.forItemOnSignOld()));

        if (list.isEmpty())
            return null;

        return list.iterator().next();
    }
    public static void convertFromOldDisplay(ItemDisplay display){
        if (display == null)
            return;

        PersistentDataContainer container = display.getPersistentDataContainer();
        String side = container.get(NamespacedKeyUtils.forItemOnSignOld(), PersistentDataType.STRING);
        if (side == null)
            return;

        container.remove(NamespacedKeyUtils.forItemOnSignOld());
        container.set(NamespacedKeyUtils.forItemOnSign(side), PersistentDataType.BOOLEAN, false);
    }

    public static void removeItemDisplayFromSign(@NotNull Location location){
        Collection<ItemDisplay> list = location.getWorld().getNearbyEntitiesByType(
                ItemDisplay.class,
                location,
                0.1,
                display -> {
                    PersistentDataContainer container = display.getPersistentDataContainer();
                    return container.has(NamespacedKeyUtils.forItemOnSignOld()) || container.has(NamespacedKeyUtils.forItemOnSign(Side.FRONT.name())) || container.has(NamespacedKeyUtils.forItemOnSign(Side.BACK.name()));
                } );

        if (list.isEmpty())
            return;

        list.forEach(Entity::remove);
    }


}
