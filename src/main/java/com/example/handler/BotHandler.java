package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.config.BotConfig;
import com.example.feature.complaint.ComplaintService;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.UserService;
import com.example.feature.vacancy.VacancyService;
import com.example.registration.ComplaintRegistration;
import com.example.registration.MuseumRegistration;
import com.example.registration.RegistrationType;
import com.example.registration.UserStateManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotHandler {

    private final HandlerCallback callback;
    private final HandlerMessage message;
    private final BotConfig config;
 //   private final MuseumService museumService;
 //   private final UserService userService;
 //   private final ComplaintService complaintService;
    private final UserStateManager stateManager;
    private final MuseumRegistration museumRegistration;
    private final ComplaintRegistration complaintRegistration;
//    private final VacancyService vacancyService;
    private final HandlerPhoto handlerOfPhoto;

    public void answerToCallback(Update update){
      //  TelegramBot bot = new TelegramBot(config, museumService, userService, complaintService, stateManager, museumRegistration, complaintRegistration, vacancyService);

        TelegramBot bot = new TelegramBot(config, stateManager,
                new BotHandler(callback,message,config, stateManager, museumRegistration, complaintRegistration, handlerOfPhoto));

        callback.handlerOfGeneralInfo(update, bot);
        callback.handlerOfMuseum(update, bot);
        callback.handlerOfVacancy(update, bot);
        callback.handlerOfBack(update, bot);
        callback.handlerOfYesOrNoMuseum(update, bot);
        callback.handlerOfSkip(update, bot);
        callback.handlerOfYesOrNoComplaint(update, bot);
    }

    @SneakyThrows
    public void answerToMessage(Update update, UserStateManager stateManager){
     //   TelegramBot bot = new TelegramBot(config, museumService, userService, complaintService, stateManager, museumRegistration, complaintRegistration, vacancyService);

        TelegramBot bot = new TelegramBot(config, stateManager,
                new BotHandler(callback,message,config, stateManager, museumRegistration, complaintRegistration, handlerOfPhoto));

        // Если пользователь в процессе регистрации, обрабатываем только нужный тип регистрации
        if (stateManager.isUserRegistering(update.getMessage().getChatId())) {
            if (update.getMessage().getText().equalsIgnoreCase("/cancel")) {
                stateManager.removeUser(update.getMessage().getChatId());
                bot.sendMessage(update.getMessage().getChatId(), "❌ Регистрация отменена.");
            } else {
                log.info("🟡 Пользователь {} находится в процессе регистрации.", update.getMessage().getChatId());

                RegistrationType type = stateManager.getUserRegistrationType(update.getMessage().getChatId());

                if (type == RegistrationType.MUSEUM) {
                    museumRegistration.processRegistrationStep(update.getMessage().getChatId(), update.getMessage().getText(), bot);
                }

                if (type == RegistrationType.COMPLAINT) {
                    complaintRegistration.processRegistrationStep(update.getMessage().getChatId(), update.getMessage().getText(), bot);
                }

            }
            return; // Прерываем дальнейшую обработку
        }

        message.handlerOfGeneralInfo(update, bot);
        message.handlerOfMuseum(update, bot);
        message.handlerOfRent(update, bot);
        message.handlerOfStart(update, bot);
        message.handlerOfNewDayToMuseum(update, bot);
        message.handlerOfCloseExhibition(update, bot);
        message.handlerOfShow(update, bot);
        message.handlerOfComplaint(update, bot);
        message.handlerOfVacancyCommand(update, bot);
        message.handlerOfVacancyChange(update, bot);
    }

    public void answerToPhoto(Update update){
       //TelegramBot bot = new TelegramBot(config, museumService, userService, complaintService, stateManager, museumRegistration, complaintRegistration, vacancyService);

        TelegramBot bot = new TelegramBot(config, stateManager,
                new BotHandler(callback,message,config, stateManager, museumRegistration, complaintRegistration, handlerOfPhoto));
        handlerOfPhoto.handlerOfPhoto(update, bot);
    }

}
