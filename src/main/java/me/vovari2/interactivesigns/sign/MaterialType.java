package me.vovari2.interactivesigns.sign;

import com.destroystokyo.paper.MaterialSetTag;
import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public enum MaterialType {
    DRAGON_HEAD(Material.DRAGON_HEAD::equals),
    PLAYER_HEAD(new PredicateList()
            .inc(Material.PLAYER_HEAD)
            .inc(Material.CREEPER_HEAD)
            .inc(Material.WITHER_SKELETON_SKULL)
            .inc(Material.SKELETON_SKULL)
            .inc(Material.ZOMBIE_HEAD)
            .inc(Material.PIGLIN_HEAD)),
    TALL_ITEM(new PredicateList()
            .inc(Material.DECORATED_POT)
            .inc(Material.LECTERN)),
    BANNERS(new PredicateList()
            .inc(MaterialSetTag.BANNERS)),
    BEDS(new PredicateList()
            .inc(MaterialSetTag.BEDS)),
    SHIELD(new PredicateList()
            .inc(Material.SHIELD)),
    TRIDENT(new PredicateList()
            .inc(Material.TRIDENT)),
    ITEM(new PredicateList()
            .inc(MaterialTags.DOORS)
            .inc(Material.IRON_BARS)
            .inc(Material.CHAIN)
            .inc(MaterialTags.GLASS_PANES)
            .inc(MaterialSetTag.CANDLES)
            .inc(Material.POINTED_DRIPSTONE)
            .inc(Material.SMALL_AMETHYST_BUD)
            .inc(Material.MEDIUM_AMETHYST_BUD)
            .inc(Material.LARGE_AMETHYST_BUD)
            .inc(Material.AMETHYST_CLUSTER)
            .inc(MaterialSetTag.SAPLINGS)
            .inc(Material.BROWN_MUSHROOM)
            .inc(Material.RED_MUSHROOM)
            .inc(Material.CRIMSON_FUNGUS)
            .inc(Material.WARPED_FUNGUS)
            .inc(Material.DEAD_BUSH)
            .inc(Material.FERN)
            .inc(Material.BAMBOO)
            .inc(Material.SUGAR_CANE)
            .inc(Material.LARGE_FERN)
            .inc(Material.NETHER_SPROUTS)
            .inc(Material.WARPED_ROOTS)
            .inc(Material.CRIMSON_ROOTS)
            .inc(Material.WEEPING_VINES)
            .inc(Material.VINE)
            .inc(Material.TWISTING_VINES)
            .inc(Material.HANGING_ROOTS)
            .inc(Material.TURTLE_EGG)
            .inc(Material.SNIFFER_EGG)
            .inc(Material.FROGSPAWN)
            .inc(Material.NETHER_WART)
            .inc(Material.SEA_PICKLE)
            .inc(Material.KELP)
            .inc(Material.SEAGRASS)
            .inc(Material.LILY_PAD)
            .inc(Material.COBWEB)
            .inc(Material.CAULDRON)
            .inc(Material.TORCH)
            .inc(Material.SOUL_TORCH)
            .inc(Material.REDSTONE_TORCH)
            .inc(Material.LANTERN)
            .inc(Material.SOUL_LANTERN)
            .inc(Material.CAMPFIRE)
            .inc(Material.SOUL_CAMPFIRE)
            .inc(Material.BREWING_STAND)
            .inc(Material.BELL)
            .inc(Material.LADDER)
            .inc(Material.FLOWER_POT)
            .inc(Material.REPEATER)
            .inc(Material.COMPARATOR)
            .inc(Material.TRIPWIRE_HOOK)
            .inc(Material.LEVER)
            .inc(Material.DEAD_BRAIN_CORAL)
            .inc(Material.DEAD_BRAIN_CORAL_FAN)
            .inc(Material.DEAD_BUBBLE_CORAL)
            .inc(Material.DEAD_BUBBLE_CORAL_FAN)
            .inc(Material.DEAD_TUBE_CORAL)
            .inc(Material.DEAD_TUBE_CORAL_FAN)
            .inc(Material.DEAD_FIRE_CORAL)
            .inc(Material.DEAD_FIRE_CORAL_FAN)
            .inc(Material.DEAD_HORN_CORAL)
            .inc(Material.DEAD_HORN_CORAL_FAN)
            .inc(Material.CAKE)
            .inc(MaterialSetTag.ALL_SIGNS)
            .inc(MaterialSetTag.CANDLES)
            .inc(MaterialSetTag.RAILS)
            .inc(MaterialSetTag.CORALS)
            .exc(Material.AZALEA)
            .exc(Material.FLOWERING_AZALEA)
            .exc(Material.SPORE_BLOSSOM)),
    BLOCK(MaterialType::isBlock);

    private Predicate<Material> predicate = null;
    private PredicateList predicateList = null;

    MaterialType(Predicate<Material> predicate){
        this.predicate = predicate;
    }
    MaterialType(PredicateList predicateList){
        this.predicateList = predicateList;
    }
    private boolean qualifies(Material material){
        return (predicateList != null && predicateList.isInclude(material))
                || (predicate != null && predicate.test(material));
    }

    public static @NotNull MaterialType getType(Material material){
        for (MaterialType type : values())
            if (type.qualifies(material))
                return type;
        return MaterialType.ITEM;
    }

    public static boolean isBlock(Material material){
        try{ material.createBlockData();
        } catch(Exception e){ return false; }
        return true;
    }
}
