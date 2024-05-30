package org.example;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
public class SolaCevir extends ImageFilter {
    public SolaCevir(File inputFile) {
        super(inputFile);
    }

    @Override
    public void applyFilter() {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = result.createGraphics();
        g2d.rotate(-Math.toRadians(90));
        g2d.drawImage(image, -width, 0, null);
        g2d.dispose();

        image = result;
    }
}