package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.config.BotConfig;
import com.example.handler.command.complaint.ComplaintBotHandler;
import com.example.handler.command.notice.NoticeBotHandler;
import com.example.handler.command.stops.StopsBotHandler;
import com.example.handler.command.tracks.TracksBotHandler;
import com.example.handler.command.vacancy.VacancyBotHandler;
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
    private final UserStateManager stateManager;
    private final MuseumRegistration museumRegistration;
    private final TracksBotHandler tracksBotHandler;
    private final StopsBotHandler stopsBotHandler;
    private final BotSenderService botSenderService;
    private final VacancyBotHandler vacancyBotHandler;
    private final ComplaintBotHandler complaintBotHandler;
    private final NoticeBotHandler noticeBotHandler;


    public void answerToCallback(Update update)  {
        TelegramBot bot = new TelegramBot(
                config,
                stateManager,
                new BotHandler(callback,message,config, stateManager, museumRegistration, tracksBotHandler,
                        stopsBotHandler, botSenderService, vacancyBotHandler, complaintBotHandler,noticeBotHandler),
                tracksBotHandler,
                stopsBotHandler,
                botSenderService,
                vacancyBotHandler,
                complaintBotHandler,
                noticeBotHandler);

        callback.handlerOfGeneralInfo(update, bot);
        callback.handlerOfMuseum(update, bot);
        callback.handlerOfVacancy(update, bot);
        callback.handlerOfBack(update, bot);
        callback.handlerOfYesOrNoMuseum(update, bot);
        callback.handlerOfTracks(update, bot);
        callback.handlerForStops(update, bot);
    }

    @SneakyThrows
    public void answerToMessage(Update update, UserStateManager stateManager){
        TelegramBot bot = new TelegramBot(
                config,
                stateManager,
                new BotHandler(callback,message,config, stateManager, museumRegistration, tracksBotHandler,
                        stopsBotHandler, botSenderService, vacancyBotHandler, complaintBotHandler,noticeBotHandler),
                tracksBotHandler,
                stopsBotHandler,
                botSenderService,
                vacancyBotHandler,
                complaintBotHandler,
                noticeBotHandler);

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
        message.handlerOfMuseumAndTracks(update, bot);
    }


}
