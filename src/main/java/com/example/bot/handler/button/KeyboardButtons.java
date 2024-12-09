package com.example.bot.handler.button;

import com.example.bot.constance.Button;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardButtons {

    private KeyboardButtons(){}

    public static ReplyKeyboardMarkup getButtons(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add(Button.RENT.getFullName());
        row.add(Button.MUSEUM.getFullName());

        keyboard.add(row);

        row = new KeyboardRow();

        row.add(Button.GENERAL_INFO.getFullName());
        row.add(Button.COMPLAINT.getFullName());

        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }

}
