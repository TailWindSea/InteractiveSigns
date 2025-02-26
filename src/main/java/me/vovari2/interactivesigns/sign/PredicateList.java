package me.vovari2.interactivesigns.sign;

import org.bukkit.Material;
import org.bukkit.Tag;

import java.util.LinkedList;
import java.util.List;

public class PredicateList {
    private final List<PredicateValue> includingValues;
    private final List<PredicateValue> excludingValues;
    public PredicateList(){
        includingValues = new LinkedList<>();
        excludingValues = new LinkedList<>();
    }
    public PredicateList inc(Material material){
        includingValues.add(new MaterialValue(material));
        return this;
    }
    public PredicateList inc(Tag<Material> tag){
        includingValues.add(new MaterialTagValue(tag));
        return this;
    }
    public PredicateList exc(Material material){
        excludingValues.add(new MaterialValue(material));
        return this;
    }
    public boolean isInclude(Material material){
        for (PredicateValue value : includingValues)
            if (value.equals(material) && !isExclude(material))
                return true;
        return false;
    }
    private boolean isExclude(Material material){
        for (PredicateValue value : excludingValues)
            if (value.equals(material))
                return true;
        return false;
    }

    private interface PredicateValue {
        boolean equals(Material material);
    }
    private record MaterialValue(Material material) implements PredicateValue {
        @Override
        public boolean equals(Material material) {
            return this.material.equals(material);
        }
    }
    private record MaterialTagValue(Tag<Material> tag) implements PredicateValue {
        @Override
        public boolean equals(Material material) {
            return this.tag.isTagged(material);
        }
    }
}
