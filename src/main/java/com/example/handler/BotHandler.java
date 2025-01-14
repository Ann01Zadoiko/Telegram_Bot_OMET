package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.config.BotConfig;
import com.example.feature.museum.MuseumService;
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

    public void answerToCallback(Update update){
        TelegramBot bot = new TelegramBot(config, museumService);

        callback.handlerOfGeneralInfo(update, bot);
        callback.handlerOfMuseum(update, bot);
        callback.handlerOfVacancy(update, bot);
        callback.handlerOfBack(update, bot);
        callback.handlerOfYesOrNoMuseum(update, bot);
    }

    public void answerToMessage(Update update){
        TelegramBot bot = new TelegramBot(config, museumService);

        message.handlerOfGeneralInfo(update, bot);
        message.handlerOfMuseum(update, bot);
        message.handlerOfRent(update, bot);
        message.handlerOfStart(update, bot);
        message.handlerOfNewDayToMuseum(update, bot);
        message.handlerOfFullNameForMuseum(update, bot);
        message.handlerOfPhoneNumberForMuseum(update, bot);
        message.handlerOfCountOfPeopleForMuseum(update, bot);
    }
}
