package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.Function;


import com.example.constance.museum.Registration;
import com.example.constance.rent.Rent;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.User;
import com.example.feature.user.UserService;
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
import java.util.List;

@RequiredArgsConstructor
@Service
public class HandlerMessage {

    private final MuseumService museumService;
    private final UserService userService;

    @SneakyThrows
    public void handlerOfStart(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Function.START)) {
            bot.sendMessage(chatId, Function.WELCOME);
        }
    }

    @SneakyThrows
    public void handlerOfMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Button.MUSEUM.getFullName())){
           bot.sendMessage(chatId, Button.MUSEUM.getFullName(), MuseumButtons.getButtons());
        }
    }

    @SneakyThrows
    public void handlerOfGeneralInfo(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Button.GENERAL_INFO.getFullName())){
            bot.sendMessage(chatId, Button.GENERAL_INFO.getFullName(), GeneralInfoButtons.getButtons());
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
        Museum museum = museumService.getById(1L);

        if (MessageChecker.isSetDate(text) && userService.hasPermission(chatId)){

            String dayString = text.substring(9);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(dayString, formatter);
            museum.setClose(false);
            museum.setDate(date);
            museumService.save(museum);

            museumService.setNewDayToExhibition(date);

            bot.sendMessage(chatId, "Ви змінили день екскурсії на " + dayString);
        }
    }

    @SneakyThrows
    public void handlerOfFullNameForMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        //add check
        if (MessageChecker.isFullNameMuseum(text)){
            Museum byChatId = museumService.getByChatId(chatId);
            byChatId.setFullName(text);
            museumService.save(byChatId);

            bot.sendMessage(chatId, Registration.STEP_3.getText());
        }
    }

    @SneakyThrows
    public void handlerOfPhoneNumberForMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();
        //add check
        if (MessageChecker.isPhoneNumberMuseum(text)){
            Museum byChatId = museumService.getByChatId(chatId);
            byChatId.setPhoneNumber(text);
            museumService.save(byChatId);

            bot.sendMessage(chatId, Registration.STEP_4.getText());
        }
    }

    @SneakyThrows
    public void handlerOfCountOfPeopleForMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        //add check
        if (MessageChecker.isCountOfPeople(text)){
            Museum byChatId = museumService.getByChatId(chatId);
            byChatId.setCountOfPeople(Integer.parseInt(text));
            museumService.save(byChatId);

            bot.sendMessage(chatId, Registration.STEP_8.getText() + byChatId.getDate() + " на 12:00\n" + Registration.STEP_7.getText());
        }
    }

    @SneakyThrows
    public void handlerOfCloseExhibition(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        Museum museum = museumService.getById(1L);

        if (MessageChecker.isClose(text) && userService.hasPermission(chatId)){
            museum.setClose(true);
            museumService.save(museum);

            bot.sendMessage(chatId, "Ви закрили запись на ексеурсію");
        }
    }

    @SneakyThrows
    public void handlerOfHelp(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (MessageChecker.isHelp(text) && userService.hasPermission(chatId)){
            bot.sendMessage(chatId, Function.HELP_TEXT);
        }
    }

    @SneakyThrows
    public void handlerOfShow(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        String dayString = text.substring(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(dayString, formatter);

        if (MessageChecker.isShow(text) && userService.hasPermission(chatId)){
            String answer = "";
            int number = 1;
            int count = 0;

            List<Museum> byDate = museumService.getByDate(date);
            for (Museum museum: byDate){
                if (museum.getId() != 1L){
                    answer += (number++) + ". " + museum.getFullName() + "    " + museum.getPhoneNumber() + "    " + museum.getCountOfPeople() + "\n";
                    count += museum.getCountOfPeople();
                }
            }

            answer += "Всього: " + count;

            bot.sendMessage(chatId, answer);
        }
    }
}
