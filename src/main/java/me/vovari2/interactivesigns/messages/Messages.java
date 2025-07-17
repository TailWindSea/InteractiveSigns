package me.vovari2.interactivesigns.messages;

import me.vovari2.interactivesigns.messages.types.ChatMessage;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public enum Messages {
    COMMAND_HELP("""
              <gradient:#54B435:#82CD47><strikethrough>        </strikethrough></gradient> <#E8E7AB>InteractiveSigns</#E8E7AB> <gradient:#82CD47:#54B435><strikethrough>        </strikethrough></gradient>
              <newline>  <#54B435>- <#E8E7AB> <hover:show_text:'<#E8E7AB>Message about the information of the plugin commands'>/is help</hover></#E8E7AB>
              <newline>  <#54B435>- <#E8E7AB> <hover:show_text:'<#E8E7AB>Reloading the plugin'>/is reload</hover></#E8E7AB>
              <newline>  <#54B435>- <#E8E7AB> <hover:show_text:'<#E8E7AB>Clear all entities created by the plugin within a certain radius'>/is clear <radius></hover></#E8E7AB><newline>"""),
    COMMAND_RELOAD_CONFIRM("<gradient:#54B435:#82CD47>Плагин был перезагружен!</gradient>"),
    COMMAND_RELOAD("<gradient:#54B435:#82CD47>Подтвердите перезапуск командой <yellow>/ins reload confirm</yellow>!</gradient>"),
    COMMAND_CLEAR("<gradient:#54B435:#82CD47>All displays within a <#F2AE66><%radius%></#F2AE66> radius have been removed!"),

    WARNING_YOU_CANT_PUT_THAT_HERE("<bold><red>Hey!</bold> <gray>Sorry, but you can't put that here"),
    WARNING_YOU_CANT_USE_THAT_HERE("<bold><red>Hey!</bold> <gray>Sorry, but you can't use that here");


    private Message message;
    Messages(@NotNull String message){
        this.message = new ChatMessage(message);
    }
    public void set(@NotNull Message message){
        this.message = message;
    }

    public String string(){
        return message.string();
    }
    public Component component(){
        return message.component();
    }
    public String string(@NotNull Player player){
        return message.string(player);
    }
    public Component component(@NotNull Player player){
        return message.component(player);
    }

    public Message replace(@NotNull String placeholder, @NotNull String replacement){
        return message.replace(placeholder, replacement);
    }
    public Message replace(@NotNull String placeholder, int replacement){
        return message.replace(placeholder, replacement);
    }
    public Message replace(@NotNull String placeholder, long replacement){
        return message.replace(placeholder, replacement);
    }

    public void send(@NotNull Audience audience){
        message.send(audience);
    }
}
