package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.info.GeneralInfo;
import com.example.constance.info.Study;
import com.example.constance.museum.Registration;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.handler.button.*;
import com.example.constance.museum.MuseumEnum;
import com.example.constance.museum.MuseumInfo;
import com.example.constance.info.tracks.TracksTrams;
import com.example.constance.info.tracks.TracksTrolls;
import com.example.constance.info.vacancy.Specification;
import com.example.constance.info.vacancy.VacancyWithExperience;
import com.example.constance.info.vacancy.VacancyWithoutExperience;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Service
public class HandlerCallback {

    private final MuseumService museumService;

    @SneakyThrows
    public void handlerOfMuseum(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (MessageChecker.isEnumValueStatus(data)){
            switch (MuseumEnum.valueOf(data)){
                case INFO ->
                    bot.sendMessage(chatId, MuseumInfo.TEXT_1, messageId, update.getCallbackQuery());

                case SOCIAL_MEDIA_MUSEUM ->
                    bot.sendMiniApp(chatId, LinkButtons.createLinksMuseumButtons(), "Соціальні мережі", messageId, update.getCallbackQuery());

                case REGISTRATION ->{
                    Museum museum = museumService.getById(1L);
                    bot.executeEditMessage(Registration.STEP_1.getText() + museum.getDate() + " на 12:00?", chatId, messageId, MuseumButtons.getButtonsYesOrNo());
                }
            }
        }
    }

    @SneakyThrows
    public void handlerOfGeneralInfo(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if(MessageChecker.isEnumValueGeneralInfo(data)){
            switch (GeneralInfo.valueOf(data)){
                case STUDY ->
                    bot.sendMessage(chatId, Study.TEXT, messageId, update.getCallbackQuery());

                case SOCIAL_MEDIA ->
                    bot.sendMiniApp(chatId, LinkButtons.createLinksGeneralInfoButtons(), "Соціальні мережі", messageId, update.getCallbackQuery());

                case VACANCY ->
                    bot.sendMiniApp(chatId, VacancyButtons.getButtonsSpecification(), "Вакансії", messageId, update.getCallbackQuery());

                case TRACKS ->
                    bot.sendMiniApp(chatId, TracksButtons.getButtonsTracks(), "Маршрут", messageId, update.getCallbackQuery());
            }
        }
    }

    @SneakyThrows
    public void handlerOfVacancy(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (MessageChecker.isEnumValueSpecification(data)){
            switch (Specification.valueOf(data)){
                case WITH_EXPERIENCE -> {
                    String text = "";
                    for (VacancyWithExperience vacancy: VacancyWithExperience.values()){
                       text += "- " + vacancy.getName() + "\n";
                    }
                    bot.executeEditMessage(text, chatId , messageId, BackButton.getButtons("BACK_VACANCY"));
                }
                case WITHOUT_EXPERIENCE -> {
                    String text = "";
                    for (VacancyWithoutExperience vacancy: VacancyWithoutExperience.values()){
                        text += "- " + vacancy.getName() + "\n";
                    }
                    bot.executeEditMessage(text, chatId , messageId, BackButton.getButtons("BACK_VACANCY"));
                }
            }
        }

        if (data.equals("Трамвай")){
            String text = "";
            for (TracksTrams vacancy: TracksTrams.values()){
                text += vacancy.getNumber() + ": " + vacancy.getStartEnd()  + "\n";
            }
            bot.executeEditMessage(text, chatId , messageId, BackButton.getButtons("BACK_TRACKS"));
        }

        if (data.equals("Тролейбус")){
            String text = "";
            for (TracksTrolls vacancy: TracksTrolls.values()){
                text += vacancy.getNumber() + ": " + vacancy.getStartEnd() + "\n";
            }
            bot.executeEditMessage(text, chatId , messageId, BackButton.getButtons("BACK_TRACKS"));
        }
    }

    public void handlerOfBack(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals("BACK_TRACKS")){
            bot.executeEditMessage("Маршрут", chatId, messageId, TracksButtons.getButtonsTracks());
        }

        if (data.equals("BACK_VACANCY")){
            bot.executeEditMessage("Вакансії", chatId,  messageId, VacancyButtons.getButtonsSpecification());
        }
    }

    public void handlerOfYesOrNoMuseum(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (data.equals("YES_MUSEUM")){
            bot.executeEditMessage(Registration.STEP_2.getText(), chatId, messageId, null);
        }

        if (data.equals("NO_MUSEUM")){
            bot.executeEditMessage("Музей", chatId, messageId, MuseumButtons.getButtons());
        }
    }


}
