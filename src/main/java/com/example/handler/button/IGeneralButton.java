package com.example.handler.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public interface IGeneralButton {

    InlineKeyboardMarkup getButtons(List<String> names,List<String> callbacks);

}
