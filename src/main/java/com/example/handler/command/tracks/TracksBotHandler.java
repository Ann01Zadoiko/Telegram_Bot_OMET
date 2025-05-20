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
            session.setLastInput(text);
        }

        switch (session.getState()) {
            case IDLE_TRACK -> handleIdleState(chatId, session);
            case TRACKS_ACTION_SELECTION -> handleTrackActionSelection(chatId, session);
            case TRACK_SELECT_TYPE -> handleTrackSelectType(chatId, session);
            case TRACK_ENTER_NUMBER, TRACK_ENTER_STOP_END, TRACK_ENTER_TIME_START,
                 TRACK_ENTER_TIME_END, TRACK_ENTER_LINK, TRACK_ENTER_INTERVAL_WEEKDAYS,
                 TRACK_ENTER_INTERVAL_WEEKEND-> handleAddTrackState(chatId, session);
            case TRACK_UPDATE_SELECT_FIELD, TRACK_UPDATE_ENTER_VALUE,
                 TRACK_UPDATE_ENTER_NEW_VALUE -> handleUpdateTrackState(chatId, session);
            case TRACK_DELETE_SELECT -> handleDeleteTrackState(chatId, session);
        }
    }

    private void handleIdleState(Long chatId, UserSession session) {
        if ("Маршрут".equals(session.getLastInput()) && chatId == 391736560L) {
            session.pushState(IDLE_TRACK);
            session.setState(TRACKS_ACTION_SELECTION);
            Map<String, String> actions = new LinkedHashMap<>();
            actions.put("додати", "track_add");
            actions.put("оновити", "track_update");
            actions.put("видалити", "track_delete");
            sender.sendInlineKeyboard(chatId, "Оберіть дію:", actions);

        }
    }

    private void handleTrackActionSelection(Long chatId, UserSession session) {
        session.setPreviousAction(session.getLastInput());
        session.pushState(TRACKS_ACTION_SELECTION);
        session.setState(TRACK_SELECT_TYPE);
        sender.sendCallbackKeyboard(chatId, "Оберіть тип транспортного засобу:", List.of("трамвай", "тролейбус"), false);

    }

    private void handleTrackSelectType(Long chatId, UserSession session) {
        session.setTransportType(session.getLastInput());
        session.pushState(TRACK_SELECT_TYPE);
        List<String> numbers = transportService.getNumbersByType(session.getTransportType());

        switch (session.getPreviousAction()) {
            case "track_add" -> {
                session.setState(TRACK_ENTER_NUMBER);
                sender.sendMessage(chatId, "Введить номер маршруту:");
            }
            case "track_update" -> {
                session.setState(TRACK_UPDATE_SELECT_FIELD);
                sender.sendCallbackKeyboard(chatId, "Оберіть номер маршруту:", numbers, false);
            }
            case "track_delete" -> {
                session.setState(TRACK_DELETE_SELECT);
                sender.sendCallbackKeyboard(chatId, "Оберіть номер маршруту:", numbers, false);
            }
        }

    }

    private void handleAddTrackState(Long chatId, UserSession session) {
        switch (session.getState()) {
            case TRACK_ENTER_NUMBER -> {
                session.setTrackNumber(session.getLastInput().trim());
                session.pushState(TRACK_ENTER_NUMBER);
                session.setState(TRACK_ENTER_STOP_END);
                sender.sendMessage(chatId, "Введить кінцеві зупинки:");
            }
            case TRACK_ENTER_STOP_END -> {
                session.setStopsStartEnd(session.getLastInput());
                session.pushState(TRACK_ENTER_STOP_END);
                session.setState(TRACK_ENTER_TIME_START);
                sender.sendMessage(chatId, "Введить час початку роботи маршруту:");
            }
            case TRACK_ENTER_TIME_START -> {
                session.setTimeStart(session.getLastInput());
                session.pushState(TRACK_ENTER_TIME_START);
                session.setState(TRACK_ENTER_TIME_END);
                sender.sendMessage(chatId, "Введить час завершення роботи маршруту:");
            }
            case TRACK_ENTER_TIME_END -> {
                session.setTimeEnd(session.getLastInput());
                session.pushState(TRACK_ENTER_TIME_END);
                session.setState(TRACK_ENTER_LINK);
                sender.sendMessage(chatId, "Введить посилання на маршрут:");
            }
            case TRACK_ENTER_LINK -> {
                session.setLink(session.getLastInput());
                session.pushState(TRACK_ENTER_LINK);
                session.setState(TRACK_ENTER_INTERVAL_WEEKDAYS);
                sender.sendMessage(chatId, "Введить інтевал у будні дні:");
            }
            case TRACK_ENTER_INTERVAL_WEEKDAYS -> {
                session.setIntervalWeekdays(session.getLastInput());
                session.pushState(TRACK_ENTER_INTERVAL_WEEKDAYS);
                session.setState(TRACK_ENTER_INTERVAL_WEEKEND);
                sender.sendMessage(chatId, "Введить інтевал у вихідні дні:");
            }
            case TRACK_ENTER_INTERVAL_WEEKEND -> {
                session.setIntervalWeekend(session.getLastInput());
                saveTransport(session);
                session.setState(IDLE_TRACK);
                sender.sendMessage(chatId, "Маршрут додан ✅");
            }
        }

    }

    private void handleUpdateTrackState(Long chatId, UserSession session) {
        List<String> list = List.of(
                "номер маршрута",
                "кіневі зупинки",
                "початок роботи",
                "останнє відправлення",
                "посилання",
                "інтервал у будні дні",
                "інтервал у вихідні",
                "Завершити");

        switch (session.getState()) {
            case TRACK_UPDATE_SELECT_FIELD -> {
                session.setTrackNumber(session.getLastInput());
                session.pushState(TRACK_UPDATE_SELECT_FIELD);
                session.setState(TRACK_UPDATE_ENTER_VALUE);
                sender.sendCallbackKeyboard(chatId, "Що бажаєте оновити?",
                        list, false);
            }
            case TRACK_UPDATE_ENTER_VALUE -> {
                if ("Завершить".equals(session.getLastInput())) {
                    session.setState(IDLE_TRACK);
                    sender.sendMessage(chatId, "Оновлення завершено ✅");
                    return;
                }
                session.setUpdateField(session.getLastInput());
                session.pushState(TRACK_UPDATE_ENTER_VALUE);
                session.setState(TRACK_UPDATE_ENTER_NEW_VALUE); // ДОБАВЬ ЭТО СОСТОЯНИЕ В ENUM
                sender.sendMessage(chatId, "Введить нове значення:");
            }
            case TRACK_UPDATE_ENTER_NEW_VALUE -> {
                String newValue = session.getLastInput();
                updateTransport(session, newValue); // вызываем обновление
                session.setState(TRACK_UPDATE_ENTER_VALUE); // возвращаемся к выбору следующего поля
                sender.sendCallbackKeyboard(chatId, "Що бажаєте оновити ще?", list, false);
            }
        }

    }

    private void handleDeleteTrackState(Long chatId, UserSession session) {
        transportService.deleteByTypeAndNumberOfTrack(session.getTransportType(), session.getLastInput());
        session.setState(IDLE_TRACK);
        sender.sendMessage(chatId, "Маршрут видален ✅");

    }

    private void saveTransport(UserSession session) {
        Transport t = new Transport();
        t.setType(session.getTransportType());
        t.setNumberOfTrack(session.getTrackNumber());
        t.setStopsStartEnd(session.getStopsStartEnd());
        t.setTimeStart(session.getTimeStart());
        t.setTimeEnd(session.getTimeEnd());
        t.setLink(session.getLink());
        t.setIntervalWeekdays(session.getIntervalWeekdays());
        t.setIntervalWeekend(session.getIntervalWeekend());

        transportService.add(t);

        log.info(t.toString());
    }

    private void updateTransport(UserSession session, String value) {
        Transport transport = transportService.getByTypeAndNumber(session.getTransportType(), session.getTrackNumber());

        switch (session.getUpdateField()) {
            case "номер маршрута" -> transport.setNumberOfTrack(value);
            case "кіневі зупинки" -> transport.setStopsStartEnd(value);
            case "початок роботи" -> transport.setTimeStart(value);
            case "останнє відправлення" -> transport.setTimeEnd(value);
            case "посилання" -> transport.setLink(value);
            case "інтервал у будні дні" -> transport.setIntervalWeekdays(value);
            case "інтервал у вихідні" -> transport.setIntervalWeekend(value);
        }

        transportService.update(transport); // не забудь реализовать метод update
        log.info("Updated transport: {}", transport);
    }
}
