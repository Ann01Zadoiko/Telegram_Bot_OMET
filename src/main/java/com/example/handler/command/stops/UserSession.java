package com.example.handler.command.stops;

import com.example.handler.command.BotState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Deque;

@RequiredArgsConstructor
@Getter
@Setter
public class UserSession{

    private BotState state = BotState.IDLE_STOP; // Текущее состояние
    private final Deque<BotState> stateHistory = new ArrayDeque<>(); // История состояний
    private String lastInput; // Последний ввод пользователя

    // Поля, используемые при работе с маршрутами
    private String previousAction; // "добавить", "обновить", "удалить"
    private String transportType; // Тип транспорта
    private String trackNumber; // Номер маршрута
    private String stopsAcross;
    private String stopsRightBack;
    private String updateField; // Обновляемое поле при редактировании

    // Установить новое состояние, сохранив текущее в историю
    public void pushState(BotState newState) {
        if (this.state != null) {
            stateHistory.push(this.state);
        }
        this.state = newState;
    }
}
