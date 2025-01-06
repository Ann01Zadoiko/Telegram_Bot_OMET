package com.example.museum;

public enum Museum {
    INFO ("Інформація про музей"),
    SOCIAL_MEDIA_MUSEUM ("Соціальні мережі"),
    REGISTRATION ("Реєстрація");

    private String fullName;

    Museum(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }
}
