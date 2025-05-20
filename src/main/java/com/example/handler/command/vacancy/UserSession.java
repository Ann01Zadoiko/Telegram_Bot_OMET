package com.example.handler.command.vacancy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Deque;

@Setter
@Getter
@RequiredArgsConstructor
public class UserSession {

    private VacancyState state = VacancyState.IDLE_VACANCY; // Текущее состояние
    private final Deque<VacancyState> stateHistory = new ArrayDeque<>(); // История состояний
    private String lastInput; // Последний ввод пользователя

    // Поля, используемые при работе с маршрутами
    private String specification;
    private String list;

    // Установить новое состояние, сохранив текущее в историю
    public void pushState(VacancyState newState) {
        if (this.state != null) {
            stateHistory.push(this.state);
        }
        this.state = newState;
    }
}
