package com.example.bot;

import com.example.config.BotConfig;
import com.example.constance.complaint.Complain;
import com.example.email.EmailSender;
import com.example.feature.complaint.Complaint;
import com.example.feature.complaint.ComplaintService;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.UserService;
import com.example.feature.vacancy.VacancyService;
import com.example.handler.BotHandler;
import com.example.handler.HandlerCallback;
import com.example.handler.HandlerMessage;
import com.example.handler.button.*;
import com.example.registration.ComplaintRegistration;
import com.example.registration.MuseumRegistration;
import com.example.registration.UserStateManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot{

    private final BotConfig config;
    private final MuseumService museumService;
    private final UserService userService;
    private final ComplaintService complaintService;
    private final UserStateManager stateManager;
    private final MuseumRegistration museumRegistration;
    private final ComplaintRegistration complaintRegistration;
    private final VacancyService vacancyService;

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
        BotHandler botHandler = new BotHandler(
                new HandlerCallback(museumService, museumRegistration, complaintRegistration, complaintService, vacancyService),
                new HandlerMessage(museumService, userService, complaintService, vacancyService),
                config,
                museumService,
                userService,
                complaintService,
                stateManager,
                museumRegistration,
                complaintRegistration,
                vacancyService);

        if (update.hasMessage() && update.getMessage().hasText()) {
            botHandler.answerToMessage(update, stateManager);
        }

        if (update.hasCallbackQuery()){
            botHandler.answerToCallback(update);
        }

        if (update.hasMessage() && update.getMessage().hasPhoto()){
            List<Complaint> byChatId = complaintService.findByChatId(update.getMessage().getChatId());
            Complaint complaint = byChatId.get(byChatId.size() - 1);

            processPhotoAndSendEmail(update,
                    complaint.getFullName() + "\n" + complaint.getPhoneNumber() + "\n" + complaint.getText());

            sendMessage(update.getMessage().getChatId(), Complain.STEP_7.getText());
        }
    }

    @SneakyThrows
    public void processPhotoAndSendEmail(Update update, String text) {
        try {
            List<PhotoSize> photos = update.getMessage().getPhoto();
            PhotoSize bestPhoto = photos.get(photos.size() - 1);
            String fileId = bestPhoto.getFileId();

            org.telegram.telegrambots.meta.api.objects.File telegramFile = execute(new GetFile(fileId));
            String fileUrl = "https://api.telegram.org/file/bot" + getBotToken() + "/" + telegramFile.getFilePath();
            String localFilePath = EmailSender.downloadFile(fileUrl, "photo.jpg");

            EmailSender.sendEmailWithAttachment("info@oget.od.ua", "Скарга", text, localFilePath);
            sendMessage(update.getMessage().getChatId(), "Фото успешно отправлено на email!");
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(update.getMessage().getChatId(), "Ошибка отправки фото: " + e.getMessage());
        }
    }

    @SneakyThrows
    public void sendMessage(long chatId, String text, long messageId, CallbackQuery callbackQuery){
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .replyToMessageId((int) messageId)
                .build();
        execute(sendMessage);

        answerCallback(callbackQuery);

        log.info("Reply sent: " + sendMessage.getText() + "\nBy user: " + sendMessage.getChatId());
    }

    public void answerCallback(CallbackQuery callbackQuery) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackQuery.getId());
        answer.setShowAlert(false); // true, если хотите показать всплывающее окно

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //send message for user
    @SneakyThrows
    public void sendMessage(long chatId, String text) throws TelegramApiException {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(KeyboardButtons.getButtons())
                .build();
        execute(sendMessage);

        log.info("Reply sent: " + sendMessage.getText() + "\nBy user: " + sendMessage.getChatId());
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

        log.info("Reply sent: " + sendMessage.getText() + "\nBy user: " + sendMessage.getChatId());
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

        log.info("Reply sent: " + editMessageText.getText() + "\nBy user: " + editMessageText.getChatId());
    }

    @SneakyThrows
    public void sendMiniApp(Long chatId, InlineKeyboardMarkup markup, String text, long messageId, CallbackQuery callbackQuery){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(markup);
        message.setReplyToMessageId((int) messageId);
        execute(message);

        answerCallback(callbackQuery);
    }
}

