package me.vovari2.interactivesigns.sign.types;

import me.vovari2.interactivesigns.sign.MaterialType;
import me.vovari2.interactivesigns.sign.transformations.FullTransformation;
import me.vovari2.interactivesigns.sign.transformations.VectorTransformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class StandingSign2D extends AbstractSignType {
    public StandingSign2D(){
        super();
        front.put(MaterialType.DRAGON_HEAD, new VectorTransformation(
                new Vector3f(0,0.38F, -0.046F),
                new Vector3f(0.4F,0.4F,0.001F)));
        front.put(MaterialType.PLAYER_HEAD, new VectorTransformation(
                new Vector3f(0,0.52F, -0.046F),
                new Vector3f(0.7F,0.7F,0.001F)));
        front.put(MaterialType.TALL_ITEM, new VectorTransformation(
                new Vector3f(0,0.32F, -0.046F),
                new Vector3f(0.31F,0.31F,0.001F)));
        front.put(MaterialType.BANNERS, new VectorTransformation(
                new Vector3f(0,0.25F, -0.046F),
                new Vector3f(0.216F,0.216F,0.001F)));
        front.put(MaterialType.BEDS, new FullTransformation(
                new Vector3f(-0.14F,0.35F, -0.046F),
                new AxisAngle4f(1.572F, -1,0,0),
                new Vector3f(0.28F,0.001F,0.28F),
                new AxisAngle4f(1.572F, 0, 1, 0)));
        front.put(MaterialType.ITEM, new VectorTransformation(
                new Vector3f(0,0.34F, -0.046F),
                new Vector3f(0.4F,0.4F,0.001F)));
        front.put(MaterialType.BLOCK, new VectorTransformation(
                new Vector3f(0,0.34F, -0.046F),
                new Vector3f(0.4F,0.4F,0.001F)));

        back.putAll(front);
    }
}
