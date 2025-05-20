package com.example.handler.button;

import com.example.constance.info.links.SocialMedia;
import com.example.constance.museum.SocialMediaMuseum;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;

public class LinkButtons {

    private LinkButtons(){}

    public static InlineKeyboardMarkup createLinksMuseumButtons(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        for (SocialMediaMuseum museum: SocialMediaMuseum.values()) {
            WebAppInfo webAppInfo = new WebAppInfo();

            String url = museum.getUrl();


            webAppInfo.setUrl(url);

            // Привязываем к кнопке
            InlineKeyboardButton webAppButton = new InlineKeyboardButton();
            webAppButton.setText(museum.getName());
            webAppButton.setWebApp(webAppInfo);

            List<InlineKeyboardButton> buttons = new ArrayList<>();
            buttons.add(webAppButton);

            row.add(buttons);
        }

        markup.setKeyboard(row);

        return markup;
    }

    public static InlineKeyboardMarkup createLinksGeneralInfoButtons(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        for (SocialMedia museum: SocialMedia.values()) {
            WebAppInfo webAppInfo = new WebAppInfo();
            webAppInfo.setUrl(museum.getUrl());

            // Привязываем к кнопке
            InlineKeyboardButton webAppButton = new InlineKeyboardButton();
            webAppButton.setText(museum.getName());
            webAppButton.setWebApp(webAppInfo);

            List<InlineKeyboardButton> buttons = new ArrayList<>();
            buttons.add(webAppButton);

            row.add(buttons);
        }

        markup.setKeyboard(row);

        return markup;
    }
}
