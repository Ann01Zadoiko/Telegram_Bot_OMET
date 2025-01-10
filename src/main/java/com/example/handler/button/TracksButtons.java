package com.example.handler.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TracksButtons {

    public static InlineKeyboardMarkup getButtonsTracks(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text("Трамвай")
                .callbackData("Трамвай")
                .build();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);

        row.add(buttons);

        InlineKeyboardButton button1 = InlineKeyboardButton
                .builder()
                .text("Тролейбус")
                .callbackData("Тролейбус")
                .build();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(button1);

        row.add(buttons1);

        markup.setKeyboard(row);

        return markup;
    }
}
