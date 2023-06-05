package com.alonsoaliaga.moregradientsexpansion;

import com.alonsoaliaga.moregradientsexpansion.utils.ChatUtils;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Cacheable;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class MoreGradientsExpansion extends PlaceholderExpansion implements Configurable, Cacheable {
    private final Pattern splitPattern = Pattern.compile("_(?=[^\\}]*(?:\\{|$))");
    private final String hexFormat;
    private final HashMap<String,FormatData> formats = new HashMap<>();
    public MoreGradientsExpansion() {
        boolean debug = false;
        try{
            debug = getPlaceholderAPI().getPlaceholderAPIConfig().isDebugMode();
        }catch (Throwable ignored) {}
        hexFormat = getString("default-hex-format","&#<hex-color>");
        ConfigurationSection formatsSection = getConfigSection("formats");
        if(formatsSection != null) {
            for (String formatIdentifier : formatsSection.getKeys(false)) {
                if(formatIdentifier.contains("_")) {
                    Bukkit.getConsoleSender().sendMessage("[MoreGradient-Expansion] Format identifier '"+formatIdentifier+"' cannot contain underscores. Skipping..");
                    continue;
                }
                String colorsString = formatsSection.getString(formatIdentifier+".colors",null);
                //Bukkit.getConsoleSender().sendMessage("[MoreGradient-Expansion] Loading '"+formatIdentifier+"' format with color '"+colorsString+"'..");  //Debug for testing
                if(colorsString != null && formatIdentifier.length() >= 1) {
                    List<Color> colors = new ArrayList<>();
                    for (String colorString : colorsString.split("-")) {
                        try{
                            colors.add(new Color(Integer.parseInt(colorString.substring(1), 16)));
                        }catch (Throwable ignored) {}
                    }
                    if(colors.isEmpty())
                        Bukkit.getConsoleSender().sendMessage("[MoreGradient-Expansion] Colors in '"+formatIdentifier+"' format are not valid. Skipping..");
                    else {
                        String modifierString = formatsSection.getString(formatIdentifier+".modifier","");
                        formats.put(formatIdentifier.toLowerCase(Locale.ROOT),new FormatData(colors,modifierString));
                        if(debug) Bukkit.getConsoleSender().sendMessage("[MoreGradient-Expansion] Successfully loaded '"+formatIdentifier+"' format: "+colorsString+"|"+modifierString);
                    }
                }
            }
        }
    }
    @Override
    public void clear() {
        formats.clear();
    }
    @Override
    public String onPlaceholderRequest(Player p, String params){
        if(params.equalsIgnoreCase("version")) {
            return getVersion();
        }
        if(params.equalsIgnoreCase("author")) {
            return getAuthor();
        }
        if(params.equalsIgnoreCase("hex_format")) {
            return hexFormat;
        }
        if(params.equalsIgnoreCase("loaded_formats")) {
            return "["+String.join(",", formats.keySet())+"]";
        }
        if(params.equalsIgnoreCase("loaded_formats_size")) {
            return String.valueOf(formats.size());
        }
        if(params.startsWith("iridium_stripped_no_parsed_")) {// iridium_stripped_no_parsed_&#<hex-color>_<GRADIENT:2C08BA>This is a message!</GRADIENT:028A97>
            String message = params.substring(27); // &#<hex-color>_<GRADIENT:2C08BA>This is a message!</GRADIENT:028A97>
            String[] parts = message.split("_");
            String format = null;
            if(parts.length >= 1) {
                if(parts[0].contains("<hex-color>")) {
                    format = parts[0];
                    message = message.substring(format.length()+(parts.length > 1 ? 1 : 0));
                }
            }
            return ChatUtils.revertParsedHex(IridiumColorAPI.process(ChatUtils.removeFormatting(PlaceholderAPI.setBracketPlaceholders(p,message))),format);
        }
        if(params.startsWith("iridium_stripped_")) {// iridium_stripped_<GRADIENT:2C08BA>This is a message!</GRADIENT:028A97>
            return IridiumColorAPI.process(ChatUtils.removeFormatting(PlaceholderAPI.setBracketPlaceholders(p,params.substring(17))));
        }
        if(params.startsWith("iridium_no_parsed_")) {// iridium_no_parsed_&#<hex-color>_<GRADIENT:2C08BA>This is a message!</GRADIENT:028A97>
            String message = params.substring(18); // &#<hex-color>_<GRADIENT:2C08BA>This is a message!</GRADIENT:028A97>
            String[] parts = message.split("_");
            String format = hexFormat;
            if(parts.length >= 1) {
                if(parts[0].contains("<hex-color>")) {
                    format = parts[0];
                    message = message.substring(format.length()+(parts.length > 1 ? 1 : 0));
                }
            }
            return ChatUtils.revertParsedHex(IridiumColorAPI.process(PlaceholderAPI.setBracketPlaceholders(p,message)),format);
        }
        if(params.startsWith("iridium_")) {// iridium_<GRADIENT:2C08BA>This is a message!</GRADIENT:028A97>
            return IridiumColorAPI.process(PlaceholderAPI.setBracketPlaceholders(p,params.substring(8)));
        }
        if(params.startsWith("custom_raw_no_parsed_")) {// custom_raw_no_parsed_#rrggbb-#rrggbb-#rrggbb_This is a message!
            return processCustomRawNoParsed(p, params.substring(21));
        }
        if(params.startsWith("custom_raw_")) {// custom_raw_#rrggbb-#rrggbb-#rrggbb_This is a message!
            return processCustomRaw(p, params.substring(11));//
        }
        if(params.startsWith("custom_no_parsed_")) {// custom_no_parsed_#rrggbb-#rrggbb-#rrggbb_This is a message!
            return processCustomNoParsed(p, params.substring(17));//
        }
        if(params.startsWith("custom_")) {// custom_#rrggbb-#rrggbb-#rrggbb_This is a message!
            return processCustom(p, params.substring(7));//
        }
        if(params.startsWith("colorize_")) {// colorize_&6This is a message with #rrggbbHEX colors&6!
            return processColorize(p, params.substring(9));//
        }
        if(params.startsWith("revertformatting_")) {// revertformatting_&6This is a message with #rrggbbHEX colors&6!
            return ChatUtils.revertFormatting(PlaceholderAPI.setBracketPlaceholders(p,params.substring(17)));
        }
        if(params.startsWith("removeformatting_")) {// removeformatting_&6This is a message with #rrggbbHEX colors&6!
            return ChatUtils.removeFormatting(PlaceholderAPI.setBracketPlaceholders(p,params.substring(17)));
        }
        return null;
    }
    @Override
    public Map<String, Object> getDefaults() {
        final Map<String, Object> defaults = new LinkedHashMap<>();
        defaults.put("default-hex-format","&#<hex-color>");
        defaults.put("formats.rainbow.colors","#FF0000-#FF7F00-#FFFF00-#00FF00-#0000FF-#4B0082-#9400D3");
        defaults.put("formats.rainbow.modifier","");
        defaults.put("formats.bold-rainbow.colors","#FF0000-#FF7F00-#FFFF00-#00FF00-#0000FF-#4B0082-#9400D3");
        defaults.put("formats.bold-rainbow.modifier","&l");
        defaults.put("formats.instagram.colors","#833ab4-#fd1d1d-#fcb045");
        defaults.put("formats.instagram.modifier","");
        defaults.put("formats.amethyst.colors","#9D50BB-#6E48AA");
        defaults.put("formats.amethyst.modifier","");
        defaults.put("formats.sunset.colors","#FF512F-#DD2476");
        defaults.put("formats.sunset.modifier","");
        return defaults;
    }
    @Override
    public @NotNull String getIdentifier() {
        return "moregradients";
    }
    @Override
    public @NotNull String getAuthor() {
        return "AlonsoAliaga";
    }
    @Override
    public @NotNull String getVersion() {
        return "0.2-BETA";
    }
    @Override
    public boolean persist() {
        return true;
    }
    @Override
    public boolean canRegister() {
        return true;
    }
    private String processCustom(Player player, String value) { // #rrggbb-#rrggbb-#rrggbb_This is a message!
        if(value.isEmpty()) return value;//
        String[] parts = splitPattern.split(value);
        if(parts.length >= 2) {
            String parsed = PlaceholderAPI.setBracketPlaceholders(player,parts[0]);
            String colorPart = parsed.toLowerCase(Locale.ROOT);
            value = value.substring(colorPart.length() + 1);
            String modifierString = "";
            List<Color> colors;
            if(formats.containsKey(colorPart)){
                colors = formats.get(colorPart).getColors();
                modifierString = formats.get(colorPart).getModifierString();
            }else{
                colors = new ArrayList<>();
                for (String colorString : colorPart.split("-")) {
                    if(colorString.startsWith("modifier=")) {
                        modifierString = colorString.substring(9);
                        continue;
                    }
                    try{
                        colors.add(new Color(Integer.parseInt(colorString.substring(1), 16)));
                    }catch (Throwable ignored) {}
                }
            }
            return ChatUtils.applyColor(ChatUtils.removeFormatting(PlaceholderAPI.setBracketPlaceholders(player,value)),colors,modifierString);
        }
        return "§cInvalid format!";
    }
    private String processCustomRawNoParsed(Player player, String value) { // #rrggbb-#rrggbb-#rrggbb_&#<hex-color>_This is a message!
        if(value.isEmpty()) return value;//
        String[] parts = splitPattern.split(value);
        if(parts.length >= 2) {
            String parsed = PlaceholderAPI.setBracketPlaceholders(player,parts[0]);
            String colorPart = parsed.toLowerCase(Locale.ROOT);
            String format = hexFormat;
            if(parts.length >= 3) {
                String optionalFormat = parts[1];
                if (optionalFormat.contains("<hex-color>")) {
                    format = optionalFormat;
                    value = value.substring(colorPart.length() + format.length() + 2);
                }else value = value.substring(colorPart.length() + 1);
            }else value = value.substring(colorPart.length() + 1);
            List<Color> colors;
            String modifierString = "";
            if(formats.containsKey(colorPart)){
                colors = formats.get(colorPart).getColors();
                modifierString = formats.get(colorPart).getModifierString();
            }else{
                colors = new ArrayList<>();
                for (String colorString : colorPart.split("-")) {
                    if(colorString.startsWith("modifier=")) {
                        modifierString = colorString.substring(9);
                        continue;
                    }
                    try{
                        colors.add(new Color(Integer.parseInt(colorString.substring(1), 16)));
                    }catch (Throwable ignored) {}
                }
            }
            return ChatUtils.applyColorNoConversion(ChatUtils.revertFormatting(PlaceholderAPI.setBracketPlaceholders(player,value),format),colors,format,modifierString);
        }
        return "§cInvalid format!";
    }
    private String processCustomRaw(Player player, String value) { // #rrggbb-#rrggbb-#rrggbb_This is a message!
        if(value.isEmpty()) return value;//
        String[] parts = splitPattern.split(value);
        if(parts.length >= 2) {
            String parsed = PlaceholderAPI.setBracketPlaceholders(player,parts[0]);
            String colorPart = parsed.toLowerCase(Locale.ROOT);
            String format = hexFormat;
            if(parts.length >= 3) {
                String optionalFormat = parts[1];
                if (optionalFormat.contains("<hex-color>")) {
                    format = optionalFormat;
                    value = value.substring(colorPart.length() + format.length() + 2);
                }else value = value.substring(colorPart.length() + 1);
            }else value = value.substring(colorPart.length() + 1);
            List<Color> colors;
            String modifierString = "";
            if(formats.containsKey(colorPart)){
                colors = formats.get(colorPart).getColors();
                modifierString = formats.get(colorPart).getModifierString();
            }else{
                colors = new ArrayList<>();
                for (String colorString : colorPart.split("-")) {
                    if(colorString.startsWith("modifier=")) {
                        modifierString = colorString.substring(9);
                        continue;
                    }
                    try{
                        colors.add(new Color(Integer.parseInt(colorString.substring(1), 16)));
                    }catch (Throwable ignored) {}
                }
            }
            return ChatUtils.applyColor(ChatUtils.revertFormatting(PlaceholderAPI.setBracketPlaceholders(player,value),format),colors,modifierString);
        }
        return "§cInvalid format!";
    }
    private String processCustomNoParsed(Player player, String value) { // #rrggbb-#rrggbb-#rrggbb_&#<hex-color>_This is a message!
        if(value.isEmpty()) return value;//
        String[] parts = splitPattern.split(value);
        if(parts.length >= 2) {
            String parsed = PlaceholderAPI.setBracketPlaceholders(player,parts[0]);
            String colorPart = parsed.toLowerCase(Locale.ROOT);
            String format = hexFormat;
            if(parts.length >= 3) {
                String optionalFormat = parts[1];
                if (optionalFormat.contains("<hex-color>")) {
                    format = optionalFormat;
                    value = value.substring(colorPart.length() + format.length() + 2);
                }else value = value.substring(colorPart.length() + 1);
            }else value = value.substring(colorPart.length() + 1);
            List<Color> colors;
            String modifierString = "";
            if(formats.containsKey(colorPart)){
                colors = formats.get(colorPart).getColors();
                modifierString = formats.get(colorPart).getModifierString();
            }else{
                colors = new ArrayList<>();
                for (String colorString : colorPart.split("-")) {
                    if(colorString.startsWith("modifier=")) {
                        modifierString = colorString.substring(9);
                        continue;
                    }
                    try{
                        colors.add(new Color(Integer.parseInt(colorString.substring(1), 16)));
                    }catch (Throwable ignored) {}
                }
            }
            return ChatUtils.applyColorNoConversion(ChatUtils.removeFormatting(PlaceholderAPI.setBracketPlaceholders(player,value)),colors,format,modifierString);
        }
        return "§cInvalid format!";
    }
    private String processColorize(Player player, String value) {
        return ChatUtils.parseAllFormatting(PlaceholderAPI.setBracketPlaceholders(player,value));
    }
}