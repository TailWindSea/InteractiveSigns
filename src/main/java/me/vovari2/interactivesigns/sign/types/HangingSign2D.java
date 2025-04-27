package me.vovari2.interactivesigns.sign.types;

import me.vovari2.interactivesigns.sign.MaterialType;
import me.vovari2.interactivesigns.sign.transformations.FullTransformation;
import me.vovari2.interactivesigns.sign.transformations.VectorTransformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class HangingSign2D extends AbstractSignType {
    public HangingSign2D(){
        super();
        front.put(MaterialType.DRAGON_HEAD, new VectorTransformation(
                new Vector3f(0,-0.14F, -0.06451F),
                new Vector3f(0.5F,0.5F,0.001F)));
        front.put(MaterialType.PLAYER_HEAD, new VectorTransformation(
                new Vector3f(0,0F, -0.06451F),
                new Vector3f(0.8F,0.8F,0.001F)));
        front.put(MaterialType.TALL_ITEM, new VectorTransformation(
                new Vector3f(0,-0.21F, -0.06451F),
                new Vector3f(0.4F,0.4F,0.001F)));
        front.put(MaterialType.BANNERS, new VectorTransformation(
                new Vector3f(0,-0.31F, -0.06451F),
                new Vector3f(0.27F,0.27F,0.001F)));
        front.put(MaterialType.BEDS, new FullTransformation(
                new Vector3f(-0.177F,-0.18F, -0.06451F),
                new AxisAngle4f(1.572F, -1,0,0),
                new Vector3f(0.35F,0.001F,0.35F),
                new AxisAngle4f(1.572F, 0, 1, 0)));
        front.put(MaterialType.SHIELD, new FullTransformation(
                new Vector3f(-0.175F,0,-0.06451F),
                new AxisAngle4f(0, -1,0,0),
                new Vector3f(0.35F,0.35F,0.001F),
                new AxisAngle4f(0, 0, 1, 0)));
        front.put(MaterialType.TRIDENT, new FullTransformation(
                new Vector3f(-0.43F,-0.3F,-0.06451F),
                new AxisAngle4f(1.572F, 0,0,1),
                new Vector3f(0.35F,0.35F,0.001F),
                new AxisAngle4f(0, 0, 1, 0)));
        front.put(MaterialType.ITEM, new FullTransformation(
                new Vector3f(0,-0.18F, -0.06451F),
                new AxisAngle4f(0, -1,0,0),
                new Vector3f(0.5F,0.5F,0.001F),
                new AxisAngle4f(3.144F, 0, 1, 0)));
        front.put(MaterialType.BLOCK, new VectorTransformation(
                new Vector3f(0,-0.18F, -0.06451F),
                new Vector3f(0.5F,0.5F,0.001F)));

        back.putAll(front);
    }
}