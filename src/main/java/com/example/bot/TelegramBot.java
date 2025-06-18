package com.example.bot;

import com.example.config.BotConfig;
import com.example.handler.BotHandler;
import com.example.handler.BotSenderService;
import com.example.handler.CommandHandler;
import com.example.registration.UserStateManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserStateManager stateManager;
    private final BotHandler botHandler;
    private final BotSenderService botSenderService;
    private final CommandHandler commandHandler;

    @PostConstruct
    public void init() {
        botSenderService.setBot(this);
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {
        commandHandler.handlerOfCommands(update);

        if (update.hasMessage() && update.getMessage().hasText()) {
            botHandler.answerToMessage(update, stateManager);
        }

        if (update.hasCallbackQuery()) {
            botHandler.answerToCallback(update);
        }
    }
}

