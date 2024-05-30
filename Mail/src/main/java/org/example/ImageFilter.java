package org.example;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public abstract class ImageFilter {
    protected BufferedImage image;

    public ImageFilter(File inputFile) {
        try {
            image = ImageIO.read(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void applyFilter();

    public void saveImage(File outputFile) {
        try {
            boolean saved = ImageIO.write(image, "jpg", outputFile);
            if (saved) {
                System.out.println("Resim başarıyla kaydedildi: " + outputFile.getAbsolutePath());
            } else {
                System.out.println("Resim kaydedilemedi.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }}}