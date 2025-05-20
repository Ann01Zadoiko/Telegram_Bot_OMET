package com.example.handler.message;

import com.example.handler.BotSenderService;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface IMessage {

    void handlerOfMessage(Update update, BotSenderService botSenderService);
}
