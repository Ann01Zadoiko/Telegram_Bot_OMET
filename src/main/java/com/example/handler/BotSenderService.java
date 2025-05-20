package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.complaint.Complain;
import com.example.email.EmailSender;
import com.example.handler.button.KeyboardButtons;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BotSenderService {

    private TelegramBot bot;
    private static final String LOG = "Reply sent: {}\nBy user: {}";

    public void setBot(TelegramBot bot) {
        this.bot = bot;
    }

    public void sendMessage(Long chatId, String text) {
        if ((391736560L == chatId) || (796494502L == chatId) || (1037495749L == chatId)) {

            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(text)
                    .replyMarkup(KeyboardButtons.getButtonsAdminAndEditor())
                    .build();
            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(text)
                    .replyMarkup(KeyboardButtons.getButtons())
                    .build();
            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        log.info("Reply sent: {}\nBy user: {}", text, chatId);
    }

    public void sendInlineKeyboard(Long chatId, String text, Map<String, String> buttonData) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (Map.Entry<String, String> entry : buttonData.entrySet()) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(entry.getKey())
                    .callbackData(entry.getValue())
                    .build();
            rows.add(List.of(button));
        }

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);

        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setReplyMarkup(markup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        log.info("Reply sent: {}\nBy user: {}", text, chatId);
    }

    public void sendCallbackKeyboard(Long chatId, String text, List<String> buttons, boolean withBack) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = buttons.stream()
                .map(btn -> List.of(InlineKeyboardButton.builder().text(btn).callbackData(btn).build()))
                .collect(Collectors.toList());

        if (withBack) {
            rows.add(List.of(InlineKeyboardButton.builder().text("◀️ Назад").callbackData("back").build()));
        }

        markup.setKeyboard(rows);

        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setReplyMarkup(markup);

        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        log.info("Reply sent: {}\nBy user: {}", text, chatId);
    }

    public void processPhotoAndSendEmail(String fileId, String text, Long chatId) {
        try {
            org.telegram.telegrambots.meta.api.objects.File telegramFile = bot.execute(new GetFile(fileId));
            String fileUrl = "https://api.telegram.org/file/bot" + bot.getBotToken() + "/" + telegramFile.getFilePath();
            String localFilePath = EmailSender.downloadFile(fileUrl, "photo.jpg");

            EmailSender.sendEmailWithAttachment("info@oget.od.ua", "Скарга", text, localFilePath);
            sendMessage(chatId, Complain.STEP_7.getText());
        } catch (Exception e) {
            sendMessage(chatId, "Помилка у відправки фото: " + e.getMessage());
        }

        log.info("Complaint was sent to email");
    }

    @SneakyThrows
    public void sendMessage(long chatId, String text, InlineKeyboardMarkup markup) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(markup)
                .build();
        bot.execute(sendMessage);
    }

    @SneakyThrows
    public void answerCallback(CallbackQuery callbackQuery) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackQuery.getId());
        answer.setShowAlert(false); // true, если хотите показать всплывающее окно

        bot.execute(answer);
    }

    @SneakyThrows
    public void sendMessage(long chatId, String text, long messageId, CallbackQuery callbackQuery) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(text)
                .replyToMessageId((int) messageId)
                .build();
        bot.execute(sendMessage);

        answerCallback(callbackQuery);
    }

    @SneakyThrows
    public void sendPhoto(long chatId, String text) {
        File imageFile = new File(text);

        if ((391736560L == chatId) || (796494502L == chatId) || (1037495749L == chatId)) {

            SendPhoto sendMessage = SendPhoto
                    .builder()
                    .chatId(chatId)
                    .photo(new InputFile(imageFile))
                    .replyMarkup(KeyboardButtons.getButtonsAdminAndEditor())
                    .build();

                bot.execute(sendMessage);

        } else {
            SendPhoto sendMessage = SendPhoto
                    .builder()
                    .chatId(chatId)
                    .photo(new InputFile(imageFile))
                    .replyMarkup(KeyboardButtons.getButtons())
                    .build();

            bot.execute(sendMessage);
        }

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
        bot.execute(editMessageText);

    }

    @SneakyThrows
    public void sendMiniApp(Long chatId, InlineKeyboardMarkup markup, String text, long messageId, CallbackQuery callbackQuery) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(markup);
        message.setReplyToMessageId((int) messageId);
        bot.execute(message);

        answerCallback(callbackQuery);
    }
}
