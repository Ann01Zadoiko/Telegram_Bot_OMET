package com.example.handler.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class BackButton {

    public static InlineKeyboardMarkup getButtons(String callback){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text("Назад")
                .callbackData(callback)
                .build();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);

        row.add(buttons);

        markup.setKeyboard(row);

        return markup;
    }
}
