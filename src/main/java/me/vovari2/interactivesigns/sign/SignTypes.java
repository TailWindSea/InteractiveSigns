package me.vovari2.interactivesigns.sign;

import com.destroystokyo.paper.MaterialSetTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.block.sign.Side;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;
public enum SignTypes {
    SIGN(
            List.of(new SignTransformation( // Item
                            new Vector3f(0,0.34F, -0.044F),
                            new Vector3f(0.4F,0.4F,0.0005F)),
                    new SignTransformation( // Player head
                            new Vector3f(0,0.52F, -0.044F),
                            new Vector3f(0.7F,0.7F,0.0005F)),
                    new SignTransformation( // Dragon head
                            new Vector3f(0,0.38F, -0.044F),
                            new Vector3f(0.4F,0.4F,0.0005F)),
                    new SignTransformation( // Tall items
                            new Vector3f(0,0.32F, -0.044F),
                            new Vector3f(0.31F,0.31F,0.0005F)))),

    WALL_SIGN_BACK(
            List.of(new SignTransformation( // Item
                            new Vector3f(0,0.03F,-0.481F),
                            new Vector3f(0.4F,0.4F,0.0005F)),
                    new SignTransformation( // Player head
                            new Vector3f(0,0.2F,-0.481F),
                            new Vector3f(0.7F,0.7F,0.0005F)),
                    new SignTransformation( // Dragon head
                            new Vector3f(0,0.07F,-0.481F),
                            new Vector3f(0.4F,0.4F,0.0005F)),
                    new SignTransformation( // Tall items
                            new Vector3f(0,0.01F,-0.481F),
                            new Vector3f(0.31F,0.31F,0.0005F)))),
    WALL_SIGN_FRONT(
            List.of(new SignTransformation( // Item
                            new Vector3f(-0,0.03F,0.391F),
                            new Vector3f(0.4F,0.4F,0.0005F)),
                    new SignTransformation( // Player head
                            new Vector3f(-0,0.2F,0.391F),
                            new Vector3f(0.7F,0.7F,0.0005F)),
                    new SignTransformation( // Dragon head
                            new Vector3f(-0,0.07F,0.391F),
                            new Vector3f(0.4F,0.4F,0.0005F)),
                    new SignTransformation( // Tall items
                            new Vector3f(-0,0.01F,0.391F),
                            new Vector3f(0.31F,0.31F,0.0005F)))),
    HANGING_SIGN(
            List.of(new SignTransformation( // Item
                            new Vector3f(0,-0.18F, -0.06251F),
                            new Vector3f(0.5F,0.5F,0.0005F)),
                    new SignTransformation( // Player head
                            new Vector3f(0,0F, -0.06251F),
                            new Vector3f(0.8F,0.8F,0.0005F)),
                    new SignTransformation( // Dragon head
                            new Vector3f(0,-0.14F, -0.06251F),
                            new Vector3f(0.5F,0.5F,0.0005F)),
                    new SignTransformation( // Tall items
                            new Vector3f(0,-0.21F, -0.06251F),
                            new Vector3f(0.4F,0.4F,0.0005F)))),
    WALL_HANGING_SIGN(
            List.of(new SignTransformation( // Item
                            new Vector3f(0,-0.18F, -0.06251F),
                            new Vector3f(0.5F,0.5F,0.0005F)),
                    new SignTransformation( // Player head
                            new Vector3f(0,0F, -0.06251F),
                            new Vector3f(0.8F,0.8F,0.0005F)),
                    new SignTransformation( // Dragon head
                            new Vector3f(0,-0.14F, -0.06251F),
                            new Vector3f(0.5F,0.5F,0.0005F)),
                    new SignTransformation( // Tall items
                            new Vector3f(0,-0.21F, -0.06251F),
                            new Vector3f(0.4F,0.4F,0.0005F))));

    public static final Component ART_MAP_LINE = MiniMessage.miniMessage().deserialize("*{=}*");

    private final List<SignTransformation> transformations;
    SignTypes(List<SignTransformation> transformations){
        this.transformations = transformations;
    }
    public @NotNull Transformation getTransformation(Material material){
        if (isHead(material))
            return transformations.get(1).getTransformation();
        if (isDragonHead(material))
            return transformations.get(2).getTransformation();
        if (isTallItem(material))
            return transformations.get(3).getTransformation();
        return transformations.get(0).getTransformation();
    }
    private boolean isHead(Material material){
        return material.equals(Material.PLAYER_HEAD)
                || material.equals(Material.CREEPER_HEAD)
                || material.equals(Material.WITHER_SKELETON_SKULL)
                || material.equals(Material.SKELETON_SKULL)
                || material.equals(Material.ZOMBIE_HEAD)
                || material.equals(Material.PIGLIN_HEAD);
    }
    private boolean isDragonHead(Material material){
        return material.equals(Material.DRAGON_HEAD);
    }
    private boolean isTallItem(Material material){
        return material.equals(Material.DECORATED_POT)
                || material.equals(Material.LECTERN);
    }




    public static @NotNull SignTypes getType(@NotNull Material material, Side side){
        if (MaterialSetTag.WALL_SIGNS.isTagged(material))
            return side.equals(Side.FRONT) ? WALL_SIGN_FRONT : WALL_SIGN_BACK;
        if (MaterialSetTag.WALL_HANGING_SIGNS.isTagged(material))
            return SignTypes.WALL_HANGING_SIGN;
        if (MaterialSetTag.CEILING_HANGING_SIGNS.isTagged(material))
            return SignTypes.HANGING_SIGN;
        return SignTypes.SIGN;
    }
    public static @NotNull BlockFace getSignFace(@NotNull BlockData blockData){
        return isWall(blockData.getMaterial()) ? ((Directional) blockData).getFacing() : ((Rotatable) blockData).getRotation().getOppositeFace();
    }
    public static boolean isWall(@NotNull Material material){
        return MaterialSetTag.WALL_HANGING_SIGNS.isTagged(material) || MaterialSetTag.WALL_SIGNS.isTagged(material);
    }
}
