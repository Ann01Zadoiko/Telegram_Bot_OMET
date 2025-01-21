package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.Function;
import com.example.constance.complaint.Complain;
import com.example.constance.museum.Registration;
import com.example.constance.rent.Rent;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.UserService;
import com.example.handler.button.Button;
import com.example.handler.button.GeneralInfoButtons;
import com.example.handler.button.MuseumButtons;
import com.example.handler.button.SkipButton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
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
    public void handlerOfFullNameForMuseum(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        //add check
        if (MessageChecker.isFullNameMuseum(text)){
            List<Museum> byChatId = museumService.getByChatId(chatId);
            Museum museum = byChatId.get(byChatId.size() - 1);
            museum.setFullName(text);
            museumService.save(museum);

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
            List<Museum> byChatId = museumService.getByChatId(chatId);
            Museum museum = byChatId.get(byChatId.size() - 1);
            museum.setPhoneNumber(text);
            museumService.save(museum);

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
            List<Museum> byChatId = museumService.getByChatId(chatId);
            Museum museum = byChatId.get(byChatId.size() - 1);
            museum.setCountOfPeople(Integer.parseInt(text));
            museumService.save(museum);

            bot.sendMessage(chatId, Registration.STEP_8.getText() + museum.getDate() + " на 12:00\n" + Registration.STEP_7.getText());
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
    public void handlerOfHelp(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (MessageChecker.isHelp(text) && userService.existsByChatId(chatId)){
            bot.sendMessage(chatId, Function.HELP_TEXT);
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

    @SneakyThrows
    public void handlerOfComplaintButton(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (MessageChecker.isComplaintButton(text)){
            bot.sendMessage(chatId, Complain.STEP_1.getText() + "\n" + Complain.STEP_2.getText());
        }
    }

    @SneakyThrows
    public void handlerOfFullNameComplaint(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (MessageChecker.isFullNameComplaint(text)){
            bot.sendMessage(chatId, Complain.STEP_3.getText());
        }
    }

    @SneakyThrows
    public void handlerOfPhoneNumberComplaint(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (MessageChecker.isPhoneNumberComplaint(text)){
            bot.sendMessage(chatId, Complain.STEP_4.getText());
        }
    }

    @SneakyThrows
    public void handlerOfMessageOfComplaint(Update update, TelegramBot bot){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (MessageChecker.isComplaint(text)){
            bot.sendMessage(chatId, Complain.STEP_5.getText(), SkipButton.getButtons("SKIP_AUDIO"));
        }
    }
}
