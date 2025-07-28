package me.vovari2.interactivesigns.listeners;

import com.destroystokyo.paper.MaterialSetTag;
import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.util.eventbus.Subscribe;
import me.vovari2.interactivesigns.utils.ItemDisplayUtils;
import me.vovari2.interactivesigns.utils.VersionUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.sign.Side;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BreakListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onBreakBlockByWorld(BlockDestroyEvent event){
        if(event.isCancelled())
            return;

        Block block = event.getBlock();
        if (!MaterialSetTag.ALL_SIGNS.isTagged(block.getType()))
            return;

        Location location = VersionUtils.getBlockCenter(block.getLocation());

        ItemDisplayUtils.convertFromOldDisplay(ItemDisplayUtils.getItemDisplayOnSignOld(location));
        ItemDisplayUtils.convertFromOldDisplay(ItemDisplayUtils.getItemDisplayOnSignOld(location));

        ItemDisplayUtils.dropItemFromDisplay(location, Side.FRONT);
        ItemDisplayUtils.dropItemFromDisplay(location, Side.BACK);
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void onBreakBlockByPlayer(BlockBreakEvent event){
        if(event.isCancelled())
            return;

        Block block = event.getBlock();
        if (!MaterialSetTag.ALL_SIGNS.isTagged(block.getType()))
            return;

        Location location = VersionUtils.getBlockCenter(block.getLocation());

        ItemDisplayUtils.convertFromOldDisplay(ItemDisplayUtils.getItemDisplayOnSignOld(location));
        ItemDisplayUtils.convertFromOldDisplay(ItemDisplayUtils.getItemDisplayOnSignOld(location));

        ItemDisplayUtils.dropItemFromDisplay(location, Side.FRONT);
        ItemDisplayUtils.dropItemFromDisplay(location, Side.BACK);
    }
}
