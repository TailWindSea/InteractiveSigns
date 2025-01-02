package me.vovari2.interactivesigns.sign;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class SignRotations {
    private static HashMap<BlockFace, Vector> rotations;
    public static Vector get(BlockFace face){
        return rotations.get(face);
    }


    public static void initialize(){
        rotations = new HashMap<>();
        rotations.put(BlockFace.NORTH, vectorFromAngle(0));
        rotations.put(BlockFace.NORTH_NORTH_EAST, vectorFromAngle(22.5));
        rotations.put(BlockFace.NORTH_EAST, vectorFromAngle(45));
        rotations.put(BlockFace.EAST_NORTH_EAST, vectorFromAngle(67.5));
        rotations.put(BlockFace.EAST, vectorFromAngle(90));
        rotations.put(BlockFace.EAST_SOUTH_EAST, vectorFromAngle(112.5));
        rotations.put(BlockFace.SOUTH_EAST, vectorFromAngle(135));
        rotations.put(BlockFace.SOUTH_SOUTH_EAST, vectorFromAngle(157.5));
        rotations.put(BlockFace.SOUTH, vectorFromAngle(180));
        rotations.put(BlockFace.SOUTH_SOUTH_WEST, vectorFromAngle(-157.5));
        rotations.put(BlockFace.SOUTH_WEST, vectorFromAngle(-135));
        rotations.put(BlockFace.WEST_SOUTH_WEST, vectorFromAngle(-112.5));
        rotations.put(BlockFace.WEST, vectorFromAngle(-90));
        rotations.put(BlockFace.WEST_NORTH_WEST, vectorFromAngle(-67.5));
        rotations.put(BlockFace.NORTH_WEST, vectorFromAngle(-45));
        rotations.put(BlockFace.NORTH_NORTH_WEST, vectorFromAngle(-22.5));
    }
    private static Vector vectorFromAngle(double angle){
        return new Vector(Math.sin(-Math.toRadians(angle)), 0, Math.cos(Math.toRadians(angle)));
    }
}
