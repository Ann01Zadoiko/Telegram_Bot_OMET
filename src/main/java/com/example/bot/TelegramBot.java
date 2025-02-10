package com.example.bot;

import com.example.config.BotConfig;
import com.example.constance.complaint.Complain;
import com.example.email.EmailSender;
import com.example.feature.complaint.Complaint;
import com.example.feature.complaint.ComplaintService;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.UserService;
import com.example.handler.BotHandler;
import com.example.handler.HandlerCallback;
import com.example.handler.HandlerMessage;
import com.example.handler.button.*;
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

import static com.example.email.EmailSender.sendEmailWithAttachment;


@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot{

    private final BotConfig config;
    private final MuseumService museumService;
    private final UserService userService;
    private final ComplaintService complaintService;

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        BotHandler botHandler = new BotHandler(
                new HandlerCallback(museumService),
                new HandlerMessage(museumService, userService, complaintService),
                config,
                museumService,
                userService, complaintService);

        if (update.hasMessage() && update.getMessage().hasText()){
            botHandler.answerToMessage(update);
        }

        if (update.hasMessage() && update.getMessage().hasPhoto()){
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            //Получаем самое большое фото из списка
            String fileId = update.getMessage().getPhoto().get(update.getMessage().getPhoto().size() - 1).getFileId();
            processFileAndSendEmail(fileId, "photo.jpg", update.getMessage().getChatId());

            sendMessage(chatId, Complain.STEP_7.getText());
        }

        if (update.hasMessage() && (update.getMessage().hasVoice() || update.getMessage().hasAudio())){
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            List<Complaint> byChatId = complaintService.findByChatId(chatId);
            Complaint complaint = byChatId.get(byChatId.size() - 1);

            if (update.getMessage().hasVoice()){
                processFileAndSendEmail(update.getMessage().getVoice().getFileId(), "voice.ogg", update.getMessage().getChatId());

            }

            if (update.getMessage().hasAudio()){
                processFileAndSendEmail(update.getMessage().getAudio().getFileId(), "audio.mp3", update.getMessage().getChatId());
            }

            complaintService.save(complaint);

            sendMessage(chatId, Complain.STEP_6.getText(), SkipButton.getButtons("SKIP_PHOTO"));
        }

        if (update.hasCallbackQuery()){
            botHandler.answerToCallback(update);
        }

    }

    @SneakyThrows
    private void processFileAndSendEmail(String fileId, String fileName, Long chatId) {
        try {
            // 1. Получить файл через Telegram Bot API
            org.telegram.telegrambots.meta.api.objects.File telegramFile = execute(new GetFile(fileId));
            String filePath = telegramFile.getFilePath();

            // 2. Скачать файл с серверов Telegram
            String fileUrl = "https://api.telegram.org/file/bot" + getBotToken() + "/" + filePath;
            String localFilePath = EmailSender.downloadFile(fileUrl, fileName);

            // 3. Отправить файл на email
            sendEmailWithAttachment("recipient@example.com", "Файл из Telegram", "См. вложение.", localFilePath);

            // 4. Уведомить пользователя
            sendMessage(chatId, "Файл успешно отправлен на email!");
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(chatId, "Ошибка обработки файла: " + e.getMessage());
        }
    }

    @SneakyThrows
    public void sendMessage(long chatId, String text, long messageId, CallbackQuery callbackQuery) throws TelegramApiException {

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


    public void answerCallback(CallbackQuery callbackQuery) throws TelegramApiException {

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
    public void sendPhoto(long chatId, String text) throws TelegramApiException {

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
    public void sendMessage(long chatId, String text, InlineKeyboardMarkup markup) throws TelegramApiException {

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

