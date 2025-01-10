package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.config.BotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class BotHandler {

    private final HandlerCallback callback;
    private final HandlerMessage message;
    private final BotConfig config;

    public void answerToCallback(Update update){
        TelegramBot bot = new TelegramBot(config);

        callback.handlerOfGeneralInfo(update, bot);
        callback.handlerOfMuseum(update, bot);
        callback.handlerOfVacancy(update, bot);
    }

    public void answerToMessage(Update update){
        TelegramBot bot = new TelegramBot(config);

        message.handlerOfGeneralInfo(update, bot);
        message.handlerOfMuseum(update, bot);
        message.handlerOfRent(update, bot);
        message.handlerOfStart(update, bot);
    }
}
