package com.example.registration;

import java.util.concurrent.ConcurrentHashMap;
public class UserStateManager {

    private final ConcurrentHashMap<Long, UserRegistration> userStates = new ConcurrentHashMap<>();

    // Начать регистрацию с указанным типом
    public void startRegistration(Long chatId, RegistrationType type) {
        userStates.put(chatId, new UserRegistration(type));
    }

    // Проверить, находится ли пользователь в процессе регистрации
    public boolean isUserRegistering(Long chatId) {
        return userStates.containsKey(chatId);
    }

    // Получить данные текущей регистрации пользователя
    public UserRegistration getUserRegistration(Long chatId) {
        return userStates.get(chatId);
    }

    // Удалить данные регистрации (завершение или отмена)
    public void removeUser(Long chatId) {
        userStates.remove(chatId);
    }
}
