package com.example.handler.message;

import com.example.constance.Button;
import com.example.constance.museum.MuseumEnum;
import com.example.handler.BotSenderService;
import com.example.handler.button.GeneralButton;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

@Service
public class MessageOfMuseum implements IMessage{

    @Override
    @SneakyThrows
    public void handlerOfMessage(Update update, BotSenderService botSenderService){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.equals(Button.MUSEUM.getFullName())){
            List<MuseumEnum> list = Arrays.stream(MuseumEnum.values()).toList();
            List<String> listText = list.stream().map(MuseumEnum::getFullName).toList();
            List<String> listCallback = list.stream().map(MuseumEnum::name).toList();

            botSenderService.sendMessage(chatId, Button.MUSEUM.getFullName(), new GeneralButton().getButtons(listText, listCallback));
        }
    }
}
