package com.example.handler.callback;

import com.example.feature.transport.Transport;
import com.example.feature.transport.TransportService;
import com.example.handler.BotSenderService;
import com.example.handler.button.TrackButton;
import com.example.handler.printer.TrackPrinter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Comparator;
import java.util.List;

@Slf4j
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

                List<String> numbers = transportService.getNumbersByType("тролейбус");
                numbers.sort(Comparator.comparingInt(n -> Integer.parseInt(n.replace("№", "").trim())));

                for (String number: numbers){
                    if (s[0].equals(number)){
                        Transport byTypeAndNumber = transportService.getByTypeAndNumber("тролейбус", s[0]);
                        log.info("track is work {}", byTypeAndNumber.isWork());

                        if (byTypeAndNumber.isWork()){
                            botSenderService.sendMiniApp(chatId, TrackButton.getButton(byTypeAndNumber), TrackPrinter.print(byTypeAndNumber), messageId,
                                    update.getCallbackQuery());
                        } else {
                            botSenderService.sendMessage(chatId, TrackPrinter.print(byTypeAndNumber), messageId, update.getCallbackQuery());
                        }
                    }
                }
            }

        }

        if (data.contains("trambus")){
            String[] s = data.split(" ");
            if (s[1].equals("trambus")){

                List<String> numbers = transportService.getNumbersByType("трамвай");
                numbers.sort(Comparator.comparingInt(n -> Integer.parseInt(n.replace("№", "").trim())));

                for (String number: numbers){
                    if (s[0].equals(number)){
                        Transport byTypeAndNumber = transportService.getByTypeAndNumber("трамвай", s[0]);
                        if (byTypeAndNumber.isWork()){
                            botSenderService.sendMiniApp(chatId, TrackButton.getButton(byTypeAndNumber), TrackPrinter.print(byTypeAndNumber), messageId,
                                    update.getCallbackQuery());
                        } else {
                            botSenderService.sendMessage(chatId, TrackPrinter.print(byTypeAndNumber), messageId, update.getCallbackQuery());
                        }
                    }
                }
            }

        }
    }
}
