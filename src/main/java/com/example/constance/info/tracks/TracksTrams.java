package com.example.constance.info.tracks;

public enum TracksTrams {
    TRACK_1("1", "Пересиський міст -- завод \'Центроліт\'", "https://www.eway.in.ua/en/cities/odesa/routes/2"),
    TRACK_3("3", "вул. Пастера -- Люстдорф", "https://www.eway.in.ua/en/cities/odesa/routes/3"),
    TRACK_5("5", "Автовокзал -- Аркадія", "https://www.eway.in.ua/en/cities/odesa/routes/4"),
    TRACK_7("7", "вул. Паустовського -- Люстдорф", "https://www.eway.in.ua/en/cities/odesa/routes/210"),
    TRACK_10("10", "вул. Іцкаха Рабіна -- пл. Тираспольська", "https://www.eway.in.ua/en/cities/odesa/routes/8"),
    TRACK_11("11", "станція Товарна -- пл. Старосінна", "https://www.eway.in.ua/en/cities/odesa/routes/9"),
    TRACK_12("12", "Херсонський сквер -- Слобідський ринок", "https://www.eway.in.ua/en/cities/odesa/routes/10"),
    TRACK_13("13", "ж/м Шкільний -- пл. Старосінна", "https://www.eway.in.ua/en/cities/odesa/routes/11"),
    TRACK_15("15", "пл. Олексіївська -- Слобідський ринок", "https://www.eway.in.ua/en/cities/odesa/routes/12"),
    TRACK_17("17", "11-а станція Великого Фонтану -- Куликове поле", "https://www.eway.in.ua/en/cities/odesa/routes/13"),
    TRACK_18("18", "16-а станція Великого Фонтану -- Куликове поле", "https://www.eway.in.ua/en/cities/odesa/routes/14"),
    TRACK_20("20", "Херсонський сквер -- Хаджибейський лиман", "https://www.eway.in.ua/en/cities/odesa/routes/16"),
    TRACK_21("21", "", "https://www.eway.in.ua/en/cities/odesa/routes/17"),
    TRACK_26("26", "пл. Старосінна -- 11-а станція Люстдорфської дороги", "https://www.eway.in.ua/en/cities/odesa/routes/18"),
    TRACK_27("27", "Люстдорф -- Рибний порт", "https://www.eway.in.ua/en/cities/odesa/routes/19"),
    TRACK_28("28", "вул. Пастера -- Парк ім. Тараса Шевченка", "https://www.eway.in.ua/en/cities/odesa/routes/1");

    private String number;
    private String startEnd;
    private String link;

    TracksTrams(String number, String startEnd, String link){
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
