package com.example.bot;

import com.example.config.BotConfig;
import com.example.constance.Rent;
import com.example.handler.MessageChecker;
import com.example.handler.button.*;
import com.example.museum.Museum;
import com.example.museum.MuseumInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Collections;
@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot{

    private final BotConfig config;

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
        Message message = update.getMessage();

        if (update.hasMessage() && message.hasText()){
            Long chatId = message.getChatId();
            String text = message.getText();

            if (text.equals("/start")) {
                sendMessage(chatId, "Вас вітає бот кп омет! У цьому боті Ви можете: подивитися різну інформація, " +
                        "пов'язану з електротранспортом міста Одеса, записатися до музею, орендувати будь-який " +
                        "транспорт та залишити свій відгук або скаргу. Приємного користування!");
            }

            if (text.equals(Button.MUSEUM.getFullName())){
                sendMessage(chatId, "Museum", MuseumButtons.getButtons());
            }

            if (text.equals(Button.GENERAL_INFO.getFullName())){
                sendMessage(chatId, "General info", GeneralInfoButtons.getButtons());
            }

            if (text.equals(Button.RENT.getFullName())){
                for (Rent rent: Rent.values()){
                    sendMessage(chatId, rent.getDescription());
                    sendPhoto(chatId, rent.getPhoto());
                }
            }
        }

        if (update.hasCallbackQuery()){

            String data = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();

            if (MessageChecker.isEnumValueStatus(data)){
                switch (Museum.valueOf(data)){
                    case INFO ->
                            sendMiniApp(chatId,BackButton.getButtons(),MuseumInfo.TEXT_1,messageId);

                    case SOCIAL_MEDIA_MUSEUM ->
                            sendMiniApp(chatId, LinkButtons.createLinksMuseumButtons(), "Соціальні мережі", messageId);
                }
            }

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
    public void executeEditMessage(String text, Long chatId, long messageId) {

        EditMessageText editMessageText = EditMessageText
                .builder()
                .messageId((int) messageId)
                .chatId(chatId)
                .text(text)
                .build();
        execute(editMessageText);

        log.info("Reply sent: " + editMessageText.getText() + "\nBy user: " + editMessageText.getChatId());
    }

    @SneakyThrows
    public void sendMiniApp(Long chatId, InlineKeyboardMarkup markup, String text, long messageId){
        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(markup);
        message.setMessageId((int) messageId);
        execute(message);
    }


    public void sendMiniApp(String chatId, String url, String name, String text) {
        try {

            WebAppInfo webAppInfo = new WebAppInfo();
            webAppInfo.setUrl(url);

            // Привязываем к кнопке
            InlineKeyboardButton webAppButton = new InlineKeyboardButton();
            webAppButton.setText(name);
            webAppButton.setWebApp(webAppInfo);

            // Добавляем кнопку в разметку
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            keyboardMarkup.setKeyboard(Collections.singletonList(Collections.singletonList(webAppButton)));

            // Создаем сообщение с клавиатурой
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            message.setReplyMarkup(keyboardMarkup);



            // Отправляем сообщение
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

