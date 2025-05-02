package com.example.constance.notice;

public enum NoticeEnum {
    CHOOSE_1("ДТП","Через ДТП призупинен маршрут "),
    CHOOSE_2("ремонт","Через ремонт призупинен маршрут "),//
    CHOOSE_3("перекриття вулиць","Через перекиття вулиць призупинен маршрут "),
    CHOOSE_4("перекриння шляху стороннім ТЗ","Через перекриття шляху стороннім ТЗ призупинен маршрут "),
    CHOOSE_5("возоновленно","Возоновленно маршрут");

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