package me.vovari2.interactivesigns.sign.transformations;

import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class VectorTransformation implements SignTransformation {
    public final static AxisAngle4f ZERO_ANGLE = new AxisAngle4f(0,0,0,0);

    final Vector3f translation;
    final Vector3f scale;

    public VectorTransformation(Vector3f translation, Vector3f scale) {
        this.translation = translation;
        this.scale = scale;
    }
    @Override
    public Transformation getTransformation() {return new Transformation(translation, ZERO_ANGLE, scale, ZERO_ANGLE);}
}
