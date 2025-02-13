package com.example.registration;

import com.example.bot.TelegramBot;
import com.example.constance.complaint.Complain;
import com.example.constance.museum.Registration;
import com.example.feature.complaint.Complaint;
import com.example.feature.complaint.ComplaintService;
import com.example.feature.museum.Museum;
import com.example.handler.button.SkipButton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ComplaintRegistration {

    private final ComplaintService complaintService;
    private final UserStateManager stateManager;

    @SneakyThrows
    public void startRegistration(Long chatId, TelegramBot bot) {
        log.info("🚀 Начата регистрация chatId: {}", chatId);
        stateManager.startRegistration(chatId, RegistrationType.COMPLAINT, 4);
        bot.sendMessage(chatId, "📝 " + Complain.STEP_2.getText());
    }

    // --- ОБРАБОТКА ЭТАПОВ РЕГИСТРАЦИИ ---
    @SneakyThrows
    public void processRegistrationStep(Long chatId, String messageText, TelegramBot bot) {
        log.info("🔹 processRegistrationStep() вызван для chatId: {}, step: {}", chatId, stateManager.getUserRegistration(chatId).getStep());

        UserRegistration userReg = stateManager.getUserRegistration(chatId);
        List<Complaint> byChatId = complaintService.findByChatId(chatId);
        Complaint complaint = byChatId.get(byChatId.size() - 1);

        switch (userReg.getStep()) {
            case 4:
                log.info("✅ Пользователь ввел имя: {}", messageText);

                complaint.setFullName(messageText);
                complaintService.save(complaint);

                userReg.setFullName(messageText);
                userReg.nextStep();
                stateManager.updateUserRegistration(chatId, userReg); // 👈 ОБНОВЛЯЕМ СОСТОЯНИЕ
                bot.sendMessage(chatId, "📞 " + Complain.STEP_3.getText());
                break;

            case 5:
                log.info("✅ Пользователь ввел номер телефона: {}", messageText);

                complaint.setPhoneNumber(messageText);
                complaintService.save(complaint);

                userReg.setPhoneNumber(messageText);
                userReg.nextStep();
                stateManager.updateUserRegistration(chatId, userReg); // 👈 ОБНОВЛЯЕМ СОСТОЯНИЕ

                bot.sendMessage(chatId, Complain.STEP_4.getText());

                break;

            case 6:
                complaint.setText(messageText);
                complaintService.save(complaint);

                userReg.setText(messageText);
                bot.sendMessage(chatId, Complain.STEP_6.getText(), SkipButton.getButtons("SKIP_PHOTO"));

                stateManager.removeUser(chatId);
                break;

        }
    }
}
