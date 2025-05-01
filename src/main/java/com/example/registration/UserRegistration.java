package com.example.registration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistration {

    private String fullName;
    private String phoneNumber;
    private String text;// Используем для жалобы
    private int step = 1;
    private RegistrationType type;

    public UserRegistration(RegistrationType type, int step) {
        this.type = type;
        this.step = step;
    }

    public RegistrationType getType() {
        return type;
    }

    public void nextStep() {
        step++;
    }


}
