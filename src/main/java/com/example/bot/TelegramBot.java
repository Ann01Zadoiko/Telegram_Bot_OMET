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
import com.example.registration.RegistrationType;
import com.example.registration.UserRegistration;
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
    private final UserStateManager stateManager = new UserStateManager();

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    // --- НАЧАЛО РЕГИСТРАЦИИ ---
    @SneakyThrows
    private void startRegistration(Long chatId, RegistrationType type) {
        stateManager.startRegistration(chatId, type);
        sendMessage(chatId, (type == RegistrationType.MUSEUM) ?
                "📝 Введите ваше полное имя (для записи в музей):" :
                "📝 Введите ваше полное имя (для подачи жалобы):");
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {

        BotHandler botHandler = new BotHandler(
                new HandlerCallback(museumService),
                new HandlerMessage(museumService, userService, complaintService),
                config,
                museumService,
                userService, complaintService);

        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText().trim();

            // Если пользователь в процессе регистрации, обрабатываем только регистрацию
            if (stateManager.isUserRegistering(chatId)) {
                if (messageText.equalsIgnoreCase("/cancel")) {
                    stateManager.removeUser(chatId);
                    sendMessage(chatId, "❌ Регистрация отменена.");
                } else {
                    processRegistrationStep(chatId, messageText);
                }
                return; // Прерываем дальнейшую обработку, чтобы не реагировать на команды
            }

            // --- ОБРАБОТКА КОМАНД ---
            switch (messageText.toLowerCase()) {

                case "/register":
                    startRegistration(chatId, RegistrationType.MUSEUM);
                    break;

                case "/complaint":
                    startRegistration(chatId, RegistrationType.COMPLAINT);
                    break;

                default:
                    sendMessage(chatId, "❓ Я не понимаю этот запрос. Попробуйте /start.");
            }
        }

        if (update.hasCallbackQuery()){
            botHandler.answerToCallback(update);
        }
    }

//    @SneakyThrows
//    @Override
//    public void onUpdateReceived(Update update) {
//        BotHandler botHandler = new BotHandler(
//                new HandlerCallback(museumService),
//                new HandlerMessage(museumService, userService, complaintService),
//                config,
//                museumService,
//                userService, complaintService);
//
//        if (update.hasMessage() && update.getMessage().hasText()){
//            botHandler.answerToMessage(update);
//
//            String text = update.getMessage().getText();
//            Long chatId = update.getMessage().getChatId();
//
//            switch (text.toLowerCase()) {
//
//                case "/register":
//                    startRegistration(chatId, RegistrationType.MUSEUM);
//                    break;
//
//                case "/complaint":
//                    startRegistration(chatId, RegistrationType.COMPLAINT);
//                    break;
//
//                default:
//                    sendMessage(chatId, "❓ Я не понимаю этот запрос. Попробуйте /start.");
//            }
//
//
//            // Если пользователь в процессе регистрации, обрабатываем только регистрацию
//            if (stateManager.isUserRegistering(update.getMessage().getChatId())) {
//                if (update.getMessage().getText().equalsIgnoreCase("/cancel")) {
//                    stateManager.removeUser(update.getMessage().getChatId());
//                    sendMessage(update.getMessage().getChatId(), "❌ Регистрация отменена.");
//                } else {
//                    processRegistrationStep(update.getMessage().getChatId(), update.getMessage().getText());
//                }
//                return; // Прерываем дальнейшую обработку, чтобы не реагировать на команды
//            }
//        }
//
//
//
//        if (update.hasCallbackQuery()){
//            botHandler.answerToCallback(update);
//        }
//
//    }

    // --- ОБРАБОТКА ЭТАПОВ РЕГИСТРАЦИИ ---
    @SneakyThrows
    private void processRegistrationStep(Long chatId, String messageText) {
        UserRegistration userReg = stateManager.getUserRegistration(chatId);
        RegistrationType type = userReg.getType();

        switch (userReg.getStep()) {
            case 1:
                userReg.setFullName(messageText);
                userReg.nextStep();
                sendMessage(chatId, "📞 Введите ваш номер телефона:");
                break;

            case 2:
                userReg.setPhoneNumber(messageText);
                userReg.nextStep();
                if (type == RegistrationType.MUSEUM) {
                    sendMessage(chatId, "👥 Введите количество человек:");
                } else {
                    sendMessage(chatId, "✍ Опишите вашу жалобу:");
                }
                break;

            case 3:
                if (type == RegistrationType.MUSEUM) {
                    try {
                        int count = Integer.parseInt(messageText);
                        userReg.setText("Количество человек: " + count);
                        sendMessage(chatId, "✅ Регистрация в музей завершена!\n" +
                                "Имя: " + userReg.getFullName() + "\n" +
                                "Телефон: " + userReg.getPhoneNumber() + "\n" +
                                userReg.getText());
                    } catch (NumberFormatException e) {
                        sendMessage(chatId, "❌ Пожалуйста, введите число.");
                        return;
                    }
                } else {
                    userReg.setText(messageText);
                    sendMessage(chatId, "✅ Жалоба подана!\n" +
                            "Имя: " + userReg.getFullName() + "\n" +
                            "Телефон: " + userReg.getPhoneNumber() + "\n" +
                            "Жалоба: " + userReg.getText());
                }
                stateManager.removeUser(chatId);
                break;
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

