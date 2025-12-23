package me.vovari2.interactivesigns.listeners;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.eventbus.Subscribe;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import me.vovari2.interactivesigns.Scheduler;
import me.vovari2.interactivesigns.utils.NamespacedKeyUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

public class WorldEditListener {

    @Subscribe
    public void onEditSessionChange(EditSessionEvent event) {
        if (event.getWorld() == null
                || !event.getStage().equals(EditSession.Stage.BEFORE_HISTORY))
            return;

        event.setExtent(new SignExtent(event.getExtent(), BukkitAdapter.adapt(event.getWorld())));
    }

    private static class SignExtent extends AbstractDelegateExtent {
        private final World world;
        public SignExtent(@NotNull Extent extent, @NotNull World world) {
            super(extent);
            this.world = world;
        }
        public void removeItemDisplayFromSign(@NotNull Location min, @NotNull Location max) {
            Collection<Entity> list = world.getNearbyEntities(new BoundingBox(min.x(), min.y(), min.z(), max.x(), max.y(), max.z()), entity -> {
                PersistentDataContainer container = entity.getPersistentDataContainer();
                return container.has(NamespacedKeyUtils.forItemOnSignOld())
                        || container.has(NamespacedKeyUtils.forItemOnSign(Side.FRONT.name()))
                        || container.has(NamespacedKeyUtils.forItemOnSign(Side.BACK.name()));
            });

            if (list.isEmpty())
                return;

            for (Entity entity : list){
                if (entity instanceof ItemDisplay display)
                    world.dropItemNaturally(display.getLocation(), display.getItemStack());
                entity.remove();
            }
        }
        public void removeItemDisplayFromSign(@NotNull Location loc) {
            Collection<ItemDisplay> list = world.getNearbyEntitiesByType(
                    ItemDisplay.class,
                    loc,
                    0.1,
                    display -> {
                        PersistentDataContainer container = display.getPersistentDataContainer();
                        return container.has(NamespacedKeyUtils.forItemOnSignOld()) || container.has(NamespacedKeyUtils.forItemOnSign(Side.FRONT.name())) || container.has(NamespacedKeyUtils.forItemOnSign(Side.BACK.name()));
                    });

            if (list.isEmpty())
                return;

            for (ItemDisplay display : list){
                world.dropItemNaturally(display.getLocation(), display.getItemStack());
                display.remove();
            }
        }

        public <T extends BlockStateHolder<T>> boolean setBlock(BlockVector3 pos, T block) throws WorldEditException {
            final Location loc = BukkitAdapter.adapt(world, pos).add(0.5, 0.5, 0.5);
            Scheduler.waitInLocation(loc, () -> removeItemDisplayFromSign(loc));
            return super.setBlock(pos, block);
        }
        public <T extends BlockStateHolder<T>> boolean setBlock(int x, int y, int z, T block) throws WorldEditException {
            final Location loc = new Location(world, x + 0.5, y + 0.5, z + 0.5);
            Scheduler.waitInLocation(loc, ()  -> removeItemDisplayFromSign(loc));
            return super.setBlock(x, y, z, block);
        }

        public <B extends BlockStateHolder<B>> int setBlocks(final @NotNull Region region, final B block) throws MaxChangedBlocksException {
            region.forEach(pos -> {
                final Location min = BukkitAdapter.adapt(world, region.getMinimumPoint()),
                        max = BukkitAdapter.adapt(world, region.getMinimumPoint()).add(1,1,1);
                Scheduler.waitInLocation(min, () -> removeItemDisplayFromSign(min, max));
            });
            return super.setBlocks(region, block);
        }
        public int setBlocks(final @NotNull Region region, final Pattern pattern) throws MaxChangedBlocksException {
            region.forEach(pos -> {
                final Location min = BukkitAdapter.adapt(world, region.getMinimumPoint()),
                        max = BukkitAdapter.adapt(world, region.getMinimumPoint()).add(1,1,1);
                Scheduler.waitInLocation(min, () -> removeItemDisplayFromSign(min, max));
            });
            return super.setBlocks(region, pattern);
        }
        public int setBlocks(final @NotNull Set<BlockVector3> vset, final Pattern pattern) {
            vset.forEach(pos -> {
                final Location loc = BukkitAdapter.adapt(world, pos).add(0.5, 0.5, 0.5);
                Scheduler.waitInLocation(loc, () -> removeItemDisplayFromSign(loc));
            });
            return super.setBlocks(vset, pattern);
        }
    }
}
