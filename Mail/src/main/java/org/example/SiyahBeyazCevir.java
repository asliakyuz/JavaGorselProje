package org.example;
import java.awt.Color;
import java.io.File;

public class SiyahBeyazCevir extends ImageFilter {
    public SiyahBeyazCevir(File inputFile) {

        super(inputFile);
    }

    @Override
    public void applyFilter() {

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color c = new Color(image.getRGB(x, y));
                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);
                int gray = red + green + blue;
                Color newColor = new Color(gray, gray, gray);
                image.setRGB(x, y, newColor.getRGB());
            }
        }
    }
}