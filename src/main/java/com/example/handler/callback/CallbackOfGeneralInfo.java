package com.example.handler.callback;

import com.example.constance.Function;
import com.example.constance.info.GeneralInfo;
import com.example.constance.info.Study;
import com.example.constance.info.vacancy.Specification;
import com.example.constance.rent.Rent;
import com.example.feature.finding.Finding;
import com.example.feature.finding.FindingService;
import com.example.handler.BotSenderService;
import com.example.handler.MessageChecker;
import com.example.handler.button.GeneralButton;
import com.example.handler.button.LinkButtons;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CallbackOfGeneralInfo implements ICallback{

    private final FindingService findingService;

    @Override
    @SneakyThrows
    public void handlerOfCallback(Update update, BotSenderService botSenderService){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if(MessageChecker.isEnumValueGeneralInfo(data)){
            switch (GeneralInfo.valueOf(data)){
                case STUDY ->
                        botSenderService.sendMessage(chatId, Study.TEXT, messageId, update.getCallbackQuery());

                case SOCIAL_MEDIA ->{
                    String text = "Соціальні мережі:\nFacebook\nhttps://www.facebook.com/kp.oget/";
                    botSenderService.sendMiniApp(chatId, LinkButtons.createLinksGeneralInfoButtons(), text, messageId, update.getCallbackQuery());
                }

                case VACANCY -> {
                    List<String> list = List.of(Specification.WITH_EXPERIENCE.getName(), Specification.WITHOUT_EXPERIENCE.getName());
                    botSenderService.sendMiniApp(chatId, new GeneralButton().getButtons(list, list), "Вакансії", messageId, update.getCallbackQuery());
                }

                case RENT ->{
                    for (Rent rent: Rent.values()){
                        botSenderService.sendMessage(chatId, rent.getDescription());
                        botSenderService.sendPhoto(chatId, rent.getPhoto());
                    }
                }

                case INFO_CENTER ->
                    botSenderService.sendMessage(chatId, Function.INFO_CENTER, messageId, update.getCallbackQuery());


                case FINDING_DOCUMENTS -> {
                    if (findingService.findTopByOrderByIdDesc() == null){
                        botSenderService.sendMessage(chatId, "На даний час немає жодних документів", messageId, update.getCallbackQuery());
                    } else {
                        Finding text = findingService.findTopByOrderByIdDesc();
                        botSenderService.sendMessage(chatId, text.getText(), messageId, update.getCallbackQuery());
                    }

                }
            }
        }
    }
}
