package com.example.handler.callback;

import com.example.constance.Function;
import com.example.handler.BotSenderService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CallbackForNotice implements ICallback{

    @Override
    public void handlerOfCallback(Update update, BotSenderService botSenderService){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals("Керування музеем") && chatId == 391736560L){
            botSenderService.sendMessage(chatId, Function.HANDLER_OF_MUSEUM, messageId, update.getCallbackQuery());
        }
    }
}
