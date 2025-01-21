package com.example.constance.complaint;

public enum Complain {
    STEP_1("Бажаєте залиишти скаргу чи пропозціцію?"),
    STEP_2("Для початку введіть свій ПІБ (за зразком: Іван Котов)"),
    STEP_3("Введіть свій номер телефону (за зразком: +380685554411)"),
    STEP_4("Напишить своє повідомлення"),
    STEP_5("Бажаєте залишити аудіо запис?"),
    STEP_6("Чи є у Вас фото к даному повідомленню?"),
    STEP_7("Дякуємо за Ваш відгук!");

    private String text;

    Complain(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
