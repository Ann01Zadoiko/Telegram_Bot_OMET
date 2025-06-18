package com.example.handler.registration.museum;

import com.example.constance.museum.Registration;
import com.example.feature.museum.MuseumService;
import com.example.handler.BotSenderService;
import com.example.handler.button.GeneralButton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.handler.registration.museum.MuseumBotState.*;


@Component
@Slf4j
@RequiredArgsConstructor
public class MuseumBotRegistration {


    private final MuseumService museumService;
    private final Map<Long, UserSession> sessions = new HashMap<>();
    private final BotSenderService sender;

    public void handleUpdate(Update update) {
        if (!update.hasMessage() && !update.hasCallbackQuery()) return;

        Long chatId = 0L;
        String text = "";

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            text = update.getMessage().getText();
        }

        if (update.hasCallbackQuery() ){
            chatId = update.getCallbackQuery().getMessage().getChatId();
            text = update.getCallbackQuery().getData();
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

        }
    }

    private void handleIdleMuseum(Long chatId, UserSession session){
        if ("Записатися на екскурсію".equals(session.getLastInput())){
            session.pushState(IDLE_MUSEUM_REGISTRATION);
            session.setState(REGISTRATION_CHOOSE);
            Map<String, String> actions = Map.of("Так","YES_MUSEUM", "Ні", "NO_MUSEUM");

            sender.sendInlineKeyboard(chatId, Registration.STEP_1.getText(), actions);
        }
    }

    private void handleYesAndNo(Long chatId, UserSession session){
        switch (session.getLastInput()){
            case "YES_MUSEUM" -> {
                session.pushState(REGISTRATION_CHOOSE);
                session.setState(REGISTRATION_ENTER_NAME);
                sender.sendMessage(chatId, Registration.STEP_2.getText());
            }
            case "NO_MUSEUM"-> {
                session.pushState(REGISTRATION_CHOOSE);
                session.setState(IDLE_MUSEUM_REGISTRATION);

                Map<String, String> action = new LinkedHashMap<>();
                

//                List<MuseumEnum> list = Arrays.stream(MuseumEnum.values()).toList();
//            List<String> listText = list.stream().map(MuseumEnum::getFullName).toList();
//            List<String> listCallback = list.stream().map(MuseumEnum::name).toList();
             //   sender.executeEditMessage("Музей", chatId, messageId, new GeneralButton().getButtons(listText, listCallback));
            }
            default -> {
                session.pushState(REGISTRATION_CHOOSE);
                session.setState(IDLE_MUSEUM_REGISTRATION);
            }
        }
    }
}
