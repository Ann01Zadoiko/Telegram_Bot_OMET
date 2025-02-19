package com.example.registration;

import com.example.bot.TelegramBot;
import com.example.constance.museum.Registration;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MuseumRegistration {

    private final UserStateManager stateManager;
    private final MuseumService museumService;

    // --- НАЧАЛО РЕГИСТРАЦИИ ---
    @SneakyThrows
    public void startRegistration(Long chatId, TelegramBot bot) {
        log.info("🚀 Начата регистрация chatId: {}", chatId);
        stateManager.startRegistration(chatId, RegistrationType.MUSEUM, 1);
        bot.sendMessage(chatId, "📝 " + Registration.STEP_2.getText());
    }

    // --- ОБРАБОТКА ЭТАПОВ РЕГИСТРАЦИИ ---
    @SneakyThrows
    public void processRegistrationStep(Long chatId, String messageText, TelegramBot bot) {
        log.info("🔹 processRegistrationStep() вызван для chatId: {}, step: {}", chatId, stateManager.getUserRegistration(chatId).getStep());

        UserRegistration userReg = stateManager.getUserRegistration(chatId);

        List<Museum> byChatId = museumService.getByChatId(chatId);
        Museum museum = byChatId.get(byChatId.size() - 1);

        switch (userReg.getStep()) {
            case 1:
                log.info("✅ Пользователь ввел имя: {}", messageText);

                museum.setFullName(messageText);
                museumService.save(museum);

                userReg.setFullName(messageText);
                userReg.nextStep();
                stateManager.updateUserRegistration(chatId, userReg); // 👈 ОБНОВЛЯЕМ СОСТОЯНИЕ
                bot.sendMessage(chatId, "📞 " + Registration.STEP_3.getText());
                break;

            case 2:
                log.info("✅ Пользователь ввел номер телефона: {}", messageText);

                museum.setPhoneNumber(messageText);
                museumService.save(museum);

                userReg.setPhoneNumber(messageText);
                userReg.nextStep();
                stateManager.updateUserRegistration(chatId, userReg); // 👈 ОБНОВЛЯЕМ СОСТОЯНИЕ

                bot.sendMessage(chatId, "👥 " + Registration.STEP_4.getText());

                break;

            case 3:
                log.info("✅ Пользователь ввел последний шаг: {}", messageText);

                try {
                    int count = Integer.parseInt(messageText);
                    museum.setCountOfPeople(count);
                    museumService.save(museum);

                    userReg.setText("Количество человек: " + count);

                    bot.sendMessage(chatId, Registration.STEP_8.getText() + "\n" + Registration.STEP_7.getText());
                } catch (NumberFormatException e) {
                    bot.sendMessage(chatId, "❌ Пожалуйста, введите число.");
                    return;
                }
                stateManager.removeUser(chatId);
                break;
        }
    }
}
