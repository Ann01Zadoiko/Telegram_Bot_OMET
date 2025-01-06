package com.example.handler.button;

import com.example.museum.SocialMediaMuseum;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;

public class LinkButtons {

    public static InlineKeyboardMarkup createLinksMuseumButtons(){


//        // Добавляем кнопку в разметку
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        keyboardMarkup.setKeyboard(Collections.singletonList(Collections.singletonList(webAppButton)));

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        for (SocialMediaMuseum museum: SocialMediaMuseum.values()) {
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

        InlineKeyboardButton webAppButton = new InlineKeyboardButton();
        webAppButton.setText("Назад");
        webAppButton.setCallbackData("BACK");

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(webAppButton);

        row.add(buttons);

        markup.setKeyboard(row);

        return markup;
    }
}
