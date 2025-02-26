package me.vovari2.interactivesigns.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;

public class ArtMapUtils {
    private static final Component ART_MAP_LINE = TextUtils.toComponent("*{=}*");
    public static boolean isCanvas(Sign signBlock){
        return signBlock.getSide(Side.FRONT).lines().get(3).equals(ART_MAP_LINE);
    }
}
