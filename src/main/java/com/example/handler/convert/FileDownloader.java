package com.example.handler.convert;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

public class FileDownloader {

    private final String botToken;

    public FileDownloader(String botToken) {
        this.botToken = botToken;
    }

    public byte[] getFileBytesFromPhotoSize(PhotoSize photoSize) {
        try {
            // 1. Получить информацию о файле через API
            String getFileUrl = String.format("https://api.telegram.org/bot%s/getFile?file_id=%s", botToken, photoSize.getFileId());
            HttpURLConnection getFileConnection = (HttpURLConnection) new URL(getFileUrl).openConnection();
            getFileConnection.setRequestMethod("GET");

            if (getFileConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed to get file info: " + getFileConnection.getResponseMessage());
            }

            // Чтение JSON-ответа и извлечение пути к файлу
            String jsonResponse = new String(getFileConnection.getInputStream().readAllBytes());
            String filePath = jsonResponse.replaceAll(".*\"file_path\":\"(.*?)\".*", "$1");

            if (filePath.isEmpty()) {
                throw new RuntimeException("Failed to extract file path from response");
            }

            // 2. Сформировать полный URL файла
            String fileUrl = String.format("https://api.telegram.org/file/bot%s/%s", botToken, filePath);

            // 3. Загрузить файл
            HttpURLConnection fileConnection = (HttpURLConnection) new URL(fileUrl).openConnection();
            fileConnection.setRequestMethod("GET");

            if (fileConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed to download file: " + fileConnection.getResponseMessage());
            }

            // 4. Преобразовать InputStream в массив байтов
            try (InputStream inputStream = fileConnection.getInputStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving file bytes", e);
        }
    }
}
