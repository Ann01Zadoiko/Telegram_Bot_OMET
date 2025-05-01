package com.example.constance.info.links;

public enum SocialMedia {
    SITE("https://oget.od.ua/", "Сайт"),
    //   FACEBOOK("https://www.facebook.com/kp.oget/", "Facebook"),
    INSTAGRAM("https://www.instagram.com/kp_oget_staff_/", "Instagram");

    private String url;
    private String name;

    SocialMedia(String url, String name){
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
