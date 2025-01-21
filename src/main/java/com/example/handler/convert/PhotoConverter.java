package com.example.handler.convert;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;


public class PhotoConverter {

    private TelegramBot bot;

    public PhotoConverter(String botToken) {
        this.bot = new TelegramBot(botToken);
    }


    public Blob convertPhotoSizeToBlob(PhotoSize photoSize, Connection connection) throws Exception {
        // Получение информации о файле
        GetFile getFileRequest = new GetFile(photoSize.fileId());
        GetFileResponse getFileResponse = bot.execute(getFileRequest);

        if (!getFileResponse.isOk()) {
            throw new RuntimeException("Failed to get file: " + getFileResponse.description());
        }

        // Получение файла
        String filePath = getFileResponse.file().filePath();
        String fileUrl = bot.getFullFilePath(getFileResponse.file());

        try (InputStream inputStream = new java.net.URL(fileUrl).openStream()) {
            // Преобразование InputStream в массив байтов
            byte[] fileBytes = inputStream.readAllBytes();

            // Сохранение в базе данных
            Blob blob = connection.createBlob();
            blob.setBytes(1, fileBytes);
            return blob;
        }
    }

}
