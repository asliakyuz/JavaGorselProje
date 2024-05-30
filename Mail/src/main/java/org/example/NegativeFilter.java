package org.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

public class NegativeFilter extends ImageFilter {
    public NegativeFilter(File file) {
        super(file);
    }


    @Override
    public void applyFilter() {
        BufferedImage originalImage = getImage();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();


        BufferedImage negativeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Color originalColor = new Color(originalImage.getRGB(x, y));


                int red = 255 - originalColor.getRed();
                int green = 255 - originalColor.getGreen();
                int blue = 255 - originalColor.getBlue();


                Color negativeColor = new Color(red, green, blue);


                negativeImage.setRGB(x, y, negativeColor.getRGB());
            }
        }


        setImage(negativeImage);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}