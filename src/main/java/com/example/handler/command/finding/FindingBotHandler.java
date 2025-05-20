package com.example.handler.command.finding;

import com.example.feature.finding.Finding;
import com.example.feature.finding.FindingService;
import com.example.handler.BotSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.HashMap;
import java.util.Map;

import static com.example.handler.command.finding.FindingBotState.FINDING_ENTER;
import static com.example.handler.command.finding.FindingBotState.IDLE_FINDING;

@RequiredArgsConstructor
@Component
@Slf4j
public class FindingBotHandler {

    private final FindingService findingService;
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
            case IDLE_FINDING -> handleIdleState(chatId, session);
            case FINDING_ENTER -> handlerEnterDocuments(chatId, session);
        }
    }

    private void handleIdleState(Long chatId, UserSession session){
        if ("Додати документи".equals(session.getLastInput()) && chatId == 391736560L){
            session.pushState(IDLE_FINDING);
            session.setState(FINDING_ENTER);
            sender.sendMessage(chatId, "Введить список документів:");
        }
    }

    private void handlerEnterDocuments(Long chatId, UserSession session){

        if (session.getState().equals(FINDING_ENTER)){
            session.setState(IDLE_FINDING);

            Finding finding = new Finding();
            finding.setText(session.getLastInput());
            findingService.add(finding);

            sender.sendMessage(chatId, "Дані оновлені");
        }
    }
}
