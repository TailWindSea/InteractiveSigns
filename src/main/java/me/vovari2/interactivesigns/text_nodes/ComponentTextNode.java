package me.vovari2.interactivesigns.text_nodes;

import me.vovari2.interactivesigns.utils.TextUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

public class ComponentTextNode {
    private final Component value;
    ComponentTextNode(Component value){
        this.value = value;
    }

    public ComponentTextNode replace(String placeholder, String placeholderValue) {
        return replace(placeholder, TextUtils.toComponent(placeholderValue));
    }
    public ComponentTextNode replace(String placeholder, Component placeholderValue) {
        return new ComponentTextNode(this.value.replaceText(TextReplacementConfig.builder().matchLiteral(placeholder).replacement(placeholderValue).build()));
    }
    public Component value() {
        return value;
    }
}
