package com.example.handler.callback;

import com.example.handler.BotSenderService;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ICallback {

    void handlerOfCallback(Update update, BotSenderService botSenderService);
}
