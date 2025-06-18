package com.example.handler.registration.museum;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.ArrayDeque;
import java.util.Deque;

@Getter
@Setter
@RequiredArgsConstructor
public class UserSession {

    private MuseumBotState state = MuseumBotState.IDLE_MUSEUM_REGISTRATION;
    private final Deque<MuseumBotState> stateHistory = new ArrayDeque<>();
    private String lastInput;

    private String fullName;
    private String phoneNumber;
    private int countOfPeople;

    public void pushState(MuseumBotState newState) {
        if (this.state != null) {
            stateHistory.push(this.state);
        }
        this.state = newState;
    }
}
