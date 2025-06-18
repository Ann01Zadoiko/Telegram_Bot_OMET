package com.example.handler.registration.complaint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.ArrayDeque;
import java.util.Deque;

@Getter
@Setter
@RequiredArgsConstructor
public class UserSession {

    private ComplaintBotState state = ComplaintBotState.IDLE_COMPLAINT; // Текущее состояние
    private final Deque<ComplaintBotState> stateHistory = new ArrayDeque<>(); // История состояний
    private String lastInput; // Последний ввод пользователя

    // Поля, используемые при работе с маршрутами
    private String previousAction; // "добавить", "обновить", "удалить"
    private String fullName;
    private String phoneNumber;
    private String text;
    private Blob image;

    // Установить новое состояние, сохранив текущее в историю
    public void pushState(ComplaintBotState newState) {
        if (this.state != null) {
            stateHistory.push(this.state);
        }
        this.state = newState;
    }
}
