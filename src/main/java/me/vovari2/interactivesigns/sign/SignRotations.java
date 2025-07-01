package me.vovari2.interactivesigns.sign;

import com.google.common.collect.ImmutableMap;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.Map;

public class SignRotations {
    private static final Map<BlockFace, Vector> rotations = ImmutableMap.<BlockFace, Vector>builder()
            .put(BlockFace.NORTH, vectorFromAngle(0))
            .put(BlockFace.NORTH_NORTH_EAST, vectorFromAngle(22.5))
            .put(BlockFace.NORTH_EAST, vectorFromAngle(45))
            .put(BlockFace.EAST_NORTH_EAST, vectorFromAngle(67.5))
            .put(BlockFace.EAST, vectorFromAngle(90))
            .put(BlockFace.EAST_SOUTH_EAST, vectorFromAngle(112.5))
            .put(BlockFace.SOUTH_EAST, vectorFromAngle(135))
            .put(BlockFace.SOUTH_SOUTH_EAST, vectorFromAngle(157.5))
            .put(BlockFace.SOUTH, vectorFromAngle(180))
            .put(BlockFace.SOUTH_SOUTH_WEST, vectorFromAngle(-157.5))
            .put(BlockFace.SOUTH_WEST, vectorFromAngle(-135))
            .put(BlockFace.WEST_SOUTH_WEST, vectorFromAngle(-112.5))
            .put(BlockFace.WEST, vectorFromAngle(-90))
            .put(BlockFace.WEST_NORTH_WEST, vectorFromAngle(-67.5))
            .put(BlockFace.NORTH_WEST, vectorFromAngle(-45))
            .put(BlockFace.NORTH_NORTH_WEST, vectorFromAngle(-22.5))
            .build();
    private static Vector vectorFromAngle(double angle){
        return new Vector(Math.sin(-Math.toRadians(angle)), 0, Math.cos(Math.toRadians(angle)));
    }

    public static Vector get(BlockFace face){
        return rotations.get(face);
    }
}
