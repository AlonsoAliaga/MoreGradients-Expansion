# MoreGradients-Expansion
This is a [PlaceholderAPI](https://links.alonsoaliaga.com/PlaceholderAPI) expansion to allow owners/configurator customize texts a bit more.

# Installation
You can install this expansion in 2 ways:
### 1) PlaceholderAPI eCloud (Available ✔️)
While being in console or having OP run the following commands:
> /papi ecloud download moregradients\
> /papi reload

✅ Expansion is ready to be used!
### 2) Manual download
Go to [eCloud](https://api.extendedclip.com/expansions/moregradients/) and click `Download Latest` button to get the .jar file.\
Copy and paste the file in `/plugins/PlaceholderAPI/expansions/` and run:
> /papi reload

✅ Expansion is ready to be used!
# Placeholders
The following placeholders are available:
## Iridium formats
> ### %moregradients_iridium_YOUR MESSAGE%
> <span style="font-weight:bold;color: rgb(255,100,100)">RECOMMENDED TO USE THIS ONE OVER THE OTHERS OF IRIDIUM SECTION.</span><br>
> Allows you to use IridiumFormat in messages. Read more about iridium format [here](https://github.com/Iridium-Development/IridiumColorAPI). <br>
> Supports PlaceholderAPI but requires `{ }` instead of `% %`.<br>
>
> **Example:** %moregradients_iridium_&lt;RAINBOW1&gt;Hello my name is {player_name}&lt;/RAINBOW&gt;%<br>
> **Output:**&nbsp;<img style="position: absolute;padding-bottom: 50px;" src="https://i.gyazo.com/18b1ebbe1323a0d264b81f48b44f6b3a.png">

> ### %moregradients_iridium_no_parsed_YOUR MESSAGE%
> :warning: **This is meant to be used by other plugin. Do not use it if you don't know how to.**\
> This is the same that the first one, but returns color codes **WITHOUT** being parsed.
>
> **Example:** %moregradients_iridium_no_parsed_&lt;RAINBOW1&gt;I am {player_name}&lt;/RAINBOW&gt;%<br>
> **Output:** `&#ff0000I&#ff5a00 &#ffb400a&#f0ff00m&#96ff00 &#3cff00A&#00ff1el&#00ff78o&#00ffd2n&#00d2ffs&#0078ffo&#001effA&#3c00ffl&#9600ffi&#f000ffa&#ff00b4g&#ff005aa`

> ### %moregradients_iridium_stripped_YOUR MESSAGE%
> This is the same that the first one, but returns a string with parsed color codes which was stripped after placeholders were parsed. (Removes all formatting before applying iridium format)<br>
>
> **Example:** %moregradients_iridium_stripped_&lt;RAINBOW1&gt;Hello my name is {player_name}&lt;/RAINBOW&gt;%<br>
> **Output:**&nbsp;<img style="position: absolute;padding-bottom: 50px;" src="https://i.gyazo.com/18b1ebbe1323a0d264b81f48b44f6b3a.png">

> ### %moregradients_iridium_stripped_no_parsed_YOUR MESSAGE%
> :warning: **This is meant to be used by other plugin. Do not use it if you don't know how to.**\
> This is the same that the first one, but returns color codes **WITHOUT** being parsed which was stripped after placeholders were parsed. (Removes all formatting before applying iridium format).
>
> **Example:** %moregradients_iridium_stripped_no_parsed_&lt;RAINBOW1&gt;I am {player_name}&lt;/RAINBOW&gt;%<br>
> **Output:** `&#ff0000I&#ff5a00 &#ffb400a&#f0ff00m&#96ff00 &#3cff00A&#00ff1el&#00ff78o&#00ffd2n&#00d2ffs&#0078ffo&#001effA&#3c00ffl&#9600ffi&#f000ffa&#ff00b4g&#ff005aa`

## Custom colors
> ### %moregradients_custom_&lt;colors&gt;_YOUR MESSAGE%
> <span style="font-weight:bold;color: rgb(255,100,100)">RECOMMENDED TO USE THIS ONE OVER THE OTHERS OF CUSTOM SECTION.</span><br>
> Allows you to create text with gradients with more than 2 colors!<br>
> Supports PlaceholderAPI but requires `{ }` instead of `% %`.<br>
> Returns a string **WITH** parsed color codes which was stripped after placeholders were parsed. (Removes all formatting before applying colors)<br>
> <span style="font-weight:bold;color: rgb(100,150,255)">For &lt;colors&gt; you can use colors in hex like `#833ab4-#fd1d1d-#fcb045`, default identifiers like `rainbow` or create your own formats with custom identifiers in `/plugins/PlaceholderAPI/config.yml` in **moregradients** format section.</span>
>
> **Example:** %moregradients_custom_#833ab4-#fd1d1d-#fcb045_Hello my name is {player_name}%<br>
> **Output:**&nbsp;<img style="position: absolute;padding-bottom: 50px;" src="https://i.gyazo.com/474860089e8653561e8a1c643835f307.png">
>
> **Example:** %moregradients_custom_instagram_Hello my name is {player_name}%<br>
> **Output:**&nbsp;<img style="position: absolute;padding-bottom: 50px;" src="https://i.gyazo.com/474860089e8653561e8a1c643835f307.png">

> ### %moregradients_custom_no_parsed_&lt;colors&gt;_YOUR MESSAGE%
> :warning: **This is meant to be used by other plugin. Do not use it if you don't know how to.**\
> This is the same that `custom` one, but returns color codes without being parsed.
>
> **Example:** %moregradients_custom_no_parsed_#833ab4-#fd1d1d-#fcb045_I am {player_name}%<br>
> **Output:** `&#833ab4I&#9236a1 &#a2338ea&#b12f7bm&#c02c69 &#cf2856A&#df2443l&#ee2130o&#fd1d1dn&#fd2f22s&#fd4227o&#fd542cA&#fd6731l&#fc7936i&#fc8b3ba&#fc9e40g&#fcb045a`
>
> **Example:** %moregradients_custom_no_parsed_instagram_I am {player_name}%<br>
> **Output:** `&#833ab4I&#9236a1 &#a2338ea&#b12f7bm&#c02c69 &#cf2856A&#df2443l&#ee2130o&#fd1d1dn&#fd2f22s&#fd4227o&#fd542cA&#fd6731l&#fc7936i&#fc8b3ba&#fc9e40g&#fcb045a`

> ### %moregradients_custom_raw_&lt;colors&gt;_YOUR MESSAGE%
> This is the same that `custom` one, but doesn't remove formatting before setting placeholders or applying colors.\
> **Example:** %moregradients_custom_raw_#833ab4-#fd1d1d-#fcb045_Hello my name is {player_name}%<br>
> **Output:**&nbsp;<img style="position: absolute;padding-bottom: 50px;" src="https://i.gyazo.com/474860089e8653561e8a1c643835f307.png">
>
> **Example:** %moregradients_custom_raw_instagram_Hello my name is {player_name}%<br>
> **Output:**&nbsp;<img style="position: absolute;padding-bottom: 50px;" src="https://i.gyazo.com/474860089e8653561e8a1c643835f307.png">

> ### %moregradients_custom_raw_no_parsed_&lt;colors&gt;_YOUR MESSAGE%
> :warning: **This is meant to be used by other plugin. Do not use it if you don't know how to.**\
> This is the same that `custom` one, but deson't remove formatting before setting placeholders or applying colors; after that returns color codes without being parsed.\
> **Example:** %moregradients_custom_raw_no_parsed_#833ab4-#fd1d1d-#fcb045_Hello my name is {player_name}%<br>
> **Output:** `&#833ab4I&#9236a1 &#a2338ea&#b12f7bm&#c02c69 &#cf2856A&#df2443l&#ee2130o&#fd1d1dn&#fd2f22s&#fd4227o&#fd542cA&#fd6731l&#fc7936i&#fc8b3ba&#fc9e40g&#fcb045a`
>
> **Example:** %moregradients_custom_raw_no_parsed_instagram_I am {player_name}%<br>
> **Output:** `&#833ab4I&#9236a1 &#a2338ea&#b12f7bm&#c02c69 &#cf2856A&#df2443l&#ee2130o&#fd1d1dn&#fd2f22s&#fd4227o&#fd542cA&#fd6731l&#fc7936i&#fc8b3ba&#fc9e40g&#fcb045a`

## Utils
> ### %moregradients_colorize_YOUR MESSAGE% (🔰 Since 0.2-BETA)
> :warning: **This was made in case your placeholder doesn't return colors parsed.**\
> Returned value is ready to be used wherever you want.\
> **Example:** %moregradients_colorize_&eI am &6&l{player_name}%<br>
> **Output:** `§eI am §6§lAlonsoAliaga`

# Want more gradients?
**Make sure to check our [BRAND NEW TOOL](https://alonsoaliaga.com/hex) to generate your own text with gradients!**<br>
<p align="center">
    <a href="https://alonsoaliaga.com/hex"><img src="https://i.imgur.com/766Es8I.png" alt="Our brand new tool!"/></a>
</p>

# Questions?
<p align="center">
    <a href="https://alonsoaliaga.com/discord"><img style="width:200px;" src="https://alonsoaliaga.github.io/assets/img/AlonsoAliaga_logo_animated.gif"></a><br>
    <a href="https://alonsoaliaga.com/discord"><span style="font-size:25px;font-weight:bold;color:rgb(100,100,255);">Join us in our discord!</span></a>
</p>