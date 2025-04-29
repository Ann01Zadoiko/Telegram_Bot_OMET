package com.example.handler.command.tracks;

import com.example.handler.command.BotState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Deque;

@Getter
@Setter
@NoArgsConstructor
public class UserSession {
    private BotState state = BotState.IDLE_TRACK; // Текущее состояние
    private final Deque<BotState> stateHistory = new ArrayDeque<>(); // История состояний
    private String lastInput; // Последний ввод пользователя

    // Поля, используемые при работе с маршрутами
    private String previousAction; // "добавить", "обновить", "удалить"
    private String transportType; // Тип транспорта
    private String trackNumber; // Номер маршрута
    private String stopsStartEnd; // Конечные остановки
    private String timeStartEnd; // Время начала и конца
    private String link; // Ссылка на маршрут
    private String interval; // Интервал
    private String updateField; // Обновляемое поле при редактировании

    // Установить новое состояние, сохранив текущее в историю
    public void pushState(BotState newState) {
        if (this.state != null) {
            stateHistory.push(this.state);
        }
        this.state = newState;
    }

}
