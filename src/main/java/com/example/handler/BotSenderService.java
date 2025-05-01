package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.complaint.Complain;
import com.example.email.EmailSender;
import com.example.handler.button.KeyboardButtons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BotSenderService {

    private TelegramBot bot;

    public void setBot(TelegramBot bot) {
        this.bot = bot;
    }

    public void sendMessage(Long chatId, String text) {


        if (391736560L == chatId) {

            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(text)
                    .replyMarkup(KeyboardButtons.getButtonsAdmin())
                    .build();
            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText(text);
            message.setReplyMarkup(KeyboardButtons.getButtons());

            try {
                bot.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
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
    }

    public void processPhotoAndSendEmail(String fileId, String text, Long chatId) {
        try {
            org.telegram.telegrambots.meta.api.objects.File telegramFile = bot.execute(new GetFile(fileId));
            String fileUrl = "https://api.telegram.org/file/bot" + bot.getBotToken() + "/" + telegramFile.getFilePath();
            String localFilePath = EmailSender.downloadFile(fileUrl, "photo.jpg");

            EmailSender.sendEmailWithAttachment("info@oget.od.ua", "Скарга", text, localFilePath, chatId);
            sendMessage(chatId, Complain.STEP_7.getText());
        } catch (Exception e) {
            sendMessage(chatId, "Помилка у відправки фото: " + e.getMessage());
        }
    }
}
