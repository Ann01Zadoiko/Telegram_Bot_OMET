package com.example.handler.button;

import com.example.constance.info.vacancy.Specification;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class VacancyButtons {

    public static InlineKeyboardMarkup getButtonsSpecification(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();


        for (Specification specification: Specification.values()){
            InlineKeyboardButton button = InlineKeyboardButton
                    .builder()
                    .text(specification.getName())
                    .callbackData(specification.name())
                    .build();

            List<InlineKeyboardButton> buttons = new ArrayList<>();
            buttons.add(button);

            row.add(buttons);
        }

        markup.setKeyboard(row);

        return markup;
    }
}
