package com.example.handler.command.tracks;

import com.example.feature.transport.Transport;
import com.example.feature.transport.TransportService;
import com.example.handler.BotSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.*;
import static com.example.handler.command.BotState.*;

@Slf4j
@Component
public class TracksBotHandler {
    private final TransportService transportService;
    private final Map<Long, UserSession> sessions = new HashMap<>();
    private final BotSenderService sender;

    public TracksBotHandler(TransportService transportService, BotSenderService sender) {
        this.transportService = transportService;
        this.sender = sender;
    }

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
            case IDLE_TRACK -> handleIdleState(chatId, session);
            case TRACKS_ACTION_SELECTION -> handleTrackActionSelection(chatId, session);
            case TRACK_SELECT_TYPE -> handleTrackSelectType(chatId, session);
            case TRACK_ENTER_NUMBER, TRACK_ENTER_STOP_END, TRACK_ENTER_TIME,
                 TRACK_ENTER_LINK, TRACK_ENTER_INTERVAL -> handleAddTrackState(chatId, session);
            case TRACK_UPDATE_SELECT_FIELD, TRACK_UPDATE_ENTER_VALUE,
                 TRACK_UPDATE_ENTER_NEW_VALUE -> handleUpdateTrackState(chatId, session);
            case TRACK_DELETE_SELECT -> handleDeleteTrackState(chatId, session);
        }
    }

    private void handleIdleState(Long chatId, UserSession session) {
        if ("/tracks".equals(session.getLastInput())) {
            session.pushState(IDLE_TRACK);
            session.setState(TRACKS_ACTION_SELECTION);
            Map<String, String> actions = new LinkedHashMap<>();
            actions.put("track_add", "track_add");
            actions.put("track_update", "track_update");
            actions.put("track_delete", "track_delete");
            sender.sendCallbackKeyboard(chatId, "Выберите действие:", List.of("track_add", "track_update","track_delete" ), false);

            log.info("{} current state", session.getState().toString());
        }
    }

    private void handleTrackActionSelection(Long chatId, UserSession session) {
        session.setPreviousAction(session.getLastInput());
        session.pushState(TRACKS_ACTION_SELECTION);
        session.setState(TRACK_SELECT_TYPE);
        sender.sendCallbackKeyboard(chatId, "Выберите тип транспорта:", List.of("трамвай", "тролейбус"), false);

        log.info("{} current state", session.getState().toString());
        log.info("{} previous action", session.getPreviousAction());

    }

    private void handleTrackSelectType(Long chatId, UserSession session) {
        session.setTransportType(session.getLastInput());
        session.pushState(TRACK_SELECT_TYPE);
        List<String> numbers = transportService.getNumbersByType(session.getTransportType());

        switch (session.getPreviousAction()) {
            case "track_add" -> {
                session.setState(TRACK_ENTER_NUMBER);
                sender.sendMessage(chatId, "Введите номер маршрута:");
            }
            case "track_update" -> {
                session.setState(TRACK_UPDATE_SELECT_FIELD);
                sender.sendCallbackKeyboard(chatId, "Выберите номер маршрута:", numbers, false);
            }
            case "track_delete" -> {
                session.setState(TRACK_DELETE_SELECT);
                sender.sendCallbackKeyboard(chatId, "Выберите номер маршрута:", numbers, false);
            }
        }

        log.info("{} current state", session.getState().toString());
        log.info("{} last input", session.getLastInput());
    }

    private void handleAddTrackState(Long chatId, UserSession session) {
        switch (session.getState()) {
            case TRACK_ENTER_NUMBER -> {
                session.setTrackNumber(session.getLastInput().trim());
                session.pushState(TRACK_ENTER_NUMBER);
                session.setState(TRACK_ENTER_STOP_END);
                sender.sendMessage(chatId, "Введите конечные остановки:");
            }
            case TRACK_ENTER_STOP_END -> {
                session.setStopsStartEnd(session.getLastInput());
                session.pushState(TRACK_ENTER_STOP_END);
                session.setState(TRACK_ENTER_TIME);
                sender.sendMessage(chatId, "Введите время начала и конца:");
            }
            case TRACK_ENTER_TIME -> {
                session.setTimeStartEnd(session.getLastInput());
                session.pushState(TRACK_ENTER_TIME);
                session.setState(TRACK_ENTER_LINK);
                sender.sendMessage(chatId, "Введите ссылку:");
            }
            case TRACK_ENTER_LINK -> {
                session.setLink(session.getLastInput());
                session.pushState(TRACK_ENTER_LINK);
                session.setState(TRACK_ENTER_INTERVAL);
                sender.sendMessage(chatId, "Введите интервал:");
            }
            case TRACK_ENTER_INTERVAL -> {
                session.setInterval(session.getLastInput());
                saveTransport(session);
                session.setState(IDLE_TRACK);
                sender.sendMessage(chatId, "Маршрут добавлен ✅");
            }
        }
        log.info("{} current state", session.getState().toString());
    }

    private void handleUpdateTrackState(Long chatId, UserSession session) {
        switch (session.getState()) {
            case TRACK_UPDATE_SELECT_FIELD -> {
                session.setTrackNumber(session.getLastInput());
                session.pushState(TRACK_UPDATE_SELECT_FIELD);
                session.setState(TRACK_UPDATE_ENTER_VALUE);
                sender.sendCallbackKeyboard(chatId, "Что хотите обновить?",
                        List.of("номер маршрута", "конечные остановки", "время работы", "ссылка", "интервал", "Завершить"), false);
            }
            case TRACK_UPDATE_ENTER_VALUE -> {
                if ("Завершить".equals(session.getLastInput())) {
                    session.setState(IDLE_TRACK);
                    sender.sendMessage(chatId, "Обновление завершено ✅");
                    return;
                }
                session.setUpdateField(session.getLastInput());
                session.pushState(TRACK_UPDATE_ENTER_VALUE);
                session.setState(TRACK_UPDATE_ENTER_NEW_VALUE); // ДОБАВЬ ЭТО СОСТОЯНИЕ В ENUM
                sender.sendMessage(chatId, "Введите новое значение:");
            }
            case TRACK_UPDATE_ENTER_NEW_VALUE -> {
                String newValue = session.getLastInput();
                updateTransport(session, newValue); // вызываем обновление
                session.setState(TRACK_UPDATE_ENTER_VALUE); // возвращаемся к выбору следующего поля
                sender.sendCallbackKeyboard(chatId, "Что хотите обновить ещё?",
                        List.of("номер маршрута", "конечные остановки", "время работы", "ссылка", "интервал", "Завершить"), false);
            }
        }

        log.info("{} current state", session.getState().toString());
    }

    private void handleDeleteTrackState(Long chatId, UserSession session) {
        transportService.deleteByTypeAndNumberOfTrack(session.getTransportType(), session.getLastInput());
        session.setState(IDLE_TRACK);
        sender.sendMessage(chatId, "Маршрут удалён ✅");

        log.info("{} current state", session.getState().toString());
        log.info(transportService.getByTypeAndNumber(session.getTransportType(), session.getLastInput()).get().toString());
    }

    private void saveTransport(UserSession session) {
        Transport t = new Transport();
        t.setType(session.getTransportType());
        t.setNumberOfTrack(session.getTrackNumber());
        t.setStopsStartEnd(session.getStopsStartEnd());
        t.setTime(session.getTimeStartEnd());
        t.setLink(session.getLink());
        t.setInterval(session.getInterval());

        transportService.add(t);

        log.info(t.toString());
    }

    private void updateTransport(UserSession session, String newValue) {
        Optional<Transport> optional = transportService.getByTypeAndNumber(session.getTransportType(), session.getTrackNumber());
        if (optional.isEmpty()) return;

        Transport transport = optional.get();
        switch (session.getUpdateField()) {
            case "номер маршрута" -> transport.setNumberOfTrack(newValue);
            case "конечные остановки" -> transport.setStopsStartEnd(newValue);
            case "время работы" -> transport.setTime(newValue);
            case "ссылка" -> transport.setLink(newValue);
            case "интервал" -> transport.setInterval(newValue);
        }

        transportService.update(transport); // не забудь реализовать метод update
        log.info("Updated transport: {}", transport);
    }
}
