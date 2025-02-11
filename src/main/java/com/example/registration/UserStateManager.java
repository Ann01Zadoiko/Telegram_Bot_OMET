package com.example.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class UserStateManager {

    private final Map<Long, UserRegistration> userStates = new ConcurrentHashMap<>();

    // Начать регистрацию с указанным типом
    public void startRegistration(Long chatId, RegistrationType type) {
        UserRegistration registration = new UserRegistration(type);
        userStates.put(chatId, registration);
        log.info("✅ Пользователь {} добавлен в userStates: {}", chatId, registration);
    }

    public void updateUserRegistration(Long chatId, UserRegistration registration) {
        userStates.put(chatId, registration);
        log.info("🔄 Обновлено состояние регистрации: {} -> {}", chatId, registration);
    }

    // Проверить, находится ли пользователь в процессе регистрации
    public boolean isUserRegistering(Long chatId) {
        boolean result = userStates.containsKey(chatId);
        log.info("🔍 Проверка регистрации chatId {}: {}", chatId, result);
        return result;
    }

    // Получить данные текущей регистрации пользователя
    public UserRegistration getUserRegistration(Long chatId) {
        return userStates.get(chatId);
    }

    // Удалить данные регистрации (завершение или отмена)
    public void removeUser(Long chatId) {

        userStates.remove(chatId);
        log.info("❌ Пользователь {} удален из userStates", chatId);
    }
}
