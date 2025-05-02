package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.Button;
import com.example.constance.Function;

import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.UserService;

import com.example.handler.button.*;
import com.example.printer.NoticePrinter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class HandlerMessage {

    private final MuseumService museumService;
    private final UserService userService;
    private final NoticePrinter noticePrinter;

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
    public void handlerOfTracks(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Button.INFO_TRACKS.getFullName())){
            bot.sendMessage(chatId, "Оберіть тип транспортного засобу:", TracksButtons.getButtonsTracks());
        }
    }

    @SneakyThrows
    public void handlerOfNewDayToMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();
        Museum museum = museumService.getById(1L);

        if (MessageChecker.isSetDate(text) && userService.existsByChatId(chatId)){

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
    public void handlerOfCloseExhibition(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        Museum museum = museumService.getById(1L);

        if (MessageChecker.isClose(text) && userService.existsByChatId(chatId)){
            museum.setClose(true);
            museumService.save(museum);

            bot.sendMessage(chatId, "Ви закрили запись на ексеурсію");
        }
    }


    @SneakyThrows
    public void handlerOfShow(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (MessageChecker.isShow(text) && userService.existsByChatId(chatId)){
            String dayString = text.substring(6);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(dayString, formatter);

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


    public void handlerOfMuseumAndTracks(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals("Керування ботом") && chatId == 391736560L){
            String answer = "Оберіть дію:";

            List<String> list = new ArrayList<>();
            list.add("Керування музеем");
            list.add("Вакансії");
            list.add("Зупинки");
            list.add("Маршрут");
            list.add("Додати повідомлення");

            bot.sendMessage(chatId, answer, new GeneralButton().getButtons(list, list));
        }

        if (text.equals("Термінові повідомлення")){
            String print = noticePrinter.print(LocalDate.now());

            bot.sendMessage(chatId, print);
        }
    }
}
