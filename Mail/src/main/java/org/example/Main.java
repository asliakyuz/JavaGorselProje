package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dosya Seçim Uygulaması");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(new Color(125, 125, 207));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(300, 0, 1, 0));

        JLabel welcomeLabel = new JLabel("Görüntü İşleme Uygulamasına Hoş geldiniz");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 40));
        welcomeLabel.setForeground(new Color(64, 64, 89));

        Image img2 = new ImageIcon(Main.class.getResource("/resimikonu.png")).getImage();
        welcomeLabel.setIcon(new ImageIcon(img2));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));

        centerPanel.add(welcomeLabel);

        JLabel instructionLabel = new JLabel(" İşlem yapmak istediğiniz dosyayı seçiniz.");
        instructionLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        instructionLabel.setForeground(new Color(64, 64, 89));

        Image img3 = new ImageIcon(Main.class.getResource("/catikon.png")).getImage();
        instructionLabel.setIcon(new ImageIcon(img3));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(instructionLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton chooseFileButton = new JButton("Dosyayı Seç");
        chooseFileButton .setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        chooseFileButton.setForeground(new Color(64, 64, 89));


        Image img1 = new ImageIcon(Main.class.getResource("/secmeikonu.png")).getImage();
        chooseFileButton.setIcon(new ImageIcon(img1));
        chooseFileButton.setPreferredSize(new Dimension(2000, 190));
        chooseFileButton.setBackground(new Color(108, 107, 149));
        chooseFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        chooseFileButton.addActionListener(new ChooseFileActionListener());
        centerPanel.add(chooseFileButton);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
        frame.add(backgroundPanel);
        frame.setVisible(true);
    }
}