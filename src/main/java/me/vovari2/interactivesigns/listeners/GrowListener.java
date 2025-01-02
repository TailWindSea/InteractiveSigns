package me.vovari2.interactivesigns.listeners;

import com.destroystokyo.paper.MaterialSetTag;
import me.vovari2.interactivesigns.utils.ItemDisplayUtils;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.Collection;

public class GrowListener implements Listener {
    @EventHandler
    public void onStructureGrowEvent(StructureGrowEvent event){
        for (BlockState state : event.getBlocks()){
            Block block = state.getWorld().getBlockAt(state.getLocation());
            if (!MaterialSetTag.ALL_SIGNS.isTagged(block.getType()))
                continue;

            Collection<ItemDisplay> itemDisplays = ItemDisplayUtils.getItemDisplaysOnSign(block.getLocation().add(0.5F, 0.5F, 0.5F));
            if (itemDisplays.isEmpty())
                continue;

            for (ItemDisplay display : itemDisplays){
                display.remove();
                if (display.getItemStack() != null)
                    block.getWorld().dropItemNaturally(block.getLocation(), display.getItemStack());
            }
        }
    }
}
