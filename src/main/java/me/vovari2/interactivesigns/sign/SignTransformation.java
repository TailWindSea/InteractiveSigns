package me.vovari2.interactivesigns.sign;

import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class SignTransformation {
    public static AxisAngle4f ZERO_ANGLE = new AxisAngle4f(0,0,0,0);

    private final Vector3f translation;
    private final Vector3f scale;

    public SignTransformation(Vector3f translation, Vector3f scale) {
        this.translation = translation;
        this.scale = scale;
    }

    public Transformation getTransformation(){
        return new Transformation(translation, ZERO_ANGLE, scale, ZERO_ANGLE);
    }
}
