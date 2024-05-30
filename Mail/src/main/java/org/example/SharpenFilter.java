package org.example;


import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;

public class SharpenFilter extends ImageFilter {
    private static final float[][] SHARPEN_MATRIX = {
            {  0.0f, -1.0f,  0.0f },
            { -1.0f,  5.0f, -1.0f },
            {  0.0f, -1.0f,  0.0f }
    };

    public SharpenFilter(File file) {
        super(file);
    }

    @Override
    public void applyFilter() {
        BufferedImage originalImage = getImage();
        BufferedImage filteredImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                int[] pixel = getSurroundingPixels(originalImage, x, y);
                int red = (int) (SHARPEN_MATRIX[1][1] * getRed(pixel));
                int green = (int) (SHARPEN_MATRIX[1][1] * getGreen(pixel));
                int blue = (int) (SHARPEN_MATRIX[1][1] * getBlue(pixel));

                int sumAlpha = getAlpha(pixel);
                int sumRed = red;
                int sumGreen = green;
                int sumBlue = blue;

                filteredImage.setRGB(x, y, new Color(
                        clamp(sumRed),
                        clamp(sumGreen),
                        clamp(sumBlue),
                        clamp(sumAlpha)
                ).getRGB());
            }
        }

        setImage(filteredImage);
    }

    private int[] getSurroundingPixels(BufferedImage image, int x, int y) {
        int[] pixels = new int[9];
        int width = image.getWidth();
        int height = image.getHeight();

        int index = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    pixels[index] = image.getRGB(nx, ny);
                } else {
                    pixels[index] = image.getRGB(x, y);
                }
                index++;
            }
        }

        return pixels;
    }

    private int getAlpha(int[] pixels) {
        int sumAlpha = 0;
        for (int pixel : pixels) {
            sumAlpha += (pixel >> 24) & 0xFF;
        }
        return sumAlpha / pixels.length;
    }

    private int getRed(int[] pixels) {
        int sumRed = 0;
        for (int pixel : pixels) {
            sumRed += (pixel >> 16) & 0xFF;
        }
        return sumRed / pixels.length;
    }

    private int getGreen(int[] pixels) {
        int sumGreen = 0;
        for (int pixel : pixels) {
            sumGreen += (pixel >> 8) & 0xFF;
        }
        return sumGreen / pixels.length;
    }

    private int getBlue(int[] pixels) {
        int sumBlue = 0;
        for (int pixel : pixels) {
            sumBlue += pixel & 0xFF;
        }
        return sumBlue / pixels.length;
    }

    private int clamp(int value) {
        return Math.min(255, Math.max(0, value));
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}