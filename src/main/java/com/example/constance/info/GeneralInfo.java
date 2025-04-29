package com.example.constance.info;

public enum GeneralInfo {
    VACANCY ("Вакансії"),
    STUDY ("Навчання"),
    SOCIAL_MEDIA ("Соціальні мережі"),
    RENT("Оренда");

    private String fullName;

    GeneralInfo(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }
}
