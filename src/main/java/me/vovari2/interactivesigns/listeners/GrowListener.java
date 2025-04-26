package me.vovari2.interactivesigns.listeners;

import com.destroystokyo.paper.MaterialSetTag;
import me.vovari2.interactivesigns.utils.ItemDisplayUtils;
import me.vovari2.interactivesigns.utils.VersionUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.sign.Side;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

public class GrowListener implements Listener {
    @EventHandler
    public void onStructureGrowEvent(StructureGrowEvent event){
        for (BlockState state : event.getBlocks()){
            Block block = state.getWorld().getBlockAt(state.getLocation());
            if (!MaterialSetTag.ALL_SIGNS.isTagged(block.getType()))
                continue;

            Location location = VersionUtils.getBlockCenter(block.getLocation());

            ItemDisplayUtils.convertFromOldDisplay(ItemDisplayUtils.getItemDisplayOnSignOld(location));
            ItemDisplayUtils.convertFromOldDisplay(ItemDisplayUtils.getItemDisplayOnSignOld(location));

            ItemDisplayUtils.dropItemFromDisplay(location, Side.FRONT);
            ItemDisplayUtils.dropItemFromDisplay(location, Side.BACK);
        }
    }
}
