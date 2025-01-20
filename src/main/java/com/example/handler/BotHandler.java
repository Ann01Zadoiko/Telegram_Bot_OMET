package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.config.BotConfig;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.UserService;
import lombok.RequiredArgsConstructor;
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

    public void answerToCallback(Update update){
        TelegramBot bot = new TelegramBot(config, museumService, userService);

        callback.handlerOfGeneralInfo(update, bot);
        callback.handlerOfMuseum(update, bot);
        callback.handlerOfVacancy(update, bot);
        callback.handlerOfBack(update, bot);
        callback.handlerOfYesOrNoMuseum(update, bot);
    }

    public void answerToMessage(Update update){
        TelegramBot bot = new TelegramBot(config, museumService, userService);

        message.handlerOfGeneralInfo(update, bot);
        message.handlerOfMuseum(update, bot);
        message.handlerOfRent(update, bot);
        message.handlerOfStart(update, bot);
        message.handlerOfNewDayToMuseum(update, bot);
        message.handlerOfFullNameForMuseum(update, bot);
        message.handlerOfPhoneNumberForMuseum(update, bot);
        message.handlerOfCountOfPeopleForMuseum(update, bot);
        message.handlerOfCloseExhibition(update, bot);
        message.handlerOfHelp(update, bot);
        message.handlerOfShow(update, bot);
    }
}
