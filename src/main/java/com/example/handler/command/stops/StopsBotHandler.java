package com.example.handler.command.stops;

import com.example.feature.stop.Stop;
import com.example.feature.stop.StopService;
import com.example.feature.transport.Transport;
import com.example.feature.transport.TransportService;
import com.example.handler.BotSenderService;
import com.example.handler.command.stops.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.*;

import static com.example.handler.command.BotState.*;

@Slf4j
@Component
public class StopsBotHandler {
    private final TransportService transportService;
    private final StopService stopService;
    private final Map<Long, UserSession> sessions = new HashMap<>();
    private final BotSenderService sender;

    public StopsBotHandler(TransportService transportService, StopService stopService, BotSenderService sender) {
        this.transportService = transportService;
        this.stopService = stopService;
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
            case IDLE_STOP -> handleIdleState(chatId, session);
            case STOPS_ACTION_SELECTION -> handleStopsActionSelection(chatId, session);
            case STOP_SELECT_TYPE -> handleStopsSelectType(chatId, session);
            case STOP_SELECT_NUMBER, STOP_ENTER_NAME_1,
                 STOP_ENTER_NAME_2-> handleAddStopsState(chatId, session);
            case STOP_UPDATE_SELECT_FIELD, STOP_UPDATE_ENTER_VALUE,
                 STOP_UPDATE_ENTER_NEW_VALUE -> handleUpdateStopsState(chatId, session);
            case STOP_DELETE_NAME -> handleDeleteStopsState(chatId, session);
        }
    }

    private void handleIdleState(Long chatId, UserSession session) {
        if ("/stops".equals(session.getLastInput())) {
            session.pushState(IDLE_STOP);
            session.setState(STOPS_ACTION_SELECTION);
            Map<String, String> actions = new LinkedHashMap<>();
            actions.put("stop_add", "stop_add");
            actions.put("stop_update", "stop_update");
            actions.put("stop_delete", "stop_delete");
            sender.sendCallbackKeyboard(chatId, "Выберите действие:", List.of("stop_add", "stop_update","stop_delete"), false);

            log.info("{} current state", session.getState().toString());
        }
    }

    private void handleStopsActionSelection(Long chatId, UserSession session) {
        session.setPreviousAction(session.getLastInput());
        session.pushState(STOPS_ACTION_SELECTION);
        session.setState(STOP_SELECT_TYPE);
        sender.sendCallbackKeyboard(chatId, "Выберите тип транспорта:", List.of("трамвай", "тролейбус"), false);

        log.info("{} current state", session.getState().toString());
        log.info("{} previous action", session.getPreviousAction());

    }

    private void handleStopsSelectType(Long chatId, UserSession session) {
        session.setTransportType(session.getLastInput());
        session.pushState(STOP_SELECT_TYPE);
        List<String> numbers = transportService.getNumbersByType(session.getTransportType());

        switch (session.getPreviousAction()) {
            case "stop_add" -> {
                session.setState(STOP_ENTER_NAME_1);
                sender.sendCallbackKeyboard(chatId, "Выберите номер маршрута:", numbers, false);
            }
            case "stop_update" -> {
                session.setState(STOP_UPDATE_SELECT_FIELD);
                sender.sendCallbackKeyboard(chatId, "Выберите номер маршрута:", numbers, false);
            }
            case "stop_delete" -> {
                session.setState(STOP_DELETE_NAME);
                sender.sendCallbackKeyboard(chatId, "Выберите номер маршрута:", numbers, false);
            }
        }

        log.info("{} current state", session.getState().toString());
        log.info("{} last input", session.getLastInput());
    }

    private void handleAddStopsState(Long chatId, UserSession session) {
        switch (session.getState()) {
            case STOP_ENTER_NAME_1 -> {
                session.setStopsAcross(session.getLastInput());
                session.pushState(STOP_ENTER_NAME_1);
                session.setState(STOP_ENTER_NAME_2);
                sender.sendMessage(chatId, "Введите остановки в прямом направлении:");
            }
            case STOP_ENTER_NAME_2 -> {
                session.setStopsRightBack(session.getLastInput());
                session.pushState(STOP_ENTER_NAME_2);
                saveStops(session);
                session.setState(IDLE_STOP);
                sender.sendMessage(chatId, "Введите остановки в обратном напрявлении:");
            }
        }
        log.info("{} current state", session.getState().toString());
    }

    private void handleUpdateStopsState(Long chatId, UserSession session) {
        switch (session.getState()) {
            case STOP_UPDATE_SELECT_FIELD -> {
                session.setTrackNumber(session.getLastInput());
                session.pushState(STOP_UPDATE_SELECT_FIELD);
                session.setState(STOP_UPDATE_ENTER_VALUE);
                sender.sendCallbackKeyboard(chatId, "Что хотите обновить?",
                        List.of("прямий напрямок", "зворотній напрямок", "Завершить"), false);
            }
            case STOP_UPDATE_ENTER_VALUE -> {
                if ("Завершить".equals(session.getLastInput())) {
                    session.setState(IDLE_STOP);
                    sender.sendMessage(chatId, "Обновление завершено ✅");
                    return;
                }
                session.setUpdateField(session.getLastInput());
                session.pushState(STOP_UPDATE_ENTER_VALUE);
                session.setState(STOP_UPDATE_ENTER_NEW_VALUE);
                sender.sendMessage(chatId, "Введите новое значение:");
            }
            case STOP_UPDATE_ENTER_NEW_VALUE -> {
                String newValue = session.getLastInput();
                updateStops(session, newValue); // вызываем обновление
                session.setState(STOP_UPDATE_ENTER_VALUE); // возвращаемся к выбору следующего поля
                sender.sendCallbackKeyboard(chatId, "Что хотите обновить ещё?",
                        List.of("прямий напрямок", "зворотній напрямок", "Завершить"), false);
            }
        }

        log.info("{} current state", session.getState().toString());
    }

    private void handleDeleteStopsState(Long chatId, UserSession session) {
        Transport transport = transportService.getByTypeAndNumber(session.getTransportType(), session.getLastInput()).get();
        stopService.delete(transport);

        session.setState(IDLE_STOP);
        sender.sendMessage(chatId, "Остановки удалены ✅");

        log.info("{} current state", session.getState().toString());
    }

    private void saveStops(UserSession session) {

        Transport transport = transportService.getByTypeAndNumber(session.getTransportType(), session.getTrackNumber()).get();

        Stop stop = new Stop();
        stop.setTransport(transport);
        stop.setStopAcross(session.getStopsAcross());
        stop.setStopRightBack(session.getStopsRightBack());
        stopService.add(stop);

        log.info(stop.toString());
    }

    private void updateStops(UserSession session, String newValue) {
        Optional<Transport> optional = transportService.getByTypeAndNumber(session.getTransportType(), session.getTrackNumber());
        if (optional.isEmpty()) return;

        Stop stop = stopService.getByTransport(optional.get());

        switch (session.getUpdateField()) {
            case "прямий напрямок" -> stop.setStopAcross(newValue);
            case "зворотній напрямок" -> stop.setStopRightBack(newValue);
        }

        stopService.update(stop);
        log.info("Updated stops: {}", stop);
    }
}
