package com.example.handler.button;

import com.example.constance.Button;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


public class KeyboardButtons {

    public static ReplyKeyboardMarkup getButtons(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add(Button.INFO_TRACKS.getFullName());
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

    public static ReplyKeyboardMarkup getButtonsAdmin(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add(Button.INFO_TRACKS.getFullName());
        row.add(Button.MUSEUM.getFullName());

        keyboard.add(row);

        row = new KeyboardRow();

        row.add(Button.GENERAL_INFO.getFullName());
        row.add(Button.COMPLAINT.getFullName());

        keyboard.add(row);

        row = new KeyboardRow();

        row.add("Упр марш");
        row.add("Управ муз");

        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup getButtonsDispecher(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add(Button.INFO_TRACKS.getFullName());
        row.add(Button.MUSEUM.getFullName());

        keyboard.add(row);

        row = new KeyboardRow();

        row.add(Button.GENERAL_INFO.getFullName());
        row.add(Button.COMPLAINT.getFullName());

        keyboard.add(row);

        row = new KeyboardRow();

        row.add("Термінові повідомлення");

        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }
}
