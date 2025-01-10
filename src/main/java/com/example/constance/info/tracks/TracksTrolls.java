package com.example.constance.info.tracks;

public enum TracksTrolls {
    TRACK_2("2", "Парк ім. Тараса Шевченка -- вул. Новосельского"),
    TRACK_3("3", "Парк ім. Тараса Шевченка -- станція Застава-1"),
    TRACK_7("7", "вул. Архітекторська -- вул. Новосельського"),
    TRACK_8("8", "Суперфосфатний завод -- Залізнчий вокзал"),
    TRACK_9("9", "вул. Інглезі -- вул. Рішельєвська"),
    TRACK_10("10", "вул. Інглезі -- Пересипський міст"),
    TRACK_12("12", "вул. Архітекторська -- станція Одеса-Застава І");

    private String number;
    private String startEnd;

    TracksTrolls(String number, String startEnd){
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
