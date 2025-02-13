package com.example.constance.complaint;

public enum Complain {
    STEP_1("Бажаєте залиишти скаргу чи пропозціцію?"),
    STEP_2("Для початку введіть свій ПІБ:"),
    STEP_3("Введіть свій номер телефону:"),
    STEP_4("Напишить своє повідомлення:"),
    STEP_6("Чи є у Вас фото к даному повідомленню?"),
    STEP_7("Дякуємо за Ваш відгук!");

    private final String text;

    Complain(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
