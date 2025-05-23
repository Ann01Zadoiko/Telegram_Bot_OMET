package com.example.feature.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByChatId(Long chatId);

    boolean existsByChatId(Long chatId);
}
