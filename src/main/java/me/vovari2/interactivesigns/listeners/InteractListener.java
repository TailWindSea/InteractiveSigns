package me.vovari2.interactivesigns.listeners;

import com.destroystokyo.paper.MaterialTags;
import me.vovari2.interactivesigns.*;
import me.vovari2.interactivesigns.loaders.types.ConfigurationLoader;
import me.vovari2.interactivesigns.messages.Messages;
import me.vovari2.interactivesigns.sign.SignRotations;
import me.vovari2.interactivesigns.sign.SignTypes;
import me.vovari2.interactivesigns.utils.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InteractListener implements Listener {

    private final Component ART_MAP_LINE = TextUtils.toComponent("*{=}*");
    public boolean isCanvas(Sign signBlock){
        return signBlock.getSide(Side.FRONT).lines().get(3).equals(ART_MAP_LINE);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractPlayer(PlayerInteractEvent event){
        // Блок должен являться табличкой
        if (event.getClickedBlock() == null || !(event.getClickedBlock().getState() instanceof Sign signBlock))
            return;

        // Защита от вставки в таблички плагина ArtMap
        if (isCanvas(signBlock))
            return;

        Material signMaterial = signBlock.getType();
        Vector signDirection = SignRotations.get(SignTypes.getSignFace(signBlock.getBlockData()));
        if (SignTypes.isWall(signMaterial)) // Если табличка настенная, отражаем дисплей
            signDirection = signDirection.clone().multiply(-1);

        Player player = event.getPlayer();
        Side side = getClickedSide(
                signDirection,
                new Vector(Math.sin(-Math.toRadians(player.getYaw())), signDirection.getY(), Math.cos(Math.toRadians(player.getYaw()))));

        Location signLocation = signBlock.getLocation();
        Location displayLocation = signLocation.add(0.5F, 0.5F, 0.5F);
        displayLocation.setDirection(side.equals(Side.FRONT) ? signDirection.clone().multiply(-1) : signDirection);


        switch(event.getAction()) {
            case RIGHT_CLICK_BLOCK: {
                ItemDisplayUtils.convertFromOldDisplay(ItemDisplayUtils.getItemDisplayOnSignOld(displayLocation));
                ItemDisplayUtils.convertFromOldDisplay(ItemDisplayUtils.getItemDisplayOnSignOld(displayLocation));

                boolean isDisplay = ItemDisplayUtils.getItemDisplayOnSign(displayLocation, side) != null;
                if (!ProtectionPlugins.canInteractWithSign(player, signLocation) && !Executor.hasPermission(player)){
                    if (isDisplay)
                        Messages.WARNING_YOU_CANT_USE_THAT_HERE.send(player);
                    return;
                }

                if (player.isSneaking())
                    return;

                Location center = VersionUtils.getBlockCenter(signBlock.getLocation());
                ItemStack item = getItemInHand(event.getHand(), player);
                if (signBlock.isWaxed()){
                    if (item != null && MaterialTags.AXES.isTagged(item.getType())) {
                        addItemDamage(player, item);
                        signBlock.setWaxed(false);
                        signBlock.update();
                        SoundUtils.playWaxOffItemOnSign(center);
                        ParticleUtils.spawnWaxOff(center);
                    }
                    event.setCancelled(true);
                    return;
                }

                boolean isText = isOccupiedByText(signBlock.getSide(side).lines());
                if (isText || isDisplay){
                    if (item != null && Material.HONEYCOMB.equals(item.getType())){
                        item.subtract();
                        signBlock.setWaxed(true);
                        signBlock.update();
                        SoundUtils.playWaxOnItemOnSign(center);
                        ParticleUtils.spawnWaxOn(center);
                    }
                    if (isDisplay)
                        event.setCancelled(true);
                    return;
                }

                if (item == null)
                    return;

                if (ConfigurationLoader.PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS)
                    if (!player.hasPermission(ConfigurationLoader.PERMISSION_CAN_USE_SIGNS))
                        return;

                if (ConfigurationLoader.DISALLOW_SIGN_ITEM_PLACEMENT.contains(item.getType())){
                    Messages.WARNING_YOU_CANT_PUT_THAT_HERE.send(player);
                    return;
                }

                ItemStack placedItem = item.clone();
                placedItem.setAmount(1);
                player.getInventory().getItem(event.getHand()).subtract();

                if (Plugins.CoreProtect.isEnabled())
                    CoreProtectUtils.logPuttingItemOnSign(player.getName(), signLocation, placedItem.getType());

                ItemDisplay itemDisplay = (ItemDisplay) displayLocation.getWorld().spawnEntity(displayLocation, EntityType.ITEM_DISPLAY);
                itemDisplay.getPersistentDataContainer().set(NamespacedKeyUtils.forItemOnSign(side.name()), PersistentDataType.BOOLEAN, false);
                itemDisplay.setTransformation(SignTypes.getTransformation(side, signMaterial, placedItem.getType()));
                itemDisplay.setItemStack(placedItem);

                SoundUtils.playPasteItemOnSign(displayLocation);

                event.setCancelled(true);
            } break;
            case LEFT_CLICK_BLOCK: {
                ItemDisplay display = ItemDisplayUtils.getItemDisplayOnSign(displayLocation, side);
                if (display == null)
                    return;

                if (signBlock.isWaxed())
                    return;

                if (!ProtectionPlugins.canInteractWithSign(player, signLocation) && !Executor.hasPermission(player)){
                    Messages.WARNING_YOU_CANT_USE_THAT_HERE.send(player);
                    return;
                }

                if (ConfigurationLoader.PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS)
                    if (!player.hasPermission(ConfigurationLoader.PERMISSION_CAN_USE_SIGNS)){
                        Messages.WARNING_YOU_CANT_USE_THAT_HERE.send(player);
                        event.setCancelled(true);
                        return;
                    }

                display.remove();
                ItemStack droppedItem = display.getItemStack();

                if (Plugins.CoreProtect.isEnabled())
                    CoreProtectUtils.logTakingItemOnSign(player.getName(), signLocation, droppedItem.getType());

                signBlock.getWorld().dropItemNaturally(VersionUtils.getBlockCenter(signBlock.getLocation()), droppedItem);
                SoundUtils.playRemoveItemOnSign(displayLocation);
                event.setCancelled(true);
            }
            break;
            default:
        }
    }
    private static @NotNull Side getClickedSide(Vector signDirection, Vector playerDirection){
        double lengthForward = signDirection.clone().add(playerDirection).length(),
                lengthBack = signDirection.clone().subtract(playerDirection).length();

        if (lengthForward <= lengthBack)
            return Side.FRONT;
        return Side.BACK;
    }
    private static ItemStack getItemInHand(EquipmentSlot slot, Player player){
        if (slot == null)
            return null;

        ItemStack itemStack = player.getInventory().getItem(slot);
        if (itemStack.isEmpty())
            return null;

        return itemStack;
    }
    private static void addItemDamage(Player player, ItemStack item){
        if (!(item.getItemMeta() instanceof Damageable damageable))
            return;
        damageable.setDamage(damageable.getDamage() + 1);
        item.setItemMeta(damageable);
        if (damageable.getDamage() >= item.getType().getMaxDurability()){
            item.subtract();
            SoundUtils.playToolBreak(player);
        }
    }
    private static boolean isOccupiedByText(@NotNull List<Component> lines){
        for (Component text : lines)
            if (!text.equals(Component.empty()))
                return true;
        return false;
    }
}
