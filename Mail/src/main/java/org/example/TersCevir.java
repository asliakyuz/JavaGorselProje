package org.example;
import java.awt.image.BufferedImage;
import java.io.File;


public class TersCevir extends ImageFilter {
    public TersCevir (File inputFile) {
        super(inputFile);
    }

    @Override
    public void applyFilter() {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result.setRGB(x, height - y - 1, image.getRGB(x, y));
            }
        }

        image = result;
    }
}