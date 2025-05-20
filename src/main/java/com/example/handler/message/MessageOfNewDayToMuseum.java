package com.example.handler.message;


import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.handler.BotSenderService;
import com.example.handler.MessageChecker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MessageOfNewDayToMuseum implements IMessage{

    private final MuseumService museumService;

    @Override
    @SneakyThrows
    public void handlerOfMessage(Update update, BotSenderService sender){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();
        Museum museum = museumService.getById(1L);

        if (MessageChecker.isSetDate(text) && chatId == 391736560L){

            String dayString = text.substring(9);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(dayString, formatter);
            museum.setClose(false);
            museum.setDate(date);
            museumService.save(museum);

            museumService.setNewDayToExhibition(date);

            sender.sendMessage(chatId, "Ви змінили день екскурсії на " + dayString);
        }
    }
}
