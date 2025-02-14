package com.example.constance.info.vacancy;

public enum Vacancy {
    STEP_1("Для того, щоб змінити вакансії спочатку оберіть тип:"),
    STEP_2("Напишить всі вакансії:"),
    STEP_3("Вакансії змінено!");

    private String text;

    Vacancy(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
