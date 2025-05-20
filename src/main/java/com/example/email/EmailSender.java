package com.example.email;

import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

@Service
public class EmailSender {

    private EmailSender(){}

    public static void sendEmailWithAttachment(String recipient, String subject, String text) {
        final String senderEmail = "the.rain.frog01@gmail.com";
        final String senderPassword = "gadi tshe lfjc voer";

        // Настройка SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Сессия
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Создание письма
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            // Текст письма
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(text);

            // Комбинируем текст и вложение
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            // Устанавливаем содержимое письма
            message.setContent(multipart);

            // Отправка письма
            Transport.send(message);


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void sendEmailWithAttachment(String recipient, String subject, String text, String filePath) {
        final String senderEmail = "the.rain.frog01@gmail.com"; // Укажите свой email
        final String senderPassword = "gadi tshe lfjc voer"; // Пароль или app-specific пароль

        // Настройка SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Сессия
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Создание письма
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            // Текст письма
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(text);

            // Вложение
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath)); // Укажите путь к файлу

            // Комбинируем текст и вложение
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
                  multipart.addBodyPart(attachmentPart);

            // Устанавливаем содержимое письма
            message.setContent(multipart);

            // Отправка письма
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static String downloadFile(String fileUrl, String fileName) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        String localFilePath = System.getProperty("java.io.tmpdir") + fileName;

        try (InputStream inputStream = connection.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(localFilePath)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return localFilePath;
    }

}
