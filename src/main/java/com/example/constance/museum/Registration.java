package com.example.constance.museum;

public enum Registration {
    STEP_1("Бажаєте відвідати виставу у "),
    STEP_2("Напишить своє повне ім'я:"),
    STEP_3("Напишить свій номер телефону:"),
    STEP_4("Яка кількість людей буде відвідувати виставу?"),
    STEP_6("Бажаєте замовити власну виставу?"),
    STEP_7("За додатковою інформацією звонить номером +380503994211"),
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
