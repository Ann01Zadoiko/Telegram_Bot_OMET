package com.example.handler.command.museum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Deque;

@Getter
@Setter
@RequiredArgsConstructor
public class UserSession {

    private MuseumBotState state = MuseumBotState.IDLE_MUSEUM; // Текущее состояние
    private String lastInput; // Последний ввод пользователя
    private final Deque<MuseumBotState> stateHistory = new ArrayDeque<>();
    private String date;

    public void pushState(MuseumBotState newState) {
        if (this.state != null) {
            stateHistory.push(this.state);
        }
        this.state = newState;
    }
}
