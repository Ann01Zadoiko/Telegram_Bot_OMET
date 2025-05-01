package com.example.constance.complaint;

public enum Complain {
    STEP_1("Бажаєте залишти скаргу чи пропозицію?"),
    STEP_2("Для початку введіть своє повне ім'я та прізвище:"),
    STEP_3("Введіть свій номер телефону:"),
    STEP_4("Напишить своє повідомлення:"),
    STEP_6("Чи є у Вас фото доказ до цього повідомлення?"),
    STEP_7("Дякуємо за Ваш відгук!");

    private final String text;

    Complain(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
