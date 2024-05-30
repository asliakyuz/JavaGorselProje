package org.example;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WaveFilter {
    private BufferedImage image;

    public WaveFilter(File file) {
        try {
            this.image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void applyFilter() {
        if (image == null) {
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage waveImage = new BufferedImage(width, height, image.getType());

        int amplitude = 10;
        int frequency = 20;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int newX = x + (int) (amplitude * Math.sin(2 * Math.PI * y / frequency));
                int newY = y + (int) (amplitude * Math.sin(2 * Math.PI * x / frequency));

                if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                    waveImage.setRGB(x, y, image.getRGB(newX, newY));
                } else {
                    waveImage.setRGB(x, y, 0x000000);
                }
            }
        }

        image = waveImage;
    }

    public void saveImage(File file) {
        try {
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}