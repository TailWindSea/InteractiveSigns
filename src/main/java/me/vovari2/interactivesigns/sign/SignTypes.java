package me.vovari2.interactivesigns.sign;

import com.destroystokyo.paper.MaterialSetTag;
import me.vovari2.interactivesigns.loaders.types.ConfigurationLoader;
import me.vovari2.interactivesigns.sign.types.*;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.sign.Side;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public enum SignTypes {
    STANDING_SIGN,
    WALL_SIGN,
    HANGING_SIGN;

    private static HashMap<SignTypes, SignType> types;
    public static void initialize(){
        types = new HashMap<>();

        boolean isThreeDimensional = ConfigurationLoader.ENABLE_ITEMS_VOLUME;
        types.put(STANDING_SIGN, isThreeDimensional ? new StandingSign3D() : new StandingSign2D());
        types.put(WALL_SIGN, isThreeDimensional ? new WallSign3D() : new WallSign2D());
        types.put(HANGING_SIGN, isThreeDimensional ? new HangingSign3D() : new HangingSign2D());
    }

    public static @NotNull Transformation getTransformation(Side side, Material signMaterial, Material placedMaterial){
        return types.get(getType(signMaterial))
                .getSignTransformation(side, MaterialType.getType(placedMaterial))
                .getTransformation();
    }
    private static @NotNull SignTypes getType(Material material){
        if (MaterialSetTag.WALL_SIGNS.isTagged(material))
            return WALL_SIGN;
        if (MaterialSetTag.WALL_HANGING_SIGNS.isTagged(material) || MaterialSetTag.CEILING_HANGING_SIGNS.isTagged(material))
            return HANGING_SIGN;
        return STANDING_SIGN;
    }

    public static @NotNull BlockFace getSignFace(@NotNull BlockData blockData){
        return isWall(blockData.getMaterial()) ? ((Directional) blockData).getFacing() : ((Rotatable) blockData).getRotation().getOppositeFace();
    }
    public static boolean isWall(@NotNull Material material){
        return MaterialSetTag.WALL_HANGING_SIGNS.isTagged(material) || MaterialSetTag.WALL_SIGNS.isTagged(material);
    }
}
