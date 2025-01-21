package com.example.handler.convert;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Audio;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;

import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AudioConverter {

    private TelegramBot bot;

    public AudioConverter(String botToken) {
        this.bot = new TelegramBot(botToken);
    }

    public Blob convertAudioToBlob(Audio audio, Connection connection) throws Exception {
        // Получение информации о файле
        GetFile getFileRequest = new GetFile(audio.fileId());
        GetFileResponse getFileResponse = bot.execute(getFileRequest);

        if (!getFileResponse.isOk()) {
            throw new RuntimeException("Failed to get file: " + getFileResponse.description());
        }

        // Получение пути к файлу
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

    public void saveAudioToDatabase(Audio audio, Connection connection) throws Exception {
        Blob audioBlob = convertAudioToBlob(audio, connection);

        String insertSQL = "INSERT INTO audio_messages (audio_data, title, performer, duration) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setBlob(1, audioBlob);
            statement.setString(2, audio.title());
            statement.setString(3, audio.performer());
            statement.setInt(4, audio.duration());
            statement.executeUpdate();
        }
    }
}
//CREATE TABLE audio_messages (
//    id SERIAL PRIMARY KEY,
//    audio_data BLOB NOT NULL,
//    title VARCHAR(255),
//    performer VARCHAR(255),
//    duration INT,
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
//);