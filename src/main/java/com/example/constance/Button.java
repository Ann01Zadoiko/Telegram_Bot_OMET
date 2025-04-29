package com.example.constance;

public enum Button {
    MUSEUM("Музей"),
    COMPLAINT("Скарги та пропозиції"),
    GENERAL_INFO("Загальна інформация"),
    INFO_TRACKS("Маршрути");

    private String fullName;

    Button(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }

}
