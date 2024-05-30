package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SekilEkle extends ImageFilter {
    public SekilEkle(File inputFile) {
        super(inputFile);
    }

    @Override
    public void applyFilter() {
        ShapeDialog shapeDialog = new ShapeDialog();
        ShapeOptions selectedShape = shapeDialog.getSelectedShape();

        if (selectedShape != null) {
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(selectedShape.getColor());

            switch (selectedShape.getShape()) {
                case "Daire":
                    g2d.fillOval(selectedShape.getX(), selectedShape.getY(), selectedShape.getWidth(), selectedShape.getHeight());
                    break;
                case "Dikdörtgen":
                    g2d.fillRect(selectedShape.getX(), selectedShape.getY(), selectedShape.getWidth(), selectedShape.getHeight());
                    break;
                case "Üçgen":
                    int[] xPoints = {
                            selectedShape.getX(),
                            selectedShape.getX() + selectedShape.getWidth() / 2,
                            selectedShape.getX() + selectedShape.getWidth()
                    };
                    int[] yPoints = {
                            selectedShape.getY() + selectedShape.getHeight(),
                            selectedShape.getY(),
                            selectedShape.getY() + selectedShape.getHeight()
                    };
                    g2d.fillPolygon(xPoints, yPoints, 3);
                    break;
            }
            g2d.dispose();
        }
    }

    public static class ShapeDialog extends JDialog {
        private JComboBox<String> shapeComboBox;
        private JTextField widthField;
        private JTextField heightField;
        private JTextField xField;
        private JTextField yField;
        private JButton colorButton;
        private JButton okButton;
        private Color selectedColor;
        private ShapeOptions selectedShape;

        public ShapeDialog() {
            setTitle("Şekil Ekle");
            setSize(450, 450);
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);  // Pencereyi ekranın ortasında açmak için

            shapeComboBox = new JComboBox<>(new String[]{"Daire", "Dikdörtgen", "Üçgen"});
            widthField = new JTextField(10);
            heightField = new JTextField(10);
            xField = new JTextField(10);
            yField = new JTextField(10);
            colorButton = new JButton("Renk Seç");
            okButton = new JButton("Tamam");

            colorButton.addActionListener(e -> {
                selectedColor = JColorChooser.showDialog(this, "Renk Seç", Color.BLACK);
            });

            okButton.addActionListener(e -> {
                try {
                    String shape = (String) shapeComboBox.getSelectedItem();
                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    int x = Integer.parseInt(xField.getText());
                    int y = Integer.parseInt(yField.getText());

                    selectedShape = new ShapeOptions(shape, width, height, x, y, selectedColor);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doğru doldurduğunuzdan emin olun.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            });

            add(createLabelAndComponent("Şekil Seçin:", shapeComboBox));
            add(createLabelAndComponent("Genişlik:", widthField));
            add(createLabelAndComponent("Yükseklik:", heightField));
            add(createLabelAndComponent("X Konumu:", xField));
            add(createLabelAndComponent("Y Konumu:", yField));
            add(colorButton);
            add(okButton);

            pack();
            setModal(true);
            setVisible(true);
        }

        private JPanel createLabelAndComponent(String labelText, JComponent component) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.add(new JLabel(labelText));
            panel.add(component);
            return panel;
        }

        public ShapeOptions getSelectedShape() {
            return selectedShape;
        }
    }

    public static class ShapeOptions {
        private final String shape;
        private final int width;
        private final int height;
        private final int x;
        private final int y;
        private final Color color;

        public ShapeOptions(String shape, int width, int height, int x, int y, Color color) {
            this.shape = shape;
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
            this.color = color;
        }

        public String getShape() {
            return shape;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Color getColor() {
            return color;
        }
    }
}
