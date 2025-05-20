package com.example.handler.callback;

import com.example.constance.Button;
import com.example.constance.info.vacancy.Specification;
import com.example.constance.museum.MuseumEnum;
import com.example.handler.BotSenderService;
import com.example.handler.button.GeneralButton;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

@Service
public class CallbackOfBack implements ICallback{

    @Override
    public void handlerOfCallback(Update update, BotSenderService botSenderService){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals("BACK_TRACKS")){
            List<String> list = List.of("Трамвай", "Тролейбус");

            botSenderService.executeEditMessage("Маршрут", chatId, messageId, new GeneralButton().getButtons(list, list));
        }

        if (data.equals("BACK_VACANCY")){
            List<String> list = List.of(Specification.WITH_EXPERIENCE.getName(), Specification.WITHOUT_EXPERIENCE.getName());
            botSenderService.executeEditMessage("Вакансії", chatId,  messageId, new GeneralButton().getButtons(list, list));
        }

        if (data.equals("BACK_MUSEUM")){
            List<MuseumEnum> list = Arrays.stream(MuseumEnum.values()).toList();
            List<String> listText = list.stream().map(MuseumEnum::getFullName).toList();
            List<String> listCallback = list.stream().map(MuseumEnum::name).toList();

            botSenderService.executeEditMessage(Button.MUSEUM.getFullName(), chatId, messageId, new GeneralButton().getButtons(listText, listCallback));
        }
    }
}
