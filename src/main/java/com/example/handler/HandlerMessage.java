package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.Function;
import com.example.constance.rent.Rent;
import com.example.handler.button.Button;
import com.example.handler.button.GeneralInfoButtons;
import com.example.handler.button.MuseumButtons;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class HandlerMessage {

    @SneakyThrows
    public void handlerOfStart(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals("/start")) {
            bot.sendMessage(chatId, Function.START);
        }
    }

    @SneakyThrows
    public void handlerOfMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Button.MUSEUM.getFullName())){
           bot.sendMessage(chatId, Function.MUSEUM, MuseumButtons.getButtons());
        }
    }

    @SneakyThrows
    public void handlerOfGeneralInfo(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Button.GENERAL_INFO.getFullName())){
            bot.sendMessage(chatId, Function.GENERAL_INFO, GeneralInfoButtons.getButtons());
        }
    }

    @SneakyThrows
    public void handlerOfRent(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Button.RENT.getFullName())){
            for (Rent rent: Rent.values()){
                bot.sendMessage(chatId, rent.getDescription());
                bot.sendPhoto(chatId, rent.getPhoto());
            }
        }
    }
}
