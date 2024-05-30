package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Frame2 {
    private static BufferedImage filteredImage;
    private static File selectedFile;
    private static JLabel imageLabel;

    public static void processFile(File file) {
        selectedFile = file;

        JFrame frame = new JFrame("Image Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(125, 125, 207)); // Açık lila arka plan rengi
        imageLabel = new JLabel();
        updateImageLabel(file);
        centerPanel.add(imageLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JComboBox<String> operationsComboBox = createComboBox();
        bottomPanel.add(operationsComboBox);

        JButton applyButton = new JButton("İşlemi Uygula");
        applyButton.setPreferredSize(new Dimension(200, 50));
        applyButton.setFont(new Font("Comic Sans", Font.BOLD, 16));
        applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        applyButton.addActionListener(e -> {
            String selectedOperation = (String) operationsComboBox.getSelectedItem();
            applyOperation(selectedOperation, file);
            askForMoreOperations(frame, operationsComboBox);
        });

        bottomPanel.add(applyButton);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static JComboBox<String> createComboBox() {
        String[] operations = {"Dikey Yansıt","Kalp Filtre" ,"Negatif Renk","Renk Filtresi","Dalga Ekleme", "Bulanıklaştır","Keskinleştir","Şekil Ekle","Mozaik Filtre", "Siyah Beyaz", "Sola Çevir","Sağa Çevir", "Ters Çevir", "Metin Ekle", "Yeniden Boyutlandır"};
        JComboBox<String> operationsComboBox = new JComboBox<>(operations);
        operationsComboBox.setPreferredSize(new Dimension(200, 30));
        operationsComboBox.setFont(new Font("Arial", Font.BOLD, 20));
        operationsComboBox.setBackground(new Color(124, 132, 181, 255));
        operationsComboBox.setForeground(Color.BLACK);
        operationsComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        return operationsComboBox;
    }

    private static void updateImageLabel(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(500, 500, Image.SCALE_DEFAULT));
            imageLabel.setIcon(imageIcon);
            imageLabel.setPreferredSize(new Dimension(600, 600));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateImageLabel(BufferedImage image) {
        ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(500, 500, Image.SCALE_DEFAULT));
        imageLabel.setIcon(imageIcon);
        imageLabel.setPreferredSize(new Dimension(500, 500));
    }

    private static void applyOperation(String operation, File file) {
        JOptionPane.showMessageDialog(null, "Seçilen işlem: " + operation, "İşlem Seçildi", JOptionPane.INFORMATION_MESSAGE);
        String fileName = file.getName();
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        String validFileName = fileNameWithoutExtension.replaceAll("[\\\\/:*?\"<>|]", "_") + "." + fileExtension;
        File outputFile = new File(file.getParent(), "filtered_" + validFileName);

        Runnable onComplete = () -> {
            if (outputFile.exists()) {
                try {
                    filteredImage = ImageIO.read(outputFile);
                    updateImageLabel(filteredImage);
                    JOptionPane.showMessageDialog(null, "Filtre başarıyla uygulandı ve kaydedildi: " + outputFile.getAbsolutePath(), "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };

        switch (operation) {
            case "Negatif Renk":
                NegativeFilter neg=new NegativeFilter(file);
                neg.applyFilter();
                neg.saveImage(outputFile);
                break;
            case "Dikey Yansıt":
                DikeyYansit dikeyYansit = new DikeyYansit(file);
                dikeyYansit.applyFilter();
                dikeyYansit.saveImage(outputFile);
                break;
            case "Keskinleştir":
                SharpenFilter kes=new SharpenFilter(file);
                kes.applyFilter();
                kes.saveImage(outputFile);
                break;
            case "Mozaik Filtre":
                MozaikFiltresi mozaikFiltresi = new MozaikFiltresi(file, 10);
                mozaikFiltresi.applyFilter();
                mozaikFiltresi.saveImage(outputFile);
                break;
            case "Kalp Filtre":
                HeartShapeCropper kalp=new HeartShapeCropper(file);
                kalp.applyFilter();
                kalp.saveImage(outputFile);
                break;
            case "Siyah Beyaz":
                SiyahBeyazCevir siyahBeyazCevir = new SiyahBeyazCevir(file);
                siyahBeyazCevir.applyFilter();
                siyahBeyazCevir.saveImage(outputFile);
                break;
            case "Sola Çevir":
                SolaCevir solaCevir = new SolaCevir(file);
                solaCevir.applyFilter();
                solaCevir.saveImage(outputFile);
                break;
            case "Sağa Çevir":
                SagaCevir sagaCevir= new SagaCevir(file);
                sagaCevir.applyFilter();
                sagaCevir.saveImage(outputFile);
                break;
            case "Ters Çevir":
                TersCevir tersCevir = new TersCevir(file);
                tersCevir.applyFilter();
                tersCevir.saveImage(outputFile);
                break;
            case "Yeniden Boyutlandır":
                YenidenBoyutlandir yenidenBoyutlandir = new YenidenBoyutlandir(file, 400, 300);
                yenidenBoyutlandir.applyFilter();
                yenidenBoyutlandir.saveImage(outputFile);
                break;
            case "Metin Ekle":
                MetinEkle.openTextDialog(new JFrame(), file);
                break;
            case "Bulanıklaştır":
                Bulaniklastirma bulaniklastirma = new Bulaniklastirma(file, 5); // Bulanıklık seviyesini burada belirleyebilirsiniz, burada 5 kullanıldı
                bulaniklastirma.applyFilter();
                bulaniklastirma.saveImage(outputFile);
                break;
            case "Renk Filtresi":
                Color filterColor = JColorChooser.showDialog(null, "Renk Seç", Color.BLACK);
                if (filterColor != null) {
                    RenkFiltresi renkFiltresi = new RenkFiltresi(file, filterColor);
                    renkFiltresi.applyFilter();
                    renkFiltresi.saveImage(outputFile);
                    updateImageLabel(outputFile);
                }
                break;
            case "Şekil Ekle":
                SekilEkle sekilEkle = new SekilEkle(file);
                sekilEkle.applyFilter();
                sekilEkle.saveImage(outputFile);
                updateImageLabel(outputFile);
                break;
            case "Dalga Ekleme":
                WaveFilter dalga=new WaveFilter(file);
                dalga.applyFilter();
                dalga.saveImage(outputFile);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Bu işlem henüz uygulanmadı.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                break;
        }

        if (outputFile.exists()) {
            try {
                filteredImage = ImageIO.read(outputFile);
                updateImageLabel(filteredImage); // Resmin güncellenmiş versiyonunu ekranda göster
                JOptionPane.showMessageDialog(null, "Filtre başarıyla uygulandı ve kaydedildi: " + outputFile.getAbsolutePath(), "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void askForMoreOperations(JFrame frame, JComboBox<String> operationsComboBox) {
        int response = JOptionPane.showConfirmDialog(null, "Başka bir işlem yapmak istiyor musunuz?", "İşlem Seçimi", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            selectedFile = saveFilteredImageTemporarily();
            frame.dispose();
            processFile(selectedFile);
        } else {
            askForSaveOrSend();
        }
    }

    private static File saveFilteredImageTemporarily() {
        File tempFile = new File("temp_filtered_image.jpg");
        try {
            ImageIO.write(filteredImage, "jpg", tempFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tempFile;
    }

    private static void askForSaveOrSend() {
        Object[] options = {"Resmi Kaydet", "E-posta Olarak Gönder"};
        int action = JOptionPane.showOptionDialog(null, "Resmi kaydetmek mi yoksa e-posta olarak göndermek mi istiyorsunuz?", "İşlem Seçimi",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (action == 0) {
            saveImage();
        } else if (action == 1) {
            sendEmail();
        }
    }

    private static void saveImage() {
        if (filteredImage != null) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    // Dosya uzantısını kontrol et ve ekle
                    String filePath = file.getAbsolutePath();
                    if (!filePath.toLowerCase().endsWith(".jpg") && !filePath.toLowerCase().endsWith(".jpeg")) {
                        file = new File(filePath + ".jpg");
                    }

                    ImageIO.write(filteredImage, "jpg", file);
                    JOptionPane.showMessageDialog(null, "Resim başarıyla kaydedildi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Resim kaydedilirken bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Kaydedilecek bir görüntü bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }


    private static void sendEmail() {
        String email = JOptionPane.showInputDialog("Mail adresinizi giriniz:");
        if (email != null && !email.isEmpty()) {
            EmailSender.sendEmailWithAttachment(email, filteredImage);
        }
    }
}

