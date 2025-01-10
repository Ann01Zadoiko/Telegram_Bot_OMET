package com.example.constance.info.tracks;

public enum TracksTrams {
    TRACK_1("1", "Пересиський міст -- завод \'Центроліт\'"),
    TRACK_1K("1K", "вул.Чорноморського козацтва -- Паустовського"),
    TRACK_3("3", "вул. Пастера -- Люстдорф"),
    TRACK_5("5", "Автовокзал -- Аркадія"),
    TRACK_6("6", "Херсонський сквер -- Лузанівка"),
    TRACK_7("7", "вул. Паустовського -- Люстдорф"),
    TRACK_10("10", "вул. Іцкаха Рабіна -- пл. Тираспольська"),
    TRACK_11("11", "станція Товарна -- пл. Старосінна"),
    TRACK_12("12", "Херсонський сквер -- Слобідський ринок"),
    TRACK_13("13", "ж/м Шкільний -- пл. Старосінна"),
    TRACK_15("15", "пл. Олексіївська -- Слобідський ринок"),
    TRACK_17("17", "11-а станція Великого Фонтану -- Куликове поле"),
    TRACK_18("18", "16-а станція Великого Фонтану -- Куликове поле"),
    TRACK_20("20", "Херсонський сквер -- Хаджибейський лиман"),
    TRACK_26("26", "пл. Старосінна -- 11-а станція Люстдорфської дороги"),
    TRACK_27("27", "Люстдорф -- Рибний порт"),
    TRACK_28("28", "вул. Пастера -- Парк ім. Тараса Шевченка");

    private String number;
    private String startEnd;

    TracksTrams(String number, String startEnd){
        this.number = number;
        this.startEnd = startEnd;
    }

    public String getNumber(){
        return number;
    }

    public String getStartEnd(){
        return startEnd;
    }

}
