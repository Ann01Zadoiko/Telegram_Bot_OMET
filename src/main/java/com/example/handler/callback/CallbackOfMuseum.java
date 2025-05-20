package com.example.handler.callback;

import com.example.constance.museum.MuseumEnum;
import com.example.constance.museum.MuseumInfo;
import com.example.constance.museum.Registration;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.handler.BotSenderService;
import com.example.handler.MessageChecker;
import com.example.handler.button.GeneralButton;
import com.example.handler.button.LinkButtons;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackOfMuseum implements ICallback{

    private final MuseumService museumService;

    @Override
    @SneakyThrows
    public void handlerOfCallback(Update update, BotSenderService botSenderService){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (MessageChecker.isEnumValueStatus(data)){
            switch (MuseumEnum.valueOf(data)){
                case INFO ->
                        botSenderService.sendMessage(chatId, MuseumInfo.TEXT_1, messageId, update.getCallbackQuery());

                case SOCIAL_MEDIA_MUSEUM ->{
                    String text = "Соціальні мережі:\nFacebook\nhttps://www.facebook.com/museumoget/";
                    botSenderService.sendMiniApp(chatId, LinkButtons.createLinksMuseumButtons(), text, messageId, update.getCallbackQuery());
                }

                case REGISTRATION ->{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

                    Museum museum = museumService.getById(1L);
                    String textClose = Registration.STEP_9.getText() + "\n" + Registration.STEP_10.getText();
                    String textOpen =  Registration.STEP_1.getText() + museum.getDate().format(formatter) + " на 12:00?";

                    if (museum.isClose()){
                        botSenderService.executeEditMessage(textClose, chatId, messageId, new GeneralButton().getButtons(List.of("Назад"), List.of("BACK_MUSEUM")));
                    } else {
                        List<String> listText = List.of("Так", "Ні");
                        List<String> listCallback = List.of("YES_MUSEUM", "NO_MUSEUM");

                        botSenderService.executeEditMessage(textOpen, chatId, messageId, new GeneralButton().getButtons(listText, listCallback));
                    }
                }
            }
        }
    }
}
