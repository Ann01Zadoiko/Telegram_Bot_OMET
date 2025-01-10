package com.example.constance.info.vacancy;

public enum VacancyWithExperience {

    VACANCY_1("Електрогазозварник"),
    VACANCY_2("Зварювальник"),
    VACANCY_3("Кондуктор"),
    VACANCY_4("Маляр з металу"),
    VACANCY_5("Слюсар з ремонту рухомого складу"),
    VACANCY_6("Механік – дизеліст"),
    VACANCY_7("Монтер колії 3 розряд"),
    VACANCY_8("Електромонтер контактної та кабельної мережі"),
    VACANCY_9("Електромонтер тягової підстанції (з ремонту електроустаткування)"),
    VACANCY_10("Токар-фрезерувальник"),
    VACANCY_11("Юрист (фахівець з публічних закупівель)"),
    VACANCY_12("Слюсар електрик з ремонту електроустаткування");

    private String name;

    VacancyWithExperience(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
