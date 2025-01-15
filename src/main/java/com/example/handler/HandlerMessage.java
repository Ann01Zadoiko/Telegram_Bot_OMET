package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.Function;
import com.example.constance.museum.MuseumEnum;
import com.example.constance.museum.Registration;
import com.example.constance.rent.Rent;
import com.example.feature.museum.MuseumService;
import com.example.handler.button.Button;
import com.example.handler.button.GeneralInfoButtons;
import com.example.handler.button.MuseumButtons;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class HandlerMessage {

    private final MuseumService museumService;

    @SneakyThrows
    public void handlerOfStart(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals("/start")) {
            bot.sendMessage(chatId, Function.WELCOME);
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

    @SneakyThrows
    public void handlerOfNewDayToMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.contains(Function.SET_NEW_DAY)){

            String dayString = text.substring(9);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(dayString, formatter);

            museumService.setNewDayToExhibition(date);

            bot.sendMessage(chatId, "Ви змінили день екскурсії на " + dayString);
        }
    }

    @SneakyThrows
    public void handlerOfFullNameForMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        String[] s = text.split(" ");

        //add check
        if (s.length == 2){
            bot.sendMessage(chatId, Registration.STEP_3.getText());
        }
    }

    @SneakyThrows
    public void handlerOfPhoneNumberForMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        //add check
        if (text.startsWith("0") && text.length()==10){
            bot.sendMessage(chatId, Registration.STEP_4.getText());
        }
    }

    @SneakyThrows
    public void handlerOfCountOfPeopleForMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        //add check
        if (text.length() < 3){
            bot.sendMessage(chatId, Registration.STEP_8.getText());
        }
    }


}
