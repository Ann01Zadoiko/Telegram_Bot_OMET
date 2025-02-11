package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.config.BotConfig;
import com.example.feature.complaint.ComplaintService;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.UserService;
import com.example.registration.RegistrationType;
import com.example.registration.UserRegistration;
import com.example.registration.UserStateManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class BotHandler {

    private final HandlerCallback callback;
    private final HandlerMessage message;
    private final BotConfig config;
    private final MuseumService museumService;
    private final UserService userService;
    private final ComplaintService complaintService;
    private final UserStateManager stateManager;


    public void answerToCallback(Update update){
        TelegramBot bot = new TelegramBot(config, museumService, userService, complaintService, stateManager);

        callback.handlerOfGeneralInfo(update, bot);
        callback.handlerOfMuseum(update, bot);
        callback.handlerOfVacancy(update, bot);
        callback.handlerOfBack(update, bot);
        callback.handlerOfYesOrNoMuseum(update, bot);
        callback.handlerOfSkip(update, bot);
    }

    @SneakyThrows
    public void answerToMessage(Update update, UserStateManager stateManager){
        TelegramBot bot = new TelegramBot(config, museumService, userService, complaintService, stateManager);

        message.handlerOfGeneralInfo(update, bot);
        message.handlerOfMuseum(update, bot);
        message.handlerOfRent(update, bot);
        message.handlerOfStart(update, bot);
        message.handlerOfNewDayToMuseum(update, bot);
        message.handlerOfCloseExhibition(update, bot);
        message.handlerOfHelp(update, bot);
        message.handlerOfShow(update, bot);
        message.handlerOfCommandVacancy(update, bot);
    }


}
