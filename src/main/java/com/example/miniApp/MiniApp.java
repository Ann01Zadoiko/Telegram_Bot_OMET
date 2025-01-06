package com.example.miniApp;

public enum MiniApp {
    NEWS("https://oget.od.ua/category/blog/"),
    TRAVEL_CARD("https://oget.od.ua/proizdni-kvitki/"),
    BENEFITS("https://oget.od.ua/pravila-ta-pilgi/"),
    RULES("https://oget.od.ua/wp-content/uploads/2022/08/%D0%9F%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D0%B0-%D0%BA%D0%BE%D1%80%D0%B8%D1%81%D1%82%D1%83%D0%B2%D0%B0%D0%BD%D0%BD%D1%8F-2022.pdf");

    private String link;

    MiniApp(String link){
        this.link = link;
    }

    public String getLink(){
        return link;
    }
}
