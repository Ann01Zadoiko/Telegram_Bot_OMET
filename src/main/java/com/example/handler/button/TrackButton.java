package com.example.handler.button;

import com.example.feature.transport.Transport;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;


public class TrackButton {

    public static InlineKeyboardMarkup getButton(Transport transport){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> row = new ArrayList<>();


        WebAppInfo webAppInfo = new WebAppInfo();
        webAppInfo.setUrl(transport.getLink());
        // Привязываем к кнопке
        InlineKeyboardButton webAppButton = new InlineKeyboardButton();
        webAppButton.setText("Посилання на карту");
        webAppButton.setWebApp(webAppInfo);

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(webAppButton);

        row.add(buttons);

        InlineKeyboardButton button1 = InlineKeyboardButton
                .builder()
                .text("Зупинки")
                .callbackData("STOPS " + transport.getType() + " " + transport.getNumberOfTrack())
                .build();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(button1);
        row.add(buttons1);

        markup.setKeyboard(row);

        return markup;
    }
}
