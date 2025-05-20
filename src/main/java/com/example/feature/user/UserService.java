package com.example.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository repository;


    @Override
    public User getById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public boolean hasPermission(Long chatId) {
        return (getByChatId(chatId).getPermission() == 1);
    }

    @Override
    public User getByChatId(Long chatId){
        return repository.findByChatId(chatId);
    }

    @Override
    public boolean existsByChatId(Long chatId) {
        return repository.existsByChatId(chatId);
    }
}
