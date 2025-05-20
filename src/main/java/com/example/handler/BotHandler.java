package com.example.handler;

import com.example.feature.finding.FindingService;
import com.example.feature.museum.MuseumService;
import com.example.feature.notice.NoticeService;
import com.example.feature.stop.StopService;
import com.example.feature.transport.TransportService;
import com.example.feature.vacancy.VacancyService;
import com.example.handler.callback.*;
import com.example.handler.message.*;
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

    private final MuseumRegistration museumRegistration;
    private final BotSenderService botSenderService;
    private final MuseumService museumService;
    private final NoticeService noticeService;
    private final TransportService transportService;
    private final StopService stopService;
    private final VacancyService vacancyService;
    private final FindingService findingService;


    public void answerToCallback(Update update)  {

        new CallbackOfGeneralInfo(findingService).handlerOfCallback(update, botSenderService);
        new CallbackOfMuseum(museumService).handlerOfCallback(update, botSenderService);
        new CallbackOfVacancy(vacancyService, transportService).handlerOfCallback(update, botSenderService);
        new CallbackOfBack().handlerOfCallback(update, botSenderService);
        new CallbackOfYesOrNoMuseum(museumService, museumRegistration).handlerOfCallback(update, botSenderService);
        new CallbackOfTracks(transportService).handlerOfCallback(update, botSenderService);
        new CallbackForStops(transportService, stopService).handlerOfCallback(update, botSenderService);
        new CallbackForNotice().handlerOfCallback(update, botSenderService);
    }

    @SneakyThrows
    public void answerToMessage(Update update, UserStateManager stateManager){

        if (stateManager.isUserRegistering(update.getMessage().getChatId())) {
            if (update.getMessage().getText().equalsIgnoreCase("/cancel")) {
                stateManager.removeUser(update.getMessage().getChatId());
                botSenderService.sendMessage(update.getMessage().getChatId(), "❌ Регистрация отменена.");
            } else {
                log.info("🟡 Пользователь {} находится в процессе регистрации.", update.getMessage().getChatId());

                RegistrationType type = stateManager.getUserRegistrationType(update.getMessage().getChatId());

                if (type == RegistrationType.MUSEUM) {
                    museumRegistration.processRegistrationStep(update.getMessage().getChatId(), update.getMessage().getText(), botSenderService);
                }


            }
            return;
        }

        new MessageOfGeneralInfo().handlerOfMessage(update, botSenderService);
        new MessageOfMuseum().handlerOfMessage(update, botSenderService);
        new MessageOfTracks().handlerOfMessage(update, botSenderService);
        new MessageOfStart().handlerOfMessage(update, botSenderService);
        new MessageOfNewDayToMuseum(museumService).handlerOfMessage(update, botSenderService);
        new MessageOfCloseExhibition(museumService).handlerOfMessage(update, botSenderService);
        new MessageOfShow(museumService).handlerOfMessage(update, botSenderService);
        new MessageOfMuseumAndTracks(noticeService).handlerOfMessage(update, botSenderService);
    }
}
