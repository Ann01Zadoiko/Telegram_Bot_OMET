package com.example.constance.notice;

public enum NoticeEnum {
    CHOOSE_1("ДТП","Через ДТП призупинен рух маршруту "),
    CHOOSE_2("ремонт","Через ремонт призупинен рух маршруту "),//
    CHOOSE_3("перекриття вулиць","Через перекиття вулиць призупинен рух маршруту "),
    CHOOSE_4("перекриття шляху стороннім ТЗ","Через перекриття шляху стороннім ТЗ призупинен рух маршруту "),
    CHOOSE_5("відновлено","Відновлено рух маршруту "),
    CHOOSE_6("ввести інше повідомлення","");

    private final String name;
    private final String text;

    NoticeEnum(String name, String text){
        this.name = name;
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public String getName(){
        return name;
    }

    public static String getTextByName(String name) {
        for (NoticeEnum value : NoticeEnum.values()) {
            if (value.name.equalsIgnoreCase(name)) {
                return value.text;
            }
        }
        return null; // или можно выбросить исключение, если не найдено
    }
}
//"ДТП", "ремонт", "перекриття вулиць", "перекриння шляху стороннім ТЗ", "возобновленно"