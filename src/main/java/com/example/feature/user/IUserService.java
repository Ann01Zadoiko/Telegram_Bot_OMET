package com.example.feature.user;

public interface IUserService {

    User getById(Long id);

    boolean hasPermission(Long chatId);

    User getByChatId(Long chatId);
}
