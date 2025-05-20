package com.example.handler.message;

import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.handler.BotSenderService;
import com.example.handler.MessageChecker;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class MessageOfCloseExhibition implements IMessage{

    private final MuseumService museumService;

    @Override
    @SneakyThrows
    public void handlerOfMessage(Update update, BotSenderService botSenderService){
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        Museum museum = museumService.getById(1L);

        if (MessageChecker.isClose(text) && chatId == 391736560L){
            museum.setClose(true);
            museumService.save(museum);

            botSenderService.sendMessage(chatId, "Ви закрили запись на ексеурсію");
        }
    }
}
