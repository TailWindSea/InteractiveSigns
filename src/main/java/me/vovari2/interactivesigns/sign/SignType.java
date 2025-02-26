package me.vovari2.interactivesigns.sign;


import me.vovari2.interactivesigns.sign.transformations.VectorTransformation;
import org.bukkit.block.sign.Side;

public interface SignType {
    VectorTransformation getSignTransformation(Side side, MaterialType type);
}
