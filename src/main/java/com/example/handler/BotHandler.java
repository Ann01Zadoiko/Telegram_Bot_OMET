package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.config.BotConfig;
import com.example.feature.complaint.ComplaintService;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotHandler {

    private final HandlerCallback callback;
    private final HandlerMessage message;
    private final BotConfig config;
    private final MuseumService museumService;
    private final UserService userService;
    private final ComplaintService complaintService;

    public void answerToCallback(Update update){
        TelegramBot bot = new TelegramBot(config, museumService, userService, complaintService);

        callback.handlerOfGeneralInfo(update, bot);
        callback.handlerOfMuseum(update, bot);
        callback.handlerOfVacancy(update, bot);
        callback.handlerOfBack(update, bot);
        callback.handlerOfYesOrNoMuseum(update, bot);
        callback.handlerOfSkip(update, bot);
    }

    public void answerToMessage(Update update){
        TelegramBot bot = new TelegramBot(config, museumService, userService, complaintService);

        message.handlerOfGeneralInfo(update, bot);
        message.handlerOfMuseum(update, bot);
        message.handlerOfRent(update, bot);
        message.handlerOfStart(update, bot);
        message.handlerOfNewDayToMuseum(update, bot);
        message.handlerOfFullNameForMuseum(update, bot);
        message.handlerOfPhoneNumberForMuseum(update, bot);
        message.handlerOfCountOfPeopleForMuseum(update, bot);
        message.handlerOfCloseExhibition(update, bot);
        message.handlerOfHelp(update, bot);
        message.handlerOfShow(update, bot);
        message.handlerOfComplaintButton(update, bot);
        message.handlerOfFullNameComplaint(update, bot);
        message.handlerOfPhoneNumberComplaint(update, bot);
        message.handlerOfMessageOfComplaint(update, bot);
        message.handlerOfCommandVacancy(update, bot);
    }
}
