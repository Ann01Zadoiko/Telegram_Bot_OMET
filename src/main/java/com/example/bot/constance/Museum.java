package com.example.bot.constance;

public enum Museum {
    INFO ("Інформація про музей"),
    SOCIAL_MEDIA ("Соціальні мережі"),
    REGISTRATION ("Реєстрація");

    private String fullName;

    Museum(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }
}
