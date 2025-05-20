package com.example.handler.command.notice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.Deque;

@Setter
@Getter
@RequiredArgsConstructor
public class UserSession {

    private NoticeBotState state = NoticeBotState.IDLE_NOTICE; // Текущее состояние
    private final Deque<NoticeBotState> stateHistory = new ArrayDeque<>(); // История состояний
    private String lastInput; // Последний ввод пользователя

    // Поля, используемые при работе с маршрутами
    private String previousAction; // "добавить", "обновить", "удалить"
    private String transportType; // Тип транспорта
    private String trackNumber; // Тип транспорта
    private LocalDate date;
    private LocalTime time;// Номер маршрута
    private String reason;
    private boolean relevance;
    private String updateField; // Обновляемое поле при редактировании

    // Установить новое состояние, сохранив текущее в историю
    public void pushState(NoticeBotState newState) {
        if (this.state != null) {
            stateHistory.push(this.state);
        }
        this.state = newState;
    }
}
