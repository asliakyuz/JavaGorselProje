package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class YenidenBoyutlandir extends ImageFilter {
    private int newWidth;
    private int newHeight;

    public YenidenBoyutlandir(File inputFile, int newWidth, int newHeight) {
        super(inputFile);
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    @Override
    public void applyFilter() {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, newWidth, newHeight, null);
        g.dispose();
        image = resizedImage;
    }

    public void openResizeDialog(JFrame parentFrame) {
        JTextField widthField = new JTextField(5);
        JTextField heightField = new JTextField(5);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Yeni Genişlik:"));
        panel.add(widthField);
        panel.add(Box.createHorizontalStrut(15)); // Boşluk
        panel.add(new JLabel("Yeni Yükseklik:"));
        panel.add(heightField);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel,
                "Resmi Yeniden Boyutlandır", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int newWidth = Integer.parseInt(widthField.getText());
                int newHeight = Integer.parseInt(heightField.getText());
                resizeImage(newWidth, newHeight);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parentFrame, "Lütfen geçerli bir genişlik ve yükseklik girin.",
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void resizeImage(int newWidth, int newHeight) {
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        applyFilter();
    }

    public void saveImage(File outputFile) {
        try {
            ImageIO.write(image, "jpg", outputFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}