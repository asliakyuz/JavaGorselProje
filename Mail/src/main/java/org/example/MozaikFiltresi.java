package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
public class MozaikFiltresi extends ImageFilter {

    private final int tileSize;

    public MozaikFiltresi(File inputFile, int tileSize) {
        super(inputFile);
        this.tileSize = tileSize;
    }

    @Override
    public void applyFilter() {
        int width = image.getWidth();
        int height = image.getHeight();


        for (int x = 0; x < width; x += tileSize) {
            for (int y = 0; y < height; y += tileSize) {
                int tileAvgRed = 0;
                int tileAvgGreen = 0;
                int tileAvgBlue = 0;
                int count = 0;


                for (int i = x; i < Math.min(x + tileSize, width); i++) {
                    for (int j = y; j < Math.min(y + tileSize, height); j++) {
                        int rgb = image.getRGB(i, j);
                        tileAvgRed += (rgb >> 16) & 0xFF;
                        tileAvgGreen += (rgb >> 8) & 0xFF;
                        tileAvgBlue += rgb & 0xFF;
                        count++;
                    }
                }


                tileAvgRed /= count;
                tileAvgGreen /= count;
                tileAvgBlue /= count;


                int tileRGB = (tileAvgRed << 16) | (tileAvgGreen << 8) | tileAvgBlue;
                for (int i = x; i < Math.min(x + tileSize, width); i++) {
                    for (int j = y; j < Math.min(y + tileSize, height); j++) {
                        image.setRGB(i, j, tileRGB);
                    }
                }
            }
        }
    }
}