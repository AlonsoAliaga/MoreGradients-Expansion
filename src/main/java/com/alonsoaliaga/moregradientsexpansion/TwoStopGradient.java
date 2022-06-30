package com.alonsoaliaga.moregradientsexpansion;

import java.awt.*;

public class TwoStopGradient {
    private Color startColor;
    private Color endColor;
    private int lowerRange;
    private int upperRange;
    public TwoStopGradient(Color startColor, Color endColor, int lowerRange, int upperRange) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
    }
    public Color colorAt(int step) {
        return new Color(this.calculateHexPiece(step, this.startColor.getRed(), this.endColor.getRed()),
                this.calculateHexPiece(step, this.startColor.getGreen(), this.endColor.getGreen()),
                this.calculateHexPiece(step, this.startColor.getBlue(), this.endColor.getBlue()));
    }
    private int calculateHexPiece(int step, int channelStart, int channelEnd) {
        int range = this.upperRange - this.lowerRange;
        double interval = (channelEnd - channelStart) * 1d / range;
        return Math.max(0,Math.min(255,(int) Math.round(interval * (step - this.lowerRange) + channelStart)));
    }
}