package com.example.handler.message;

import com.example.feature.museum.MuseumService;
import com.example.handler.BotSenderService;
import com.example.handler.MessageChecker;
import com.example.handler.printer.MuseumPrinter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class MessageOfShow implements IMessage{

    private final MuseumService museumService;

    @Override
    @SneakyThrows
    public void handlerOfMessage(Update update, BotSenderService botSenderService){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (MessageChecker.isShow(text) && chatId == 391736560L){
            String dayString = text.substring(6);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(dayString, formatter);

            String answer = new MuseumPrinter(museumService).print(date);

            botSenderService.sendMessage(chatId, answer);
        }
    }
}
