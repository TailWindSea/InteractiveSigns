package me.vovari2.interactivesigns.sign.types;

import me.vovari2.interactivesigns.sign.MaterialType;
import me.vovari2.interactivesigns.sign.transformations.FullTransformation;
import me.vovari2.interactivesigns.sign.transformations.VectorTransformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class WallSign3D extends AbstractSignType {
    public WallSign3D(){
        super();
        front.put(MaterialType.DRAGON_HEAD, new VectorTransformation(
                new Vector3f(-0,0.08F,0.389F),
                new Vector3f(0.4F,0.4F,0.2F)));
        front.put(MaterialType.PLAYER_HEAD, new VectorTransformation(
                new Vector3f(-0,0.21F,0.389F),
                new Vector3f(0.7F,0.7F,0.35F)));
        front.put(MaterialType.TALL_ITEM, new VectorTransformation(
                new Vector3f(-0,0.01F,0.389F),
                new Vector3f(0.31F,0.31F,0.16F)));
        front.put(MaterialType.BANNERS, new VectorTransformation(
                new Vector3f(0,-0.07F, 0.389F),
                new Vector3f(0.216F,0.216F,0.216F)));
        front.put(MaterialType.BEDS, new FullTransformation(
                new Vector3f(-0.14F,0.048F, 0.332F),
                new AxisAngle4f(1.572F, -1,0,0),
                new Vector3f(0.28F,0.14F,0.28F),
                new AxisAngle4f(1.572F, 0, 1, 0)));
        front.put(MaterialType.ITEM, new VectorTransformation(
                new Vector3f(-0,0.03F,0.384F),
                new Vector3f(0.4F,0.4F,0.4F)));
        front.put(MaterialType.BLOCK, new VectorTransformation(
                new Vector3f(-0,0.03F,0.389F),
                new Vector3f(0.35F,0.35F,0.14F)));

        back.put(MaterialType.DRAGON_HEAD, new VectorTransformation(
                new Vector3f(0,0.08F,-0.483F),
                new Vector3f(0.4F,0.4F,0.2F)));
        back.put(MaterialType.PLAYER_HEAD, new VectorTransformation(
                new Vector3f(0,0.21F,-0.483F),
                new Vector3f(0.7F,0.7F,0.35F)));
        back.put(MaterialType.TALL_ITEM, new VectorTransformation(
                new Vector3f(0,0.01F,-0.483F),
                new Vector3f(0.31F,0.31F,0.16F)));
        back.put(MaterialType.BANNERS, new VectorTransformation(
                new Vector3f(0,-0.07F, -0.483F),
                new Vector3f(0.216F,0.216F,0.216F)));
        back.put(MaterialType.BEDS, new FullTransformation(
                new Vector3f(-0.14F,0.048F, -0.54F),
                new AxisAngle4f(1.572F, -1,0,0),
                new Vector3f(0.28F,0.14F,0.28F),
                new AxisAngle4f(1.572F, 0, 1, 0)));
        back.put(MaterialType.ITEM, new VectorTransformation(
                new Vector3f(0,0.03F,-0.488F),
                new Vector3f(0.4F,0.4F,0.4F)));
        back.put(MaterialType.BLOCK, new VectorTransformation(
                new Vector3f(0,0.03F,-0.483F),
                new Vector3f(0.35F,0.35F,0.14F)));
    }
}