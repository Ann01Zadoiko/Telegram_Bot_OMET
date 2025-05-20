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
public class UserSession{
    private BotState state = BotState.IDLE_TRACK;
    private final Deque<BotState> stateHistory = new ArrayDeque<>();
    private String lastInput;

    // Поля, используемые при работе с маршрутами
    private String previousAction;
    private String transportType;
    private String trackNumber;
    private String stopsStartEnd;
    private String timeStart;
    private String timeEnd;
    private String link;
    private String intervalWeekdays;
    private String intervalWeekend;
    private String updateField;

    // Установить новое состояние, сохранив текущее в историю
    public void pushState(BotState newState) {
        if (this.state != null) {
            stateHistory.push(this.state);
        }
        this.state = newState;
    }

}
