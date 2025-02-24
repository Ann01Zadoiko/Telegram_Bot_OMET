package com.example.handler.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class GeneralButton implements IGeneralButton{


    @Override
    public InlineKeyboardMarkup getButtons(List<String> names, List<String> callbacks) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            InlineKeyboardButton button = InlineKeyboardButton
                    .builder()
                    .text(names.get(i))
                    .callbackData(callbacks.get(i))
                    .build();

            List<InlineKeyboardButton> buttons = new ArrayList<>();
            buttons.add(button);

            row.add(buttons);
        }

        markup.setKeyboard(row);

        return markup;
    }
}
