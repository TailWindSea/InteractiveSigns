package me.vovari2.interactivesigns.listeners;

import com.destroystokyo.paper.MaterialSetTag;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.eventbus.Subscribe;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import me.vovari2.interactivesigns.Console;
import me.vovari2.interactivesigns.utils.ItemDisplayUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class WorldEditListener {

    @Subscribe
    public void onBlockDestroy(EditSessionEvent event){
        Console.warn("work1");
        event.setExtent(new SignExtent(event.getExtent(), BukkitAdapter.adapt(event.getWorld())));
    }

    private static class SignExtent extends AbstractDelegateExtent {
        private final World world;
        public SignExtent(Extent extent, World world) {
            super(extent);
            this.world = world;
        }

        public <B extends BlockStateHolder<B>> int setBlocks(final Region region, final B block) throws MaxChangedBlocksException {
            for (int x = region.getMinimumPoint().x(); x <= region.getMaximumPoint().x(); x++)
                for (int y = region.getMinimumPoint().y(); y <= region.getMaximumPoint().y(); y++)
                    for (int z = region.getMinimumPoint().z(); z <= region.getMaximumPoint().z(); z++){
                        Console.warn("-11 " + x + " " + y + " " + z);
                        ItemDisplayUtils.removeItemDisplayFromSign(new Location(world, x, y, z));
                    }
            return super.setBlocks(region, block);
        }
        public int setBlocks(final Region region, final Pattern pattern) throws MaxChangedBlocksException {
            for (int x = region.getMinimumPoint().x(); x <= region.getMaximumPoint().x(); x++)
                for (int y = region.getMinimumPoint().y(); y <= region.getMaximumPoint().y(); y++)
                    for (int z = region.getMinimumPoint().z(); z <= region.getMaximumPoint().z(); z++){
                        Console.warn("-12 " + x + " " + y + " " + z);
                        ItemDisplayUtils.removeItemDisplayFromSign(new Location(world, x, y, z));
                    }
            return super.setBlocks(region, pattern);
        }
        public int setBlocks(final Set<BlockVector3> vset, final Pattern pattern) {
            for (BlockVector3 pos : vset) {
                Console.warn("-13 " + pos);
                ItemDisplayUtils.removeItemDisplayFromSign(new Location(world, pos.x(), pos.y(), pos.z()));
            }
            return super.setBlocks(vset, pattern);
        }

        public <B extends BlockStateHolder<B>> int replaceBlocks(final Region region, final Set<BaseBlock> filter, final B replacement) throws MaxChangedBlocksException {
            if(containsSign(filter))
                for (int x = region.getMinimumPoint().x(); x <= region.getMaximumPoint().x(); x++)
                    for (int y = region.getMinimumPoint().y(); y <= region.getMaximumPoint().y(); y++)
                        for (int z = region.getMinimumPoint().z(); z <= region.getMaximumPoint().z(); z++)
                            ItemDisplayUtils.removeItemDisplayFromSign(new Location(world, x, y, z));
            return super.replaceBlocks(region, filter, replacement);
        }
        public int replaceBlocks(final Region region, final Set<BaseBlock> filter, final Pattern pattern) throws MaxChangedBlocksException {
            if(containsSign(filter))
                for (int x = region.getMinimumPoint().x(); x <= region.getMaximumPoint().x(); x++)
                    for (int y = region.getMinimumPoint().y(); y <= region.getMaximumPoint().y(); y++)
                        for (int z = region.getMinimumPoint().z(); z <= region.getMaximumPoint().z(); z++)
                            ItemDisplayUtils.removeItemDisplayFromSign(new Location(world, x, y, z));
            return super.replaceBlocks(region, filter, pattern);
        }
        public int replaceBlocks(final Region region, final Mask mask, final Pattern pattern) throws MaxChangedBlocksException {
            for (int x = region.getMinimumPoint().x(); x <= region.getMaximumPoint().x(); x++)
                for (int y = region.getMinimumPoint().y(); y <= region.getMaximumPoint().y(); y++)
                    for (int z = region.getMinimumPoint().z(); z <= region.getMaximumPoint().z(); z++){
                        if (mask.test(BlockVector3.at(x,y,z)))
                            ItemDisplayUtils.removeItemDisplayFromSign(new Location(world, x,y,z));
                    }
            return super.replaceBlocks(region, mask, pattern);
        }

        public <T extends BlockStateHolder<T>> boolean setBlock(BlockVector3 position, T block) throws WorldEditException {
            Console.warn("-21 ");
            ItemDisplayUtils.removeItemDisplayFromSign(new Location(world, position.x(), position.y(), position.z()));
            return super.setBlock(position, block);
        }
        public <T extends BlockStateHolder<T>> boolean setBlock(int x, int y, int z, T block) throws WorldEditException {
            Console.warn("-22 ");
            ItemDisplayUtils.removeItemDisplayFromSign(new Location(world, x, y, z));
            return super.setBlock(x,y,z, block);
        }

        private boolean containsSign(@NotNull Set<BaseBlock> blocks){
            return blocks.stream().anyMatch(this::isSign);
        }
        private boolean isSign(@NotNull BaseBlock block){
            return MaterialSetTag.SIGNS.isTagged(BukkitAdapter.adapt(block).getMaterial());
        }
    }
}
