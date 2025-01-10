package com.example.constance.info;

public enum GeneralInfo {
    VACANCY ("Вакансії"),
    STUDY ("Навчання"),
    TRACKS ("Маршрути"),
    SOCIAL_MEDIA ("Соціальні мережи");

    private String fullName;

    GeneralInfo(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }
}
