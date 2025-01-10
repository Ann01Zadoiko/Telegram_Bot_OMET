package com.example.constance.info.vacancy;

public enum VacancyWithoutExperience {

    VACANCY_1("Обхідник колій та штучних споруд 3 розряд"),
    VACANCY_2("Мийник-прибиральник рухомого складу"),
    VACANCY_3("Прибиральник виробничих приміщень");

    private String name;

    VacancyWithoutExperience(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
