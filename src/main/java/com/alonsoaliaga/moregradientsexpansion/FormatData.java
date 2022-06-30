package com.alonsoaliaga.moregradientsexpansion;

import java.awt.*;
import java.util.List;

public class FormatData {
    private List<Color> colors;
    private String modifierString;
    public FormatData(List<Color> colors, String modifierString) {
        this.colors = colors;
        this.modifierString = modifierString;
    }
    public List<Color> getColors() {
        return colors;
    }
    public String getModifierString() {
        return modifierString;
    }
}