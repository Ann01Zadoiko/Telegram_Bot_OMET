package com.example.handler.button;

import com.example.museum.Museum;
import com.example.museum.SocialMediaMuseum;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MuseumButtons {

    private MuseumButtons(){}

    public static InlineKeyboardMarkup getButtons(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        for (Museum museum: Museum.values()) {
            InlineKeyboardButton button = InlineKeyboardButton
                    .builder()
                    .text(museum.getFullName())
                    .callbackData(museum.name())
                    .build();

            List<InlineKeyboardButton> buttons = new ArrayList<>();
            buttons.add(button);

            row.add(buttons);
        }

        markup.setKeyboard(row);

        return markup;
    }
}
