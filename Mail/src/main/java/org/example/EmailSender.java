
package org.example;
import javax.swing.JOptionPane;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EmailSender {
    public static void sendEmailWithAttachment(String email, BufferedImage filteredImage) {
        final String username = "asliaky123@gmail.com";
        final String password = "zaah yhiw buqp fpae";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your_email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Filtered Image");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Here is your filtered image");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            File outputFile = new File("filtered_image.jpg");
            ImageIO.write(filteredImage, "jpg", outputFile);
            DataSource source = new FileDataSource(outputFile);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(outputFile.getName());
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            JOptionPane.showMessageDialog(null, "E-posta başarıyla gönderildi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

