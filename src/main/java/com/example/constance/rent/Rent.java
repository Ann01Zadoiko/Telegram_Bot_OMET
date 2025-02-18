package com.example.constance.rent;

public enum Rent {
    TRAM(
            "ТРАМВАЙНИЙ ВАГОН\nК-1, К-1М, Татра-Юг Т6В5 , TatraТ-3 SU, Tatra Т-3 SUCS, Tatra Т-3 А ,Т-3 КПВ-ОД (типу «Каштан», «Odissey»).\n" +
            "Для отримання детальної інформації звертайтесь за телефоном: 717-54-41",
            "./src/main/resources/photos/tram.jpg"),
    TRAM_RETRO(
            "ТРАМВАЙНИЙ ВАГОН «РЕТРО»\nТрамвайний вагон «Літній екскурсійний МТВ-82, та КТМ -1.\n" +
            "Для отримання детальної інформації звертайтесь за телефоном: 717-54-41",
            "./src/main/resources/photos/tram_retro.jpg"),
    TROLL(
            "ТРОЛЕЙБУСНА МАШИНА\nТРОЛЗА 5265, Шкода 14TR, 21TR, Богдан Т701, БКМ-321, ЗіУ-9, ЮМЗ-Т1, ЮМЗ-Т2.\n" +
            "Для отримання детальної інформації звертайтесь за телефоном: 717-54-42",
            "./src/main/resources/photos/troll.jpg");

    private String description;
    private String photo;

    Rent(String description, String photo){
        this.description = description;
        this.photo = photo;
    }

    public String getDescription(){
        return description;
    }

    public String getPhoto(){
        return photo;
    }
}
