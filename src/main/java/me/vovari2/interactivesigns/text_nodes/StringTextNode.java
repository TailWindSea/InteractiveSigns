package me.vovari2.interactivesigns.text_nodes;

import me.vovari2.interactivesigns.utils.TextUtils;
import net.kyori.adventure.text.Component;

public class StringTextNode{
    private final String value;
    public StringTextNode(String value) {
        this.value = value;
    }

    public ComponentTextNode toComponent(){
        return new ComponentTextNode(TextUtils.toComponent(value));
    }
    public StringTextNode replace(String placeholder, String placeholderValue) {
        return new StringTextNode(value.replace(placeholder, placeholderValue));
    }

    public boolean isEmpty(){
        return value.isEmpty();
    }
    public Component value() {
        return TextUtils.toComponent(value);
    }
}
