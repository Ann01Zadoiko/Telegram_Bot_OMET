package com.example.constance;

public enum Button {
    RENT("Оренда"),
    MUSEUM("Музей"),
    COMPLAINT("Скарги та пропозиції"),
    GENERAL_INFO("Загальна інформация");

    private String fullName;

    Button(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }

}
