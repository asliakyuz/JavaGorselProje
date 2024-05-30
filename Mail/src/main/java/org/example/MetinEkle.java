package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MetinEkle extends ImageFilter {

    private String text;
    private int x, y;
    private Color color;
    private Font font;

    public MetinEkle(File inputFile, String text, int x, int y, Color color, Font font) {
        super(inputFile);
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
        this.font = font;
    }

    @Override
    public void applyFilter() {
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.drawString(text, x, y);
        g2d.dispose();
    }

    public static void openTextDialog(JFrame parentFrame, File selectedFile) {
        JDialog dialog = new JDialog(parentFrame, "Metin Ekle", true);
        dialog.setTitle("Metin Ekle");
        dialog.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 5, 5));

        JTextField textTextField = new JTextField();
        JTextField xTextField = new JTextField();
        JTextField yTextField = new JTextField();
        JTextField sizeTextField = new JTextField();

        JComboBox<String> colorComboBox = new JComboBox<>(new String[]{"Siyah", "Kırmızı", "Yeşil", "Mavi", "Sarı"});
        JComboBox<String> fontComboBox = new JComboBox<>(new String[]{"Arial", "Times New Roman", "Courier New"});

        inputPanel.add(new JLabel("Metin:"));
        inputPanel.add(textTextField);
        inputPanel.add(new JLabel("X Koordinatı:"));
        inputPanel.add(xTextField);
        inputPanel.add(new JLabel("Y Koordinatı:"));
        inputPanel.add(yTextField);
        inputPanel.add(new JLabel("Boyut:"));
        inputPanel.add(sizeTextField);
        inputPanel.add(new JLabel("Renk:"));
        inputPanel.add(colorComboBox);
        inputPanel.add(new JLabel("Font:"));
        inputPanel.add(fontComboBox);

        JButton applyButton = new JButton("Uygula");
        applyButton.addActionListener(e -> {
            try {
                String text = textTextField.getText();
                int x = Integer.parseInt(xTextField.getText());
                int y = Integer.parseInt(yTextField.getText());
                int size = Integer.parseInt(sizeTextField.getText());

                if (x < 0 || y < 0 || size <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Koordinatlar ve boyut pozitif olmalıdır.", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Color color = getColorByName((String) colorComboBox.getSelectedItem());
                Font font = new Font((String) fontComboBox.getSelectedItem(), Font.PLAIN, size);

                MetinEkle metinEkle = new MetinEkle(selectedFile, text, x, y, color, font);
                metinEkle.applyFilter();
                File outputFile = new File(selectedFile.getParent(), "filtered_" + selectedFile.getName());
                metinEkle.saveImage(outputFile);
                dialog.dispose();

                try {
                    BufferedImage updatedImage = ImageIO.read(outputFile);
                    Frame2.updateImageLabel(updatedImage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                parentFrame.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Geçerli bir koordinat ve boyut giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(applyButton, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    private static Color getColorByName(String colorName) {
        switch (colorName) {
            case "Siyah":
                return Color.BLACK;
            case "Kırmızı":
                return Color.RED;
            case "Yeşil":
                return Color.GREEN;
            case "Mavi":
                return Color.BLUE;
            case "Sarı":
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }
}
