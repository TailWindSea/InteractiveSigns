package me.vovari2.interactivesigns.listeners;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.util.eventbus.Subscribe;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import me.vovari2.interactivesigns.Console;
import me.vovari2.interactivesigns.utils.ItemDisplayUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public class WorldEditListener {

    @Subscribe
    public void onBlockDestroy(EditSessionEvent event){
        if (event.getWorld() == null)
            return;

        event.setExtent(new SignExtent(event.getExtent(), BukkitAdapter.adapt(event.getWorld())));
    }

    private static class SignExtent extends AbstractDelegateExtent {
        private final World world;
        public SignExtent(@NotNull Extent extent, @NotNull World world) {
            super(extent);
            this.world = world;
        }

        public <T extends BlockStateHolder<T>> boolean setBlock(BlockVector3 pos, T block) throws WorldEditException {
            ItemDisplayUtils.removeItemDisplayFromSign(BukkitAdapter.adapt(world, pos).add(0.5,0.5,0.5));
            return super.setBlock(pos, block);
        }
    }
}
