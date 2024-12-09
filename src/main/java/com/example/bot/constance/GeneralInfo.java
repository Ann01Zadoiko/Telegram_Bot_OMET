package com.example.bot.constance;

public enum GeneralInfo {
    VACANCY ("Вакансії"),
    STUDY ("Навчання"),
    NEWS ("Новини"),
    SOCIAL_MEDIA ("Соціальні мережи"),
    TRACKS ("Маршрути"),
    FREE_TRAVEL ("Пільги"),
    RULES ("Правила користуванням");

    private String fullName;

    GeneralInfo(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }
}
