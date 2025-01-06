package com.example.info;

public enum GeneralInfo {
    VACANCY ("Вакансії"),
    STUDY ("Навчання"),
    TRACKS ("Маршрути"),
    NEWS ("Новини"),
    SOCIAL_MEDIA ("Соціальні мережи"),
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
