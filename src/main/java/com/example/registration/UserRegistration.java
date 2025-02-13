package com.example.registration;

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

    public void setType(RegistrationType type) {
        this.type = type;
    }

    public void nextStep() {
        step++;
    }

    public int getStep() {
        return step;
    }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

}
