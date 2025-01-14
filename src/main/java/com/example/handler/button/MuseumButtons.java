package com.example.handler.button;

import com.example.constance.museum.MuseumEnum;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MuseumButtons {

    private MuseumButtons(){}

    public static InlineKeyboardMarkup getButtons(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        for (MuseumEnum museum: MuseumEnum.values()) {
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

    public static InlineKeyboardMarkup getButtonsYesOrNo(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text("Так")
                .callbackData("YES_MUSEUM")
                .build();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);

        row.add(buttons);

        InlineKeyboardButton button1 = InlineKeyboardButton
                .builder()
                .text("Ні")
                .callbackData("NO_MUSEUM")
                .build();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(button1);

        row.add(buttons1);


        markup.setKeyboard(row);

        return markup;
    }
}
