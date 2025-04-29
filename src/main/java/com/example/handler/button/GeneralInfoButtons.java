package com.example.handler.button;

import com.example.constance.info.GeneralInfo;
import com.example.constance.info.links.Link;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.List;

public class GeneralInfoButtons {

    private GeneralInfoButtons(){}

    public static InlineKeyboardMarkup getButtons(){
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> row = new ArrayList<>();

        //vacancy
        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text(GeneralInfo.VACANCY.getFullName())
                .callbackData(GeneralInfo.VACANCY.name())
                .build();

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(button);

        row.add(buttons);

        //study
        InlineKeyboardButton button1 = InlineKeyboardButton
                .builder()
                .text(GeneralInfo.STUDY.getFullName())
                .callbackData(GeneralInfo.STUDY.name())
                .build();

        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(button1);

        row.add(buttons1);

        //tracks
        InlineKeyboardButton button2 = InlineKeyboardButton
                .builder()
                .text(GeneralInfo.RENT.getFullName())
                .callbackData(GeneralInfo.RENT.name())
                .build();

        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        buttons2.add(button2);

        row.add(buttons2);

        //social media
        InlineKeyboardButton button3 = InlineKeyboardButton
                .builder()
                .text(GeneralInfo.SOCIAL_MEDIA.getFullName())
                .callbackData(GeneralInfo.SOCIAL_MEDIA.name())
                .build();

        List<InlineKeyboardButton> buttons3 = new ArrayList<>();
        buttons3.add(button3);

        row.add(buttons3);

        //news
        WebAppInfo webAppInfo = new WebAppInfo();
        webAppInfo.setUrl(Link.NEWS.getUrl());

        InlineKeyboardButton webAppButton = new InlineKeyboardButton();
        webAppButton.setText(Link.NEWS.getName());
        webAppButton.setWebApp(webAppInfo);

        List<InlineKeyboardButton> buttons4 = new ArrayList<>();
        buttons4.add(webAppButton);

        row.add(buttons4);

        //travel cards
        WebAppInfo webAppInfo1 = new WebAppInfo();
        webAppInfo1.setUrl(Link.TRAVEL_CARD.getUrl());

        InlineKeyboardButton webAppButton1 = new InlineKeyboardButton();
        webAppButton1.setText(Link.TRAVEL_CARD.getName());
        webAppButton1.setWebApp(webAppInfo1);

        List<InlineKeyboardButton> buttons5 = new ArrayList<>();
        buttons5.add(webAppButton1);

        row.add(buttons5);

        //benefits
        WebAppInfo webAppInfo2 = new WebAppInfo();
        webAppInfo2.setUrl(Link.BENEFITS.getUrl());

        InlineKeyboardButton webAppButton2 = new InlineKeyboardButton();
        webAppButton2.setText(Link.BENEFITS.getName());
        webAppButton2.setWebApp(webAppInfo2);

        List<InlineKeyboardButton> buttons6 = new ArrayList<>();
        buttons6.add(webAppButton2);

        row.add(buttons6);

        //rules
        WebAppInfo webAppInfo3 = new WebAppInfo();
        webAppInfo3.setUrl(Link.RULES.getUrl());

        InlineKeyboardButton webAppButton3 = new InlineKeyboardButton();
        webAppButton3.setText(Link.RULES.getName());
        webAppButton3.setWebApp(webAppInfo3);

        List<InlineKeyboardButton> buttons7 = new ArrayList<>();
        buttons7.add(webAppButton3);

        row.add(buttons7);

        markup.setKeyboard(row);

        return markup;
    }
}
