package me.vovari2.interactivesigns.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class ItemUtils {
    public static void addDurability(Player player, ItemStack item, int amount){
        if (!(item.getItemMeta() instanceof Damageable damageable))
            return;
        damageable.setDamage(damageable.getDamage() - amount);
        item.setItemMeta(damageable);
        if (damageable.getDamage() >= item.getType().getMaxDurability()){
            item.subtract();
            SoundUtils.playToolBreak(player);
        }
    }

}
