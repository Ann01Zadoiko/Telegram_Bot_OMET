package com.example.handler.command.vacancy;

import com.example.feature.vacancy.Vacancy;
import com.example.feature.vacancy.VacancyService;
import com.example.handler.BotSenderService;
import com.example.handler.command.vacancy.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.*;
import static com.example.handler.command.vacancy.VacancyState.*;


@RequiredArgsConstructor
@Component
@Slf4j
public class VacancyBotHandler {

    private final VacancyService vacancyService;
    private final Map<Long, UserSession> sessions = new HashMap<>();
    private final BotSenderService sender;

    public void handleUpdate(Update update) {
        if (!update.hasMessage() && !update.hasCallbackQuery()) return;

        Long chatId;
        String text;

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            text = update.getMessage().getText();
        } else {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            text = update.getCallbackQuery().getData(); // ВАЖНО!
        }

        UserSession session = sessions.computeIfAbsent(chatId, id -> new UserSession());

        if ("back".equals(text)) {
            if (!session.getStateHistory().isEmpty()) {
                session.setState(session.getStateHistory().pop());
            }
        } else {
            session.setLastInput(text); // 💡 теперь корректно будет и при callback'ах
        }


        switch (session.getState()) {
            case IDLE_VACANCY -> handleIdleState(chatId, session);
            case VACANCY_ACTION_SELECTION -> handleStopsActionSelection(chatId, session);
            case VACANCY_UPDATE -> updateStops(chatId, session);
        }
    }

    private void handleIdleState(Long chatId, UserSession session) {
        if ("Вакансії".equals(session.getLastInput())) {
            session.pushState(IDLE_VACANCY);
            session.setState(VACANCY_ACTION_SELECTION);
            sender.sendCallbackKeyboard(chatId, "Оберіть дію:", List.of("З досвідом роботи","Без досвіду роботи"), false);

            log.info("{} current state", session.getState().toString());
        }
    }

    private void handleStopsActionSelection(Long chatId, UserSession session) {
        session.setSpecification(session.getLastInput());
        session.pushState(VACANCY_ACTION_SELECTION);
        session.setState(VACANCY_UPDATE);
        sender.sendMessage(chatId, "Введите список вакансій:");
    }


    private void updateStops(Long chat, UserSession session) {
        Vacancy vacancy = vacancyService.getBySpecification(session.getSpecification());
        session.pushState(VACANCY_UPDATE);
        vacancy.setName(session.getLastInput());
        vacancyService.save(vacancy);
        sender.sendMessage(chat, "Дані оновился");
        session.setState(IDLE_VACANCY);
    }
}
