package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DikeyYansit extends ImageFilter {

    public DikeyYansit(File inputFile) {
        super(inputFile);
    }

    @Override
    public void applyFilter() {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage mirroredImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mirroredImage.setRGB(x, y, image.getRGB(width - 1 - x, y));
            }
        }

        image = mirroredImage;
    }
}
