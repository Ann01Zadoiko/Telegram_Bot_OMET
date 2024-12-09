package com.example.bot.handler.button;

import com.example.bot.constance.GeneralInfo;
import com.example.bot.constance.Museum;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class GeneralInfoButtons {

    private GeneralInfoButtons(){}

    public static InlineKeyboardMarkup getButtons(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        for (GeneralInfo generalInfo: GeneralInfo.values()) {
            InlineKeyboardButton button = InlineKeyboardButton
                    .builder()
                    .text(generalInfo.getFullName())
                    .callbackData(generalInfo.name())
                    .build();

            List<InlineKeyboardButton> buttons = new ArrayList<>();
            buttons.add(button);

            row.add(buttons);
        }

        markup.setKeyboard(row);

        return markup;
    }
}
