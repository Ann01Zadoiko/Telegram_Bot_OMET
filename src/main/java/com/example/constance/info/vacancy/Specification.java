package com.example.constance.info.vacancy;

public enum Specification {

    WITH_EXPERIENCE("З досвідом роботи"),
    WITHOUT_EXPERIENCE("Без досвіду роботи");

    private String name;

    Specification(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
