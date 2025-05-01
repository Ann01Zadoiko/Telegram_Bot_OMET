package com.example.handler.command.notice;

import com.example.constance.notice.NoticeEnum;
import com.example.feature.notice.Notice;
import com.example.feature.notice.NoticeService;
import com.example.feature.transport.Transport;
import com.example.feature.transport.TransportService;
import com.example.handler.BotSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.util.*;

import static com.example.handler.command.notice.NoticeBotState.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class NoticeBotHandler {
    private final TransportService transportService;
    private final NoticeService noticeService;
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
            case IDLE_NOTICE -> handleIdleState(chatId, session);
            case NOTICE_SELECT_TYPE, NOTICE_SELECT_NUMBER,
                 NOTICE_SELECT_NOTIFICATION -> handleCreateNotice(chatId, session);
        }
    }

    private void handleIdleState(Long chatId, UserSession session) {
        if ("Термінові повідомлення".equals(session.getLastInput())) {
            session.pushState(IDLE_NOTICE);
            session.setState(NOTICE_SELECT_TYPE);
            sender.sendCallbackKeyboard(chatId, "Оберіть тип транспортного засобу:",  List.of("трамвай", "тролейбус"), false);

            log.info("{} current state", session.getState().toString());
        }
    }

    private void handleCreateNotice(Long chatId, UserSession session){
        switch (session.getState()){
            case NOTICE_SELECT_TYPE -> {
                session.setTransportType(session.getLastInput());
                session.pushState(NOTICE_SELECT_TYPE);
                session.setState(NOTICE_SELECT_NUMBER);

                List<String> numbers = transportService.getNumbersByType(session.getTransportType());
                sender.sendCallbackKeyboard(chatId, "Оберіть номер маршруту:", numbers, false);
            }

            case NOTICE_SELECT_NUMBER -> {
                session.setTrackNumber(session.getLastInput());
                session.pushState(NOTICE_SELECT_NUMBER);
                session.setState(NOTICE_SELECT_NOTIFICATION);

                List<String> list = Arrays.stream(NoticeEnum.values()).map(NoticeEnum::getName).toList();
                sender.sendCallbackKeyboard(chatId, "Оберіть повідомлення", list, false);
            }

            case NOTICE_SELECT_NOTIFICATION -> {
                session.setReason(session.getLastInput());

                saveStops(session);
                session.pushState(NOTICE_SELECT_NOTIFICATION);
                session.setState(IDLE_NOTICE);

                sender.sendMessage(chatId, "Повідомлення створенно");
            }
        }
    }

    private void saveStops(UserSession session) {

        Transport transport = transportService.getByTypeAndNumber(session.getTransportType(), session.getTrackNumber()).get();

        Notice notice = new Notice();
        notice.setDate(LocalDate.now());
        notice.setTransport(transport);
        notice.setReason(session.getReason());
        notice.setRelevance(true);

        noticeService.save(notice);

        log.info(notice.toString());
    }


}
