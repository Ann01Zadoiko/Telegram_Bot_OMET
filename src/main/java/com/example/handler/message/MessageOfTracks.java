package com.example.handler.message;

import com.example.constance.Button;
import com.example.handler.BotSenderService;
import com.example.handler.button.GeneralButton;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class MessageOfTracks implements IMessage{

    @Override
    @SneakyThrows
    public void handlerOfMessage(Update update, BotSenderService botSenderService){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Button.INFO_TRACKS.getFullName())){
            List<String> list = List.of("Трамвай", "Тролейбус");

            botSenderService.sendMessage(chatId, "Оберіть тип транспортного засобу:", new GeneralButton().getButtons(list, list));
        }
    }
}
