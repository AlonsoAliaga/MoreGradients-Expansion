package com.alonsoaliaga.moregradientsexpansion.utils;

import com.alonsoaliaga.moregradientsexpansion.Gradient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChatUtils {
    private static final Pattern ALL_FORMATS_PATTERN = Pattern.compile("(&#§x[a-f0-9]{6}|(§[a-f0-9]){6}|[&§][a-f0-9klmnor])",Pattern.CASE_INSENSITIVE);
    private static final Pattern ALL_FORMATS_PATTERN_IMPROVED = Pattern.compile("(&?#[a-f0-9]{6}|[&§][a-f0-9klmnorx])",Pattern.CASE_INSENSITIVE);
    private static final Pattern PARSED_COLORS_PATTERN = Pattern.compile("§[a-f0-9klmnor]",Pattern.CASE_INSENSITIVE);
    private static final Pattern PARSED_HEX_PATTERN = Pattern.compile("§x(§[a-f0-9]){6}",Pattern.CASE_INSENSITIVE);
    private static final Pattern ALL_FORMATS_PATTERN_TO_PARSE = Pattern.compile("(&?#[a-f0-9]{6}|&[a-f0-9klmnor])",Pattern.CASE_INSENSITIVE);
    private static final Pattern HEX_FORMAT_TO_PARSE = Pattern.compile("(&?#[a-f0-9]{6})",Pattern.CASE_INSENSITIVE);
    public static String applyColor(String text, List<Color> colors, String modifierString) {
        if(colors.size() == 1) {
            return colorToParsedHex(colors.get(0)) + text;
        }else if(colors.size() > 1) {
            Gradient gradient = new Gradient(colors,text.length());
            StringBuilder builder = new StringBuilder();
            for (char c : text.toCharArray()) {
                builder.append(colorToParsedHex(gradient.next())).append(parse(modifierString)).append(c);
            }
            return builder.toString();
        }
        return text;
    }
    public static String parse(String string) {
        return ChatColor.translateAlternateColorCodes('&',string);
    }
    public static String revertParsedHex(@Nonnull String string, String hexFormat) {
        if(hexFormat == null) return revertParsedHex(string);
        Matcher matcher = PARSED_HEX_PATTERN.matcher(string);
        while(matcher.find()) {
            String match = matcher.group(0);
            string = string.replace(match,hexFormat.replace("<hex-color>",match.substring(2).replace("§","")));
        }
        return string;
    }
    public static String revertParsedHex(@Nonnull String string) {
        Matcher matcher = PARSED_HEX_PATTERN.matcher(string);
        while(matcher.find()) {
            String match = matcher.group(0);
            string = string.replace(match,"&#"+match.substring(2).replace("§",""));
        }
        return string;
    }
    public static String parseAllFormatting(@Nonnull String string) {
        Matcher matcher = HEX_FORMAT_TO_PARSE.matcher(string);
        while(matcher.find()) {
            String match = matcher.group(0);
            string = string.replace(match,hexToParsedHex(match));
        }
        return ChatColor.translateAlternateColorCodes('&',string);
    }
    public static String revertFormatting(@Nonnull String string) {
        return revertParsedColors(revertParsedHex(string));
    }
    public static String revertFormatting(@Nonnull String string, String hexFormat) {
        return revertParsedColors(revertParsedHex(string,hexFormat));
    }
    public static String removeFormatting(@Nonnull String string) {
        Matcher matcher = ALL_FORMATS_PATTERN_IMPROVED.matcher(string);
        while(matcher.find()) {
            String match = matcher.group(0);
            string = string.replace(match,"");
        }
        return string;
    }
    public static String revertParsedColors(@Nonnull String string) {
        Matcher matcher = PARSED_COLORS_PATTERN.matcher(string);
        while(matcher.find()) {
            String match = matcher.group(0);
            string = string.replace(match,match.replace("§","&"));
        }
        return string;
    }
    public static String removeColors(@Nonnull String string) {
        Matcher matcher = PARSED_COLORS_PATTERN.matcher(string);
        while(matcher.find()) {
            String match = matcher.group(0);
            string = string.replace(match,match.replace("§","&"));
        }
        return string;
    }
    public static String applyColorNoConversion(String text, List<Color> colors, String hexFormat, String modifierString) {
        if(hexFormat == null) return applyColorNoConversion(text, colors, modifierString);
        if(colors.size() == 1) {
            return colors.get(0) + text;
        }else if(colors.size() > 1) {
            Gradient gradient = new Gradient(colors,text.length());
            StringBuilder builder = new StringBuilder();
            for (char c : text.toCharArray()) {
                builder.append(colorToHex(gradient.next(),hexFormat)).append(modifierString).append(c);
            }
            Bukkit.getConsoleSender().sendMessage("§e[NO_CON] Parsing: '§r"+text+"§r§e' §7| §eColors: "+colors.stream().map(ChatUtils::colorToHex).collect(Collectors.joining(",")));
            return builder.toString();
        }
        return text;
    }
    public static String applyColorNoConversion(String text, List<Color> colors, String modifierString) {
        if(colors.size() == 1) {
            return colors.get(0) + text;
        }else if(colors.size() > 1) {
            Gradient gradient = new Gradient(colors,text.length());
            StringBuilder builder = new StringBuilder();
            for (char c : text.toCharArray()) {
                builder.append(colorToHex(gradient.next())).append(modifierString).append(c);
            }
            //Bukkit.getConsoleSender().sendMessage("§e[NO_CON] Parsing: '§r"+text+"§r§e' §7| §eColors: "+colors.stream().map(ChatUtils::colorToHex).collect(Collectors.joining(","))); //Debug for testing
            return builder.toString();
        }
        return text;
    }
    private static String colorToHex(Color color) {
        String hex = Integer.toHexString(color.getRGB());
        //Bukkit.getConsoleSender().sendMessage("#"+hex); //Debug for testing
        return "&#"+(hex.length() > 6 ? hex.substring(hex.length() - 6):hex);
    }
    private static String colorToHex(Color color, String hexFormat) {
        String hex = Integer.toHexString(color.getRGB());
        //Bukkit.getConsoleSender().sendMessage("#"+hex+" ("+hexFormat+")"); //Debug for testing
        return hexFormat.replace("<hex-color>",hex.length() > 6 ? hex.substring(hex.length() - 6):hex);
    }
    private static String colorToParsedHex(Color color) {
        StringBuilder result = new StringBuilder("§x");
        String hex = Integer.toHexString(color.getRGB());
        hex = hex.length() > 6 ? hex.substring(hex.length() - 6):hex;
        for (char c : hex.toCharArray()) {
            result.append("§").append(c);
        }
        return result.toString();
    }
    private static String hexToParsedHex(String hex) {
        if(hex.length() >= 7) {
            StringBuilder result = new StringBuilder("§x");
            hex = hex.substring(hex.length() - 6);
            for (char c : hex.toCharArray()) {
                result.append("§").append(c);
            }
            return result.toString();
        }
        return hex;
    }
}
