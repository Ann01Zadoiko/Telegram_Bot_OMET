package com.example.handler.callback;

import com.example.feature.stop.Stop;
import com.example.feature.stop.StopService;
import com.example.feature.transport.Transport;
import com.example.feature.transport.TransportService;
import com.example.handler.BotSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class CallbackForStops implements ICallback{

    private final TransportService transportService;
    private final StopService stopService;

    @Override
    public void handlerOfCallback(Update update, BotSenderService botSenderService){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.startsWith("STOPS")){
            String[] s = data.split(" ");
            Transport byTypeAndNumber = transportService.getByTypeAndNumber(s[1], s[2]);
            Stop stop = stopService.getByTransport(byTypeAndNumber);

            if (stop == null){
                botSenderService.sendMessage(chatId, "На даний час немає повного списку зупинок", messageId, update.getCallbackQuery());
            } else {
                botSenderService.sendMessage(chatId, stop.getStopAcross(), messageId, update.getCallbackQuery());
                botSenderService.sendMessage(chatId, stop.getStopRightBack(), messageId, update.getCallbackQuery());
            }
        }
    }
}
