package com.example.handler.command.complaint;

import com.example.constance.complaint.Complain;
import com.example.email.EmailSender;
import com.example.feature.complaint.Complaint;
import com.example.feature.complaint.ComplaintService;
import com.example.handler.BotSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.handler.command.complaint.ComplaintBotState.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class ComplaintBotHandler {

    private final ComplaintService complaintService;
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
            text = update.getCallbackQuery().getData(); // ВАЖНО!
        }

        if (update.hasMessage() && update.getMessage().hasDocument()){
            PhotoSize thumb = update.getMessage().getDocument().getThumb();
            text = thumb.getFileId();
        }

        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            List<PhotoSize> photos = update.getMessage().getPhoto();
            PhotoSize bestPhoto = photos.get(photos.size() - 1);
            text = bestPhoto.getFileId();
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
            case IDLE_COMPLAINT -> handlerStatusComplaint(chatId, session);
            case COMPLAINT_SELECT -> handlerStartComplaint(chatId, session);
            case COMPLAINT_ENTER_FULL_NAME, COMPLAINT_ENTER_PHONE_NUMBER,
                 COMPLAINT_ENTER_TEXT, COMPLAINT_SEND_PHOTO -> handlerCreateComplaint(chatId, session);
        }
    }

    private void handlerStatusComplaint(Long chatId, UserSession session){
        if ("Скарги та пропозиції".equals(session.getLastInput())){
            session.pushState(IDLE_COMPLAINT);
            session.setState(COMPLAINT_SELECT);

            List<String> list = List.of("Так", "Ні");
            sender.sendCallbackKeyboard(chatId, Complain.STEP_1.getText(), list, false);

            log.info("Complaint start by user id {}", chatId);
        }
    }

    private void handlerStartComplaint(Long chatId, UserSession session){
        if (session.getLastInput().equals("Ні")){
            session.pushState(COMPLAINT_SELECT);
            session.setState(IDLE_COMPLAINT);

            String text = "Також можете звернутися до Інформаційного центра за номерами:\n+38 (094) 921 98 86\n+38 (048) 7 886 886\n+38 (048) 717 54 54";
            sender.sendMessage(chatId, text);
        } else if (session.getLastInput().equals("Так")){
            session.pushState(COMPLAINT_SELECT);
            session.setState(COMPLAINT_ENTER_FULL_NAME);

            sender.sendMessage(chatId, Complain.STEP_2.getText());
        } else {
            session.pushState(COMPLAINT_SELECT);
            session.setState(IDLE_COMPLAINT);
        }
        log.info("Complaint, select yes or no by user id {}", chatId);
    }

    private void handlerCreateComplaint(Long chatId, UserSession session){
        switch (session.getState()){
            case COMPLAINT_ENTER_FULL_NAME -> {
                session.setFullName(session.getLastInput());
                session.pushState(COMPLAINT_ENTER_FULL_NAME);
                session.setState(COMPLAINT_ENTER_PHONE_NUMBER);

                sender.sendMessage(chatId, Complain.STEP_3.getText());
            }

            case COMPLAINT_ENTER_PHONE_NUMBER -> {
                session.setPhoneNumber(session.getLastInput());
                session.pushState(COMPLAINT_ENTER_PHONE_NUMBER);
                session.setState(COMPLAINT_ENTER_TEXT);

                sender.sendMessage(chatId, Complain.STEP_4.getText());
            }

            case COMPLAINT_ENTER_TEXT -> {
                session.setText(session.getLastInput());
                session.pushState(COMPLAINT_ENTER_TEXT);
                session.setState(COMPLAINT_SEND_PHOTO);

                sender.sendCallbackKeyboard(chatId, Complain.STEP_6.getText(), List.of("Пропустити"), false);
            }

            case COMPLAINT_SEND_PHOTO -> {

                session.pushState(COMPLAINT_SEND_PHOTO);

                saveComplaint(chatId, session);

                List<Complaint> byChatId = complaintService.findByChatId(chatId);
                Complaint complaint = byChatId.get(byChatId.size() - 1);

                if (session.getLastInput().equals("Пропустити")){
                    EmailSender.sendEmailWithAttachment("info@oget.od.ua",
                            "Скарга",
                            complaint.getFullName() + "\n" + complaint.getPhoneNumber() + "\n" + complaint.getText());

                    sender.sendMessage(chatId,  Complain.STEP_7.getText());
                } else {

                    sender.sendMessage(chatId, "Зачекайте 5-10 секунд для обробки фото");
                    sender.processPhotoAndSendEmail(session.getLastInput(),
                            complaint.getFullName() + "\n" + complaint.getPhoneNumber() + "\n" + complaint.getText(), chatId);
                }

                session.setState(IDLE_COMPLAINT);
            }
        }

        log.info("User ({}) enter values for complaint", chatId);
    }

    private void saveComplaint(Long chatId, UserSession session){
        Complaint complaint = new Complaint();

        complaint.setText(session.getText());
        complaint.setPhoneNumber(session.getPhoneNumber());
        complaint.setFullName(session.getFullName());
        complaint.setChatId(chatId);

        complaintService.save(complaint);

        log.info("Complaint was created by user ({})", chatId);
    }
}
