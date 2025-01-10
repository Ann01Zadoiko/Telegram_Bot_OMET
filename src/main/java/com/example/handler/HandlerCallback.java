package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.info.GeneralInfo;
import com.example.constance.info.Study;
import com.example.handler.button.BackButton;
import com.example.handler.button.LinkButtons;
import com.example.handler.button.TracksButtons;
import com.example.handler.button.VacancyButtons;
import com.example.constance.museum.MuseumEnum;
import com.example.constance.museum.MuseumInfo;
import com.example.constance.info.tracks.TracksTrams;
import com.example.constance.info.tracks.TracksTrolls;
import com.example.constance.info.vacancy.Specification;
import com.example.constance.info.vacancy.VacancyWithExperience;
import com.example.constance.info.vacancy.VacancyWithoutExperience;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class HandlerCallback {

    @SneakyThrows
    public void handlerOfMuseum(Update update, TelegramBot bot){
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (MessageChecker.isEnumValueStatus(data)){
            switch (MuseumEnum.valueOf(data)){
                case INFO ->
                {
                    bot.sendMiniApp(chatId, BackButton.getButtons(), MuseumInfo.TEXT_1,messageId);
                    bot.answerCallback(update.getCallbackQuery());
                }

                case SOCIAL_MEDIA_MUSEUM ->
                {
                    bot.sendMiniApp(chatId, LinkButtons.createLinksMuseumButtons(), "Соціальні мережі", messageId);
                    bot.answerCallback(update.getCallbackQuery());
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
                {
                    bot.sendMiniApp(chatId, BackButton.getButtons(), Study.TEXT, messageId);
                    bot.answerCallback(update.getCallbackQuery());
                }

                case SOCIAL_MEDIA ->
                {
                    bot.sendMiniApp(chatId, LinkButtons.createLinksGeneralInfoButtons(), "Соціальні мережі", messageId);
                    bot.answerCallback(update.getCallbackQuery());
                }

                case VACANCY ->
                {
                    bot.sendMiniApp(chatId, VacancyButtons.getButtonsSpecification(), "Вакансії", messageId);
                    bot.answerCallback(update.getCallbackQuery());
                }

                case TRACKS ->
                {
                    bot.sendMiniApp(chatId, TracksButtons.getButtonsTracks(), "Маршрут", messageId);
                    bot.answerCallback(update.getCallbackQuery());
                }
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
                    for (VacancyWithExperience vacancy: VacancyWithExperience.values()){
                        bot.sendMessage(chatId, vacancy.getName(), messageId);
                    }
                    bot.answerCallback(update.getCallbackQuery());
                }
                case WITHOUT_EXPERIENCE -> {
                    for (VacancyWithoutExperience vacancy: VacancyWithoutExperience.values()){
                        bot.sendMessage(chatId, vacancy.getName(), messageId);
                    }
                    bot.answerCallback(update.getCallbackQuery());
                }
            }
        }

        if (data.equals("Трамвай")){
            for (TracksTrams vacancy: TracksTrams.values()){
                bot.sendMessage(chatId, vacancy.getNumber() + ": " + vacancy.getStartEnd(), messageId);
            }
            bot.answerCallback(update.getCallbackQuery());
        }

        if (data.equals("Тролейбус")){
            for (TracksTrolls vacancy: TracksTrolls.values()){
                bot.sendMessage(chatId, vacancy.getNumber() + ": " + vacancy.getStartEnd(), messageId);
            }
            bot.answerCallback(update.getCallbackQuery());
        }
    }
}
