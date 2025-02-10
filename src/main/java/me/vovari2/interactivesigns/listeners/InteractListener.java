package me.vovari2.interactivesigns.listeners;

import com.destroystokyo.paper.MaterialSetTag;
import com.destroystokyo.paper.MaterialTags;
import me.vovari2.interactivesigns.*;
import me.vovari2.interactivesigns.sign.SignRotations;
import me.vovari2.interactivesigns.sign.SignTypes;
import me.vovari2.interactivesigns.utils.ItemDisplayUtils;
import me.vovari2.interactivesigns.utils.NamespacedKeyUtils;
import me.vovari2.interactivesigns.utils.SoundUtils;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractPlayer(PlayerInteractEvent event){
        if (event.isCancelled())
            return;

        if (event.getClickedBlock() == null
                || !(event.getClickedBlock().getState() instanceof Sign signBlock))
            return;

        Player player = event.getPlayer();
        if (player.isSneaking())
            return;

        if (signBlock.getSide(Side.FRONT).lines().get(3).equals(SignTypes.ART_MAP_LINE))
            return;

        Material signMaterial = signBlock.getType();
        Vector signDirection = SignRotations.get(SignTypes.getSignFace(signBlock.getBlockData()));
        if (isWallSign(signMaterial))
            signDirection = signDirection.clone().multiply(-1);
        Vector playerDirection = new Vector(Math.sin(-Math.toRadians(player.getYaw())), signDirection.getY(), Math.cos(Math.toRadians(player.getYaw())));
        Side side = getClickedSide(signDirection, playerDirection);

        Location blockLocation = signBlock.getLocation();
        Location displayLocation = blockLocation.add(0.5F, 0.5F, 0.5F);
        displayLocation.setDirection(side.equals(Side.FRONT) ? signDirection.clone().multiply(-1) : signDirection);
        switch(event.getAction()) {
            case RIGHT_CLICK_BLOCK: {
                if (ItemDisplayUtils.getItemDisplayOnSign(displayLocation, side) != null){
                    event.setCancelled(true);
                    return;
                }

                ItemStack item = event.getItem();
                if (item == null || item.isEmpty())
                    return;

                if (!ProtectionPlugins.canInteractWithSign(player, blockLocation)){
                    Delay.run(() -> player.sendMessage(Text.value("warning.you_cant_use_that_here")), player, "cant_use_this_here", 20);
                    event.setCancelled(true);
                    return;
                }

                if (isOccupiedByText(signBlock.getSide(side).lines())){
                    if (isDye(item.getType()))
                        return;
                    Delay.run(() -> player.sendMessage(Text.value("warning.you_cant_place_item_because_sign_has_text")), player, "cant_use_this_here", 20);
                    event.setCancelled(true);
                    return;
                }

                if (event.getHand() == null)
                    return;

                if (Config.PLAYER_NEED_TO_HAVE_PERMISSION_TO_USE_SIGNS)
                    if (!player.hasPermission(Config.PERMISSION_CAN_USE_SIGNS))
                        return;


                ItemStack placedItem = item.clone();
                placedItem.setAmount(1);
                player.getInventory().getItem(event.getHand()).subtract();

                ItemDisplay itemDisplay = (ItemDisplay) displayLocation.getWorld().spawnEntity(displayLocation, EntityType.ITEM_DISPLAY);
                itemDisplay.getPersistentDataContainer().set(NamespacedKeyUtils.forItemOnSign(), PersistentDataType.STRING, side.name());
                itemDisplay.setTransformation(SignTypes.getType(signMaterial, side).getTransformation(placedItem.getType()));
                itemDisplay.setItemStack(placedItem);

                SoundUtils.playPasteItemOnSign(displayLocation);

                event.setCancelled(true);

            } break;
            case LEFT_CLICK_BLOCK: {
                ItemDisplay display = ItemDisplayUtils.getItemDisplayOnSign(displayLocation, side);
                if (display == null)
                    return;

                if (!ProtectionPlugins.canInteractWithSign(player, blockLocation)){
                    Delay.run(() -> player.sendMessage(Text.value("warning.you_cant_use_that_here")), player, "cant_use_this_here", 20);
                    event.setCancelled(true);
                    return;
                }

                display.remove();
                if (display.getItemStack() != null)
                    signBlock.getWorld().dropItemNaturally(InteractiveSigns.getInstance().getServer().getMinecraftVersion().equals("1.21.4") ? signBlock.getLocation().add(0.5, 0.5, 0.5) : signBlock.getLocation(), display.getItemStack());

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
    private static boolean isWallSign(@NotNull Material material){
        return MaterialSetTag.WALL_HANGING_SIGNS.isTagged(material) || MaterialSetTag.WALL_SIGNS.isTagged(material);
    }

    private static boolean isOccupiedByText(@NotNull List<Component> lines){
        for (Component text : lines)
            if (!text.equals(Component.empty()))
                return true;
        return false;
    }
    private static boolean isDye(@NotNull Material material){
        return MaterialTags.DYES.isTagged(material)
                || material.equals(Material.GLOW_INK_SAC)
                || material.equals(Material.INK_SAC);
    }

}
