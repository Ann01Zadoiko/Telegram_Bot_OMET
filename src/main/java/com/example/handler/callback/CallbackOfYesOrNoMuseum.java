package com.example.handler.callback;

import com.example.constance.museum.MuseumEnum;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.handler.BotSenderService;
import com.example.handler.button.GeneralButton;
import com.example.registration.MuseumRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackOfYesOrNoMuseum implements ICallback{
    private final MuseumService museumService;
    private final MuseumRegistration museumRegistration;

    @Override
    public void handlerOfCallback(Update update, BotSenderService botSenderService){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals("YES_MUSEUM")){
            Museum museum = new Museum();
            museum.setDate(museumService.showDate());
            museum.setChatId(chatId);
            museumService.save(museum);
            museumRegistration.startRegistration(chatId, botSenderService);
        }

        if (data.equals("NO_MUSEUM")){
            List<MuseumEnum> list = Arrays.stream(MuseumEnum.values()).toList();
            List<String> listText = list.stream().map(MuseumEnum::getFullName).toList();
            List<String> listCallback = list.stream().map(MuseumEnum::name).toList();

            botSenderService.executeEditMessage("Музей", chatId, messageId, new GeneralButton().getButtons(listText, listCallback));
        }
    }
}
