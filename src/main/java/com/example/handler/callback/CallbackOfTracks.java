package com.example.handler.callback;

import com.example.feature.transport.Transport;
import com.example.feature.transport.TransportService;
import com.example.handler.BotSenderService;
import com.example.handler.button.TrackButton;
import com.example.handler.printer.TrackPrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class CallbackOfTracks implements ICallback{

    private final TransportService transportService;

    @Override
    public void handlerOfCallback(Update update, BotSenderService botSenderService){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.contains("trollbus")){
            String[] s = data.split(" ");
            if (s[1].equals("trollbus")){
                for (String number: transportService.getNumbersByType("тролейбус")){
                    if (s[0].equals(number)){
                        Transport byTypeAndNumber = transportService.getByTypeAndNumber("тролейбус", s[0]);
                        botSenderService.sendMiniApp(chatId, TrackButton.getButton(byTypeAndNumber), TrackPrinter.print(byTypeAndNumber), messageId,
                                update.getCallbackQuery());
                    }
                }
            }

        }

        if (data.contains("trambus")){
            String[] s = data.split(" ");
            if (s[1].equals("trambus")){
                for (String number: transportService.getNumbersByType("трамвай")){
                    if (s[0].equals(number)){
                        Transport byTypeAndNumber = transportService.getByTypeAndNumber("трамвай", s[0]);
                        botSenderService.sendMiniApp(chatId, TrackButton.getButton(byTypeAndNumber), TrackPrinter.print(byTypeAndNumber), messageId,
                                update.getCallbackQuery());
                    }
                }
            }

        }
    }
}
