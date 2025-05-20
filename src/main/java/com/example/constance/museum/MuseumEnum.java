package com.example.constance.museum;

public enum MuseumEnum {
    INFO ("Інформація про музей"),
    SOCIAL_MEDIA_MUSEUM ("Соціальні мережі"),
    REGISTRATION ("Записатися на екскурсію");

    private String fullName;

    MuseumEnum(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }
}
