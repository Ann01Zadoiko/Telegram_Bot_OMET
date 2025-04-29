package com.example.constance.info.tracks;

public enum TracksTrolls {
    TRACK_2("2", "Парк ім. Тараса Шевченка -- вул. Новосельского", "https://www.eway.in.ua/en/cities/odesa/routes/22"),
    TRACK_3("3", "Парк ім. Тараса Шевченка -- станція Застава-1", "https://www.eway.in.ua/en/cities/odesa/routes/23"),
    TRACK_7("7", "вул. Архітекторська -- вул. Новосельського", "https://www.eway.in.ua/en/cities/odesa/routes/25"),
    TRACK_8("8", "Суперфосфатний завод -- Залізнчий вокзал", "https://www.eway.in.ua/en/cities/odesa/routes/26"),
    TRACK_9("9", "вул. Інглезі -- вул. Рішельєвська", "https://www.eway.in.ua/en/cities/odesa/routes/27"),
    TRACK_10("10", "вул. Інглезі -- Пересипський міст", "https://www.eway.in.ua/en/cities/odesa/routes/29"),
    TRACK_12("12", "вул. Архітекторська -- станція Одеса-Застава І", "https://www.eway.in.ua/en/cities/odesa/routes/31");

    private String number;
    private String startEnd;
    private String link;

    TracksTrolls(String number, String startEnd, String link){
        this.number = number;
        this.startEnd = startEnd;
        this.link = link;
    }

    public String getNumber(){
        return number;
    }

    public String getStartEnd(){
        return startEnd;
    }

    public String getLink(){ return link;}
}
