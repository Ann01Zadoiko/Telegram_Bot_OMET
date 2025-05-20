package com.example.handler.command.museum;

import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.handler.BotSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.handler.command.museum.MuseumBotState.*;


@RequiredArgsConstructor
@Component
@Slf4j
public class MuseumBotHandler {

    private final MuseumService museumService;
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


        session.setLastInput(text);


        switch (session.getState()) {
            case IDLE_MUSEUM -> handleIdleState(chatId, session);
            case MUSEUM_CHOOSE_ACTION -> handleMuseumEnter(chatId, session);
            case MUSEUM_ENTER -> handlerEnterDate(chatId, session);
        }
    }

    private void handleIdleState(Long chatId, UserSession session){
        boolean b = (796494502L == chatId) || (1037495749L == chatId) || ( chatId == 391736560L);
        if ("Редагувати екскурсію".equals(session.getLastInput()) && b){
            session.pushState(IDLE_MUSEUM);
            session.setState(MUSEUM_CHOOSE_ACTION);

            String text = "Виберіть дію:";
            sender.sendCallbackKeyboard(chatId, text, List.of("Змінити дату", "Закрити запис"), false);
        }
    }

    private void handleMuseumEnter(Long chatId, UserSession session){
        if (session.getLastInput().equals("Змінити дату")) {
            session.pushState(MUSEUM_CHOOSE_ACTION);
            session.setState(MUSEUM_ENTER);

            sender.sendMessage(chatId, "Введить дату за зразком (15.05.2025)");

        } else if (session.getLastInput().equals("Закрити запис")) {
            session.pushState(MUSEUM_CHOOSE_ACTION);
            closeMuseum();
            session.setState(IDLE_MUSEUM);

            sender.sendMessage(chatId, "Ви закрили запис до ексеурсії");
        }
    }

    private void handlerEnterDate(Long chatId, UserSession session){
        if (session.getState().equals(MUSEUM_ENTER)){
            session.pushState(MUSEUM_ENTER);

            session.setDate(session.getLastInput());
            saveMuseum(session);
            session.setState(IDLE_MUSEUM);

            sender.sendMessage(chatId, "Ви змінили дату запису до ексеурсії");
        }
    }

    private void closeMuseum(){
        Museum museum = museumService.getById(1L);
        museum.setClose(true);
        museumService.save(museum);
    }

    private void saveMuseum(UserSession session){
        Museum museum = museumService.getById(1L);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(session.getDate(), formatter);
        museum.setClose(false);
        museum.setDate(date);
        museumService.save(museum);

        museumService.setNewDayToExhibition(date);
    }
}
