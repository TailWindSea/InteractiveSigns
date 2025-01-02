package me.vovari2.interactivesigns.listeners;

import com.destroystokyo.paper.MaterialSetTag;
import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import me.vovari2.interactivesigns.utils.ItemDisplayUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Collection;

public class BreakListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakBlockByWorld(BlockDestroyEvent event){
        if(event.isCancelled())
            return;

        Block block = event.getBlock();
        if (!MaterialSetTag.ALL_SIGNS.isTagged(block.getType()))
            return;

        Collection<ItemDisplay> list = ItemDisplayUtils.getItemDisplaysOnSign(block.getLocation().add(0.5,0.5,0.5));
        if (list.isEmpty())
            return;

        for (ItemDisplay display : list){
            display.remove();
            if (display.getItemStack() != null)
                block.getWorld().dropItemNaturally(block.getLocation(), display.getItemStack());
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakBlockByPlayer(BlockBreakEvent event){
        if(event.isCancelled())
            return;

        Block block = event.getBlock();
        if (!MaterialSetTag.ALL_SIGNS.isTagged(block.getType()))
            return;

        Collection<ItemDisplay> list = ItemDisplayUtils.getItemDisplaysOnSign(block.getLocation().add(0.5,0.5,0.5));
        if (list.isEmpty())
            return;

        for (ItemDisplay display : list){
            display.remove();
            if (display.getItemStack() != null)
                block.getWorld().dropItemNaturally(block.getLocation(), display.getItemStack());
        }
    }
}
