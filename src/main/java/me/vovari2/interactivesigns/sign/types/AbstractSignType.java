package me.vovari2.interactivesigns.sign.types;

import me.vovari2.interactivesigns.sign.MaterialType;
import me.vovari2.interactivesigns.sign.transformations.VectorTransformation;
import me.vovari2.interactivesigns.sign.SignType;
import org.bukkit.block.sign.Side;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class AbstractSignType implements SignType {
    final HashMap<MaterialType, VectorTransformation> front;
    final HashMap<MaterialType, VectorTransformation> back;
    public AbstractSignType() {
        front = new HashMap<>();
        back = new HashMap<>();
    }

    @Override
    public @NotNull VectorTransformation getSignTransformation(Side side, MaterialType type) {
        return (Side.FRONT.equals(side) ? front : back).get(type);
    }
}
