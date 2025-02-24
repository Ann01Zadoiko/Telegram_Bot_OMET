package com.example.constance.info.links;

public enum Link {
    NEWS("Новини","https://oget.od.ua/category/blog/"),
    TRAVEL_CARD("Проїзні","https://oget.od.ua/proizdni-kvitki/"),
    BENEFITS("Пільги","https://oget.od.ua/pravila-ta-pilgi/"),
    RULES("Правила користування електротранспортом","https://oget.od.ua/wp-content/uploads/2022/08/%D0%9F%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D0%B0-%D0%BA%D0%BE%D1%80%D0%B8%D1%81%D1%82%D1%83%D0%B2%D0%B0%D0%BD%D0%BD%D1%8F-2022.pdf");

    private String name;
    private String url;

    Link(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }
}
