# Interactive Signs (1.20.2 - 1.21.4)
The plugin allows you to insert your items into the sign by clicking **the right mouse button**. To pick up an item, you need to hit the sign with **the left mouse button**.

This plugin also supports sign protection from plugins: **WorldGuard**, **GriefPrevention**, **SuperiorSkyblock2** and **HuskClaims** (for HuskClaims added “items_in_signs_put” trust flag)

Hopefully this will improve and diversify **your gaming experience**.

![Example](https://cdn.modrinth.com/data/cached_images/6519b865f6481460bcd85753502f62776ae870e9.png)

## Commands:
- **/ins help**  "Shows information on commands" (Permission: interactive_signs.help)
- **/ins reload**  "Restarts the plugin (to configure "text.yml")" (Permission: interactive_signs.reload)
- **/ins clear**  "Clears the item in signs within the specified radius" (Permission: interactive_signs.clear)


## File "config.yml":
```
# True - so players need the “permission_can_use_signs” permission to insert items into the signs.
# False - so all players can insert items into signs
player_need_to_have_permission_to_use_signs: false

# Permission required to insert items into the signs
permission_can_use_signs: "interactive_signs.use"
```

## File "text.yml":
```
# Messages, when using plugin commands
command:
  help: "<gradient:#54B435:#82CD47><strikethrough>        </strikethrough></gradient> <gradient:#E8E7AB:#F2AE66>InteractiveSigns</gradient> <gradient:#82CD47:#54B435><strikethrough>        </strikethrough></gradient>
  <newline><newline>  <#54B435>- <gradient:#E8E7AB:#F2AE66> <hover:show_text:'<#E8E7AB>Message about the information of the plugin commands'>/is help</hover></gradient>
  <newline>  <#54B435>- <gradient:#E8E7AB:#F2AE66> <hover:show_text:'<#E8E7AB>Reloading the plugin'>/is reload</hover></gradient>
  <newline>  <#54B435>- <gradient:#E8E7AB:#F2AE66> <hover:show_text:'<#E8E7AB>Clear all entities created by the plugin within a certain radius'>/is clear <radius></hover></gradient><newline>"
  reload: "<gradient:#54B435:#82CD47>The plugin has been reloaded!"
  clear: "<gradient:#54B435:#82CD47>All displays within a <#F2AE66><%radius%></#F2AE66> radius have been removed!"

# Warning, when a player uses a plaque in someone else's region
you_cant_use_that_here: "<bold><red>Hey!</bold> <gray>Sorry, but you can't use that here."

# Warning, when a player tries to insert an item into the sign with text
you_cant_place_item_because_sign_has_text: "<bold><red>Hey!</bold> <gray>Sorry, but it is already occupied by the text."
```