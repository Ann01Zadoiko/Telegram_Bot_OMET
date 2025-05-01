package com.example.bot;

import com.example.config.BotConfig;
import com.example.handler.BotHandler;
import com.example.handler.BotSenderService;
import com.example.handler.button.KeyboardButtons;
import com.example.handler.command.complaint.ComplaintBotHandler;
import com.example.handler.command.notice.NoticeBotHandler;
import com.example.handler.command.stops.StopsBotHandler;
import com.example.handler.command.tracks.TracksBotHandler;
import com.example.handler.command.vacancy.VacancyBotHandler;
import com.example.registration.UserStateManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import java.io.File;



@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final UserStateManager stateManager;
    private final BotHandler botHandler;
    private final TracksBotHandler tracksBotHandler;
    private final StopsBotHandler stopsBotHandler;
    private final BotSenderService botSenderService;
    private final VacancyBotHandler vacancyBotHandler;
    private final ComplaintBotHandler complaintBotHandler;
    private final NoticeBotHandler noticeBotHandler;

    private static final String LOG = "Reply sent: {}\nBy user: {}";

    @PostConstruct
    public void init() {
        botSenderService.setBot(this); // Устанавливаем TelegramBot после инициализации
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            tracksBotHandler.handleUpdate(update);
            stopsBotHandler.handleUpdate(update);
            vacancyBotHandler.handleUpdate(update);
            noticeBotHandler.handleUpdate(update);
            botHandler.answerToMessage(update, stateManager);
        }

        if (update.hasMessage()){
            complaintBotHandler.handleUpdate(update);
        }

        if (update.hasCallbackQuery()) {
            tracksBotHandler.handleUpdate(update);
            stopsBotHandler.handleUpdate(update);
            vacancyBotHandler.handleUpdate(update);
            noticeBotHandler.handleUpdate(update);
            complaintBotHandler.handleUpdate(update);
            log.info("Callback received: {}", update.getCallbackQuery().getData());
            botHandler.answerToCallback(update);
        }

    }


    @SneakyThrows
    public void sendMessage(long chatId, String text, long messageId, CallbackQuery callbackQuery) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .replyToMessageId((int) messageId)
                .build();
        execute(sendMessage);

        answerCallback(callbackQuery);

        log.info(LOG, sendMessage.getText(), sendMessage.getChatId());
    }

    @SneakyThrows
    public void answerCallback(CallbackQuery callbackQuery) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackQuery.getId());
        answer.setShowAlert(false); // true, если хотите показать всплывающее окно

        execute(answer);
    }

    //send message for user
    @SneakyThrows
    public void sendMessage(long chatId, String text) {

        if (391736560L == chatId){

            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(text)
                    .replyMarkup(KeyboardButtons.getButtonsAdmin())
                    .build();
            execute(sendMessage);

            log.info("Admin",  sendMessage.getText(), sendMessage.getChatId());

        } else {

            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(text)
                    .replyMarkup(KeyboardButtons.getButtons())
                    .build();
            execute(sendMessage);

            log.info(LOG, sendMessage.getText(), sendMessage.getChatId());
        }
    }

    @SneakyThrows
    public void sendPhoto(long chatId, String text) {
        File imageFile = new File(text);

        SendPhoto sendMessage = SendPhoto
                .builder()
                .chatId(chatId)
                .photo(new InputFile(imageFile))
                .replyMarkup(KeyboardButtons.getButtons())
                .build();
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessage(long chatId, String text, InlineKeyboardMarkup markup) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(markup)
                .build();
        execute(sendMessage);

        log.info(LOG, sendMessage.getText(), sendMessage.getChatId());
    }

    @SneakyThrows
    public void executeEditMessage(String text, Long chatId, long messageId, InlineKeyboardMarkup markup) {
        EditMessageText editMessageText = EditMessageText
                .builder()
                .messageId((int) messageId)
                .chatId(chatId)
                .replyMarkup(markup)
                .text(text)
                .build();
        execute(editMessageText);

        log.info(LOG, editMessageText.getText(), editMessageText.getChatId());
    }

    @SneakyThrows
    public void sendMiniApp(Long chatId, InlineKeyboardMarkup markup, String text, long messageId, CallbackQuery callbackQuery) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(markup);
        message.setReplyToMessageId((int) messageId);
        execute(message);

        answerCallback(callbackQuery);
    }
}

