package me.vovari2.interactivesigns.sign.transformations;

import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class FullTransformation extends VectorTransformation {
    private final AxisAngle4f leftRotation;
    private final AxisAngle4f rightRotation;
    public FullTransformation(Vector3f translation, AxisAngle4f leftRotation, Vector3f scale, AxisAngle4f rightRotation) {
        super(translation, scale);
        this.leftRotation = leftRotation != null ? leftRotation : ZERO_ANGLE;
        this.rightRotation = rightRotation != null ? rightRotation : ZERO_ANGLE;
    }
    @Override
    public Transformation getTransformation() {return new Transformation(super.translation, leftRotation, super.scale, rightRotation);}
}
