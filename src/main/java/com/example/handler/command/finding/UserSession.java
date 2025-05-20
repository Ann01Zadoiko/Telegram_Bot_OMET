package com.example.handler.command.finding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Deque;

@Getter
@Setter
@RequiredArgsConstructor
public class UserSession {

    private FindingBotState state = FindingBotState.IDLE_FINDING; // Текущее состояние
    private String lastInput; // Последний ввод пользователя
    private final Deque<FindingBotState> stateHistory = new ArrayDeque<>();

    public void pushState(FindingBotState newState) {
        if (this.state != null) {
            stateHistory.push(this.state);
        }
        this.state = newState;
    }
}
