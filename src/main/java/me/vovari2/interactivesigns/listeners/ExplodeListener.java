package me.vovari2.interactivesigns.listeners;

import com.destroystokyo.paper.MaterialSetTag;
import me.vovari2.interactivesigns.utils.ItemDisplayUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ExplodeListener  implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplodeBlocksByBlock(BlockExplodeEvent event){
        if (event.isCancelled())
            return;

        List<Block> blockList = event.blockList();
        if (blockList.isEmpty())
            return;

        Iterator<Block> iterator = blockList.iterator();
        while(iterator.hasNext()){
            Block block = iterator.next();
            if (!MaterialSetTag.ALL_SIGNS.isTagged(block.getType()))
                continue;

            Collection<ItemDisplay> itemDisplays = ItemDisplayUtils.getItemDisplaysOnSign(block.getLocation().add(0.5F, 0.5F, 0.5F));
            if (itemDisplays.isEmpty())
                continue;

            for (ItemDisplay display : itemDisplays){
                display.remove();
                block.getWorld().dropItemNaturally(block.getLocation(), display.getItemStack());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplodeBlocksByEntity(EntityExplodeEvent event){
        if (event.isCancelled())
            return;

        List<Block> blockList = event.blockList();
        if (blockList.isEmpty())
            return;

        Iterator<Block> iterator = blockList.iterator();
        while(iterator.hasNext()){
            Block block = iterator.next();
            if (!MaterialSetTag.ALL_SIGNS.isTagged(block.getType()))
                continue;

            Collection<ItemDisplay> itemDisplays = ItemDisplayUtils.getItemDisplaysOnSign(block.getLocation().add(0.5F, 0.5F, 0.5F));
            if (itemDisplays.isEmpty())
                continue;

            for (ItemDisplay display : itemDisplays){
                display.remove();
                block.getWorld().dropItemNaturally(block.getLocation(), display.getItemStack());
            }
        }
    }
}

