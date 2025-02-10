package com.example.handler.convert;

import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;

import java.sql.Blob;

import java.util.List;


public class PhotoConverter {

    public Blob convertPhotoSizesToBlob(List<PhotoSize> photoSizes) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (PhotoSize photoSize : photoSizes) {
            if (photoSize.getFileSize() == null || photoSize.getFileSize() <= 0) {
                throw new IllegalArgumentException("PhotoSize contains no file data or file size is zero");
            }



            // Здесь предполагается, что файл представлен как байты, полученные через соответствующий метод
            byte[] fileBytes = getFileBytesFromPhotoSize(photoSize);


            if (fileBytes == null || fileBytes.length == 0) {
                throw new IllegalArgumentException("PhotoSize contains no file bytes");
            }

            try {
                outputStream.write(fileBytes);
            } catch (Exception e) {
                throw new RuntimeException("Error writing file bytes to stream", e);
            }
        }

        // Преобразование ByteArrayOutputStream в Blob
        byte[] finalBytes = outputStream.toByteArray();
        return new SerialBlob(finalBytes);
    }

    // Пример метода для получения байтов файла (этот метод должен быть реализован в зависимости от вашей логики)
    private byte[] getFileBytesFromPhotoSize(PhotoSize photoSize) {
        // Логика для извлечения байтов файла из объекта PhotoSize
        // Например, через Telegram Bot API или сохранённые данные
        return new byte[0]; // Верните реальные байты файла
    }

}
