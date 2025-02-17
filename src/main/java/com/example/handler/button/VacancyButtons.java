package com.example.handler.button;

import com.example.constance.info.vacancy.Specification;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class VacancyButtons {

    public static InlineKeyboardMarkup getButtonsSpecification(String callback1, String callback2){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text(Specification.WITH_EXPERIENCE.getName())
                .callbackData(callback1)
                .build();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);

        row.add(buttons);

        InlineKeyboardButton button1 = InlineKeyboardButton
                .builder()
                .text(Specification.WITHOUT_EXPERIENCE.getName())
                .callbackData(callback2)
                .build();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(button1);

        row.add(buttons1);


        markup.setKeyboard(row);

        return markup;
    }
}
