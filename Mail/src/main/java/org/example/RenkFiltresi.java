package org.example;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RenkFiltresi extends ImageFilter {

    private Color filterColor;

    public RenkFiltresi(File inputFile, Color filterColor) {
        super(inputFile);
        this.filterColor = filterColor;
    }

    @Override
    public void applyFilter() {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb, true);

                int red = (color.getRed() + filterColor.getRed()) / 2;
                int green = (color.getGreen() + filterColor.getGreen()) / 2;
                int blue = (color.getBlue() + filterColor.getBlue()) / 2;

                Color newColor = new Color(red, green, blue, color.getAlpha());
                image.setRGB(x, y, newColor.getRGB());
            }
        }
    }
}