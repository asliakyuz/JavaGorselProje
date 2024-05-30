package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bulaniklastirma {
    private BufferedImage image;
    private int blurLevel;

    public Bulaniklastirma(File file, int blurLevel) {
        try {
            this.image = ImageIO.read(file);
            this.blurLevel = blurLevel;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void applyFilter() {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage blurredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = getBlurredRGB(x, y);
                blurredImage.setRGB(x, y, rgb);
            }
        }

        image = blurredImage;
    }

    private int getBlurredRGB(int x, int y) {
        int totalRed = 0, totalGreen = 0, totalBlue = 0;
        int count = 0;

        for (int dy = -blurLevel; dy <= blurLevel; dy++) {
            for (int dx = -blurLevel; dx <= blurLevel; dx++) {
                int nx = Math.min(Math.max(x + dx, 0), image.getWidth() - 1);
                int ny = Math.min(Math.max(y + dy, 0), image.getHeight() - 1);

                int rgb = image.getRGB(nx, ny);
                Color color = new Color(rgb);
                totalRed += color.getRed();
                totalGreen += color.getGreen();
                totalBlue += color.getBlue();

                count++;
            }
        }

        int avgRed = totalRed / count;
        int avgGreen = totalGreen / count;
        int avgBlue = totalBlue / count;

        return new Color(avgRed, avgGreen, avgBlue).getRGB();
    }

    public void saveImage(File outputFile) {
        try {
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
