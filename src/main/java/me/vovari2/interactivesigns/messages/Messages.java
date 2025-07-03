package me.vovari2.interactivesigns.messages;

import me.vovari2.interactivesigns.messages.types.ChatMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public enum Messages {
    COMMAND_HELP("""
              <gradient:#54B435:#82CD47><strikethrough>        </strikethrough></gradient> <#E8E7AB>InteractiveSigns</#E8E7AB> <gradient:#82CD47:#54B435><strikethrough>        </strikethrough></gradient>
              <newline>  <#54B435>- <#E8E7AB> <hover:show_text:'<#E8E7AB>Message about the information of the plugin commands'>/is help</hover></#E8E7AB>
              <newline>  <#54B435>- <#E8E7AB> <hover:show_text:'<#E8E7AB>Reloading the plugin'>/is reload</hover></#E8E7AB>
              <newline>  <#54B435>- <#E8E7AB> <hover:show_text:'<#E8E7AB>Clear all entities created by the plugin within a certain radius'>/is clear <radius></hover></#E8E7AB><newline>"""),
    COMMAND_RELOAD("<gradient:#54B435:#82CD47>The plugin has been reloaded!"),
    COMMAND_CLEAR("<gradient:#54B435:#82CD47>All displays within a <#F2AE66><%radius%></#F2AE66> radius have been removed!"),

    WARNING_YOU_CANT_PUT_THAT_HERE("<bold><red>Hey!</bold> <gray>Sorry, but you can't put that here"),
    WARNING_YOU_CANT_USE_THAT_HERE("<bold><red>Hey!</bold> <gray>Sorry, but you can't use that here");

    private StringMessage message;
    Messages(String message){
        this.message = new ChatMessage(this, message);
    }
    public void setMessage(@NotNull StringMessage message){
        this.message = message;
    }

    public @NotNull String string(){
        return message.string();
    }
    public @NotNull Component component(){
        return message.component();
    }
    public @NotNull MessageType type(){
        return message.type();
    }

    public @NotNull StringMessage replace(@NotNull  String placeholder, final String replacement){
        return message.replace(placeholder, replacement);
    }
    public @NotNull StringMessage replace(@NotNull String placeholder, long replacement){
        return replace(placeholder, String.valueOf(replacement));
    }

    public void send(@NotNull CommandSender sender){
        message.send(sender);
    }
}
