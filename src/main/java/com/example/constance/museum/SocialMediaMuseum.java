package com.example.constance.museum;

public enum SocialMediaMuseum {
    SITE("https://oget.od.ua/muzei/", "Сайт"),
    FACEBOOK("https://www.facebook.com/museumoget", "Facebook"),
    INSTAGRAM("https://www.instagram.com/museum_kp_omet/", "Instagram");

    private String url;
    private String name;

    SocialMediaMuseum(String url, String name){
        this.url = url;
        this.name = name;
    }

    public String getUrl(){
        return url;
    }

    public String getName(){
        return name;
    }
}
