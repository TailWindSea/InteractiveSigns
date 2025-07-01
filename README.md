# Interactive Signs (1.20.2 - 1.21.6)
The plugin allows you to insert your items into the sign by clicking **the right mouse button**. To pick up an item, you need to hit the sign with **the left mouse button**.
Also, items on signs can be waxed with **honeycomb**, once they are waxed they cannot be taken by anyone. Waxing can be removed by using an **axe**.

This plugin also supports sign protection from plugins: **WorldGuard**, **Lands**, **Residence**, **HuskClaims**, **GriefPrevention**, **SuperiorSkyblock2**, **ChestProtect** and **GriefDefender**.

Hopefully this will improve and diversify **your gaming experience**.

![Example](/images/example_new.png)

## Core Protect
If you are using **CoreProtect (v22.0+)** to view player actions.
You can use the command to view the actions of putting and taking an item from the signs.
> **/co lookup action:sign**

![Example](/images/example_coreprotect.png)

## Commands:
- **/ins help**  "Shows information on commands" (Permission: interactive_signs.help)
- **/ins reload**  "Restarts the plugin (to configure "text.yml")" (Permission: interactive_signs.reload)
- **/ins clear**  "Clears the item in signs within the specified radius" (Permission: interactive_signs.clear)

## File "config.yml":
```
# True - so that when you insert an item into the signs, it will be three-dimensional (3D)
# False - so that when an item is inserted into the signs, it will be flat (2D)
enable_items_volume: false


# A list of items that will not be allowed to be tabbed (default: empty)
blacklist_of_items:
  - #nothing

# True - so players need the “permission_can_use_signs” permission to insert items into the signs
# False - so all players can insert items into signs
player_need_to_have_permission_to_use_signs: false
# Permission required to insert items into the signs
permission_can_use_signs: "interactive_signs.use"


## Settings for protection plugins
# HuskClaims
huskclaims:
  flag_id: "items_on_signs"

# Lands
lands:
  flag_id: "items_on_signs"
  flag_name: "Items on signs"
  flag_material: "SPRUCE_HANGING_SIGN"
  flag_description: "Flag that allows you to insert and take items from the tablets"
```

## File "text.yml":
```
# Messages, when using plugin commands
command:
  help: "<gradient:#54B435:#82CD47><strikethrough>        </strikethrough></gradient> <gradient:#E8E7AB:#F2AE66>InteractiveSigns</gradient> <gradient:#82CD47:#54B435><strikethrough>        </strikethrough></gradient>
  <newline><newline>  <#54B435>- <gradient:#E8E7AB:#F2AE66> <hover:show_text:'<#E8E7AB>Message about the information of the plugin commands'>/is help</hover></gradient>
  <newline>  <#54B435>- <gradient:#E8E7AB:#F2AE66> <hover:show_text:'<#E8E7AB>Reloading the plugin'>/is reload</hover></gradient>
  <newline>  <#54B435>- <gradient:#E8E7AB:#F2AE66> <hover:show_text:'<#E8E7AB>Clear all entities created by the plugin within a certain radius'>/is clear <radius></hover></gradient><newline>"
  clear: "<gradient:#54B435:#82CD47>All displays within a <#F2AE66><%radius%></#F2AE66> radius have been removed!"

warning:
  # Warning, when a player put a prohibited item in the sign (if you make the field empty (“”) , the message will not be sent)
  you_cant_put_that_here: "<bold><red>Hey!</bold> <gray>Sorry, but you can't put that here."

  # Warning, when a player uses a plaque in someone else's region (if you make the field empty (“”) , the message will not be sent)
  you_cant_use_that_here: "<bold><red>He2y!</bold> <gray>Sorry, but you can't use that here."
```