package com.example.constance.museum;

public enum Registration {
    STEP_1("Бажаєте відвідати екскурсію у "),
    STEP_2("Напишить своє повне ім'я:"),
    STEP_3("Напишить свій номер телефону:"),
    STEP_4("Напишить кількість людей, які будуть відвідувати екскурсію:"),
    STEP_6("Бажаєте замовити власну екскурсію?"),
    STEP_7("За додатковою інформацією звонить номером: +380503994211"),
    STEP_8("Дякуємо за реєстрацію! "),
    STEP_9("Вибачте, але на даний час екскурсії незаплановано!");

    private String text;

    Registration(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
