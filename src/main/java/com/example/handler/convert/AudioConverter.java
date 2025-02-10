package com.example.handler.convert;


import org.telegram.telegrambots.meta.api.objects.Audio;
import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;

import java.sql.Blob;

public class AudioConverter {

    public Blob convertVoicesToBlob(Audio audio) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (audio.getFileSize() == null || audio.getFileSize() <= 0) {
            throw new IllegalArgumentException("Voice contains no file data or file size is zero");
        }

        // Здесь предполагается, что файл представлен как байты, полученные через соответствующий метод
        byte[] fileBytes = getFileBytesFromVoice(audio);

        if (fileBytes == null || fileBytes.length == 0) {
            throw new IllegalArgumentException("Voice contains no file bytes");
        }

        try {
            outputStream.write(fileBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error writing file bytes to stream", e);
        }

        // Преобразование ByteArrayOutputStream в Blob
        byte[] finalBytes = outputStream.toByteArray();
        return new SerialBlob(finalBytes);
    }

    // Пример метода для получения байтов файла (этот метод должен быть реализован в зависимости от вашей логики)
    private byte[] getFileBytesFromVoice(Audio voice) {
        // Логика для извлечения байтов файла из объекта Voice
        // Например, через Telegram Bot API или сохранённые данные
        return new byte[0]; // Верните реальные байты файла
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