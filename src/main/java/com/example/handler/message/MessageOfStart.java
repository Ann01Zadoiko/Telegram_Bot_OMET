package com.example.handler.message;

import com.example.constance.Function;
import com.example.handler.BotSenderService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageOfStart implements IMessage {

    @Override
    @SneakyThrows
    public void handlerOfMessage(Update update, BotSenderService botSenderService){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Function.START)) {
            botSenderService.sendMessage(chatId, Function.WELCOME);
        }
    }
}
