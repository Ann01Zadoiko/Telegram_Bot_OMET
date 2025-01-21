package com.example.handler.convert;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Voice;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;

import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class VoiceConverter {

    private TelegramBot bot;

    public VoiceConverter(String botToken) {
        this.bot = new TelegramBot(botToken);
    }

    public Blob convertVoiceToBlob(Voice voice, Connection connection) throws Exception {
        // Получение информации о файле
        GetFile getFileRequest = new GetFile(voice.fileId());
        GetFileResponse getFileResponse = bot.execute(getFileRequest);

        if (!getFileResponse.isOk()) {
            throw new RuntimeException("Failed to get file: " + getFileResponse.description());
        }

        // Получение пути файла
        String filePath = getFileResponse.file().filePath();
        String fileUrl = bot.getFullFilePath(getFileResponse.file());

        try (InputStream inputStream = new URL(fileUrl).openStream()) {
            // Чтение данных из InputStream
            byte[] fileBytes = inputStream.readAllBytes();

            // Создание объекта Blob
            Blob blob = connection.createBlob();
            blob.setBytes(1, fileBytes);
            return blob;
        }
    }

    public void saveVoiceToDatabase(Voice voice, Connection connection) throws Exception {
        Blob voiceBlob = convertVoiceToBlob(voice, connection);

        String insertSQL = "INSERT INTO voice_messages (voice_data) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setBlob(1, voiceBlob);
            statement.executeUpdate();
        }
    }
}
