package com.example.registration;

public class UserRegistration {

    private String fullName;
    private String phoneNumber;
    private String text;// Используем для жалобы
    private int countOfPeople;
    private int step = 1;
    private RegistrationType type; // Тип регистрации

    public UserRegistration(RegistrationType type) {
        this.type = type;
    }

    public void nextStep() {
        step++;
    }

    public int getStep() {
        return step;
    }

    public RegistrationType getType() {
        return type;
    }

    // Геттеры и сеттеры
    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public int getCountOfPeople() {
        return countOfPeople;
    }

    public void setCountOfPeople(int countOfPeople) {
        this.countOfPeople = countOfPeople;
    }
}
