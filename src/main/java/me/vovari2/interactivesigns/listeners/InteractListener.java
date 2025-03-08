package me.vovari2.interactivesigns.listeners;

import com.destroystokyo.paper.MaterialTags;
import me.vovari2.interactivesigns.*;
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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractPlayer(PlayerInteractEvent event){
        if (event.getClickedBlock() == null
                || !(event.getClickedBlock().getState() instanceof Sign signBlock))
            return;

        if (ArtMapUtils.isCanvas(signBlock))
            return;

        Material signMaterial = signBlock.getType();
        Vector signDirection = SignRotations.get(SignTypes.getSignFace(signBlock.getBlockData()));
        if (SignTypes.isWall(signMaterial))
            signDirection = signDirection.clone().multiply(-1);

        Player player = event.getPlayer();
        Vector playerDirection = new Vector(Math.sin(-Math.toRadians(player.getYaw())), signDirection.getY(), Math.cos(Math.toRadians(player.getYaw())));
        Side side = getClickedSide(signDirection, playerDirection);

        Location signLocation = signBlock.getLocation();
        Location displayLocation = signLocation.add(0.5F, 0.5F, 0.5F);
        displayLocation.setDirection(side.equals(Side.BACK) ? signDirection.clone().multiply(-1) : signDirection);
        player.sendMessage(side.name() + "");
        switch(event.getAction()) {
            case RIGHT_CLICK_BLOCK: {
                if (ItemDisplayUtils.getItemDisplayOnSignOld(displayLocation) != null){
                    player.sendMessage(TextUtils.toComponent("<red>The old signs format is used here <newline>Use the <click:run_command:'/ins refactor'><hover:show_text:'<gray>Нажмите'><yellow>/ins refactor</yellow></hover></click> to update the item format in the signs<newline> "));
                    event.setCancelled(true);
                    return;
                }

                if (!ProtectionPlugins.canInteractWithSign(player, signLocation)){
                    if (!Text.isEmpty("warning.you_cant_use_that_here"))
                        Delay.run(() -> player.sendMessage(Text.value("warning.you_cant_use_that_here")), player, "cant_use_this_here", 20);
                    return;
                }

                if (player.isSneaking())
                    return;

                Location center = VersionUtils.getBlockCenter(signBlock.getLocation());
                ItemStack item = getItemInHand(event.getHand(), player);
                if (signBlock.isWaxed()){
                    if (item != null && MaterialTags.AXES.isTagged(item.getType())) {
                        ItemUtils.addDurability(player, item, -1);
                        signBlock.setWaxed(false);
                        signBlock.update();
                        SoundUtils.playWaxOffItemOnSign(center);
                        ParticleUtils.spawnWaxOff(center);
                    }
                    event.setCancelled(true);
                    return;
                }

                boolean isText = isOccupiedByText(signBlock.getSide(side).lines());
                boolean isDisplay = ItemDisplayUtils.getItemDisplayOnSign(displayLocation, side) != null;
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

                if (Config.PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS)
                    if (!player.hasPermission(Config.PERMISSION_CAN_USE_SIGNS))
                        return;


                ItemStack placedItem = item.clone();
                placedItem.setAmount(1);
                player.getInventory().getItem(event.getHand()).subtract();

                if (InteractiveSigns.getCoreProtectAPI() != null)
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

                if (!ProtectionPlugins.canInteractWithSign(player, signLocation)){
                    if (!Text.isEmpty("warning.you_cant_use_that_here"))
                        Delay.run(() -> player.sendMessage(Text.value("warning.you_cant_use_that_here")), player, "cant_use_this_here", 20);
                    return;
                }

                if (Config.PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS)
                    if (!player.hasPermission(Config.PERMISSION_CAN_USE_SIGNS)){
                        if (!Text.isEmpty("warning.you_cant_use_that_here"))
                            Delay.run(() -> player.sendMessage(Text.value("warning.you_cant_use_that_here")), player, "cant_use_this_here", 20);
                        event.setCancelled(true);
                        return;
                    }

                display.remove();
                ItemStack droppedItem = display.getItemStack();
                if (droppedItem == null)
                    return;

                if (InteractiveSigns.getCoreProtectAPI() != null)
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
    private static boolean isOccupiedByText(@NotNull List<Component> lines){
        for (Component text : lines)
            if (!text.equals(Component.empty()))
                return true;
        return false;
    }
}
