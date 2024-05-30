package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeartShapeCropper {
    private BufferedImage image;

    public HeartShapeCropper(File file) {
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

        Path2D heart = new Path2D.Double();
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double heartWidth = width * 0.8;
        double heartHeight = height * 0.8;

        heart.moveTo(centerX, centerY - heartHeight / 4.0);
        heart.curveTo(centerX + heartWidth / 2.0, centerY - heartHeight / 2.0,
                centerX + heartWidth, centerY + heartHeight / 4.0,
                centerX, centerY + heartHeight / 2.0);
        heart.curveTo(centerX - heartWidth, centerY + heartHeight / 4.0,
                centerX - heartWidth / 2.0, centerY - heartHeight / 2.0,
                centerX, centerY - heartHeight / 4.0);
        heart.closePath();

        BufferedImage heartImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = heartImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(heart);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        image = heartImage;
    }

    public void saveImage(File file) {
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}