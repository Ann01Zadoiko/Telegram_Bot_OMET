package com.example.handler.message;


import com.example.feature.notice.NoticeService;
import com.example.handler.BotSenderService;
import com.example.handler.button.GeneralButton;
import com.example.handler.printer.NoticePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageOfMuseumAndTracks implements IMessage{

    private final NoticeService noticeService;

    @Override
    public void handlerOfMessage(Update update, BotSenderService botSenderService){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals("Керування ботом") && chatId == 391736560L){
            String answer = "Оберіть дію:";

            List<String> list = new ArrayList<>();
            list.add("Керування музеем");
            list.add("Вакансії");
            list.add("Зупинки");
            list.add("Маршрут");
            list.add("Додати повідомлення");
            list.add("Додати документи");

            botSenderService.sendMessage(chatId, answer, new GeneralButton().getButtons(list, list));
        }

        if (text.equals("Термінові повідомлення")){
            String print = new NoticePrinter(noticeService).print();
            botSenderService.sendMessage(chatId, print);
        }
    }
}
