package com.example.handler;

import com.example.constance.Function;
import com.example.constance.info.GeneralInfo;
import com.example.constance.museum.MuseumEnum;
import com.example.constance.Button;

public class MessageChecker {

    public static boolean isEnumValueStatus(String value) {
        for (MuseumEnum e : MuseumEnum.values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEnumValueGeneralInfo(String value) {
        for (GeneralInfo e : GeneralInfo.values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSetDate(String value){
        String[] s = value.split(" ");
        if (value.startsWith(Function.SET_NEW_DAY) && s.length == 2){
            return true;
        }
        return false;
    }

    public static boolean isShow(String value){
        String[] s = value.split(" ");
        if (s.length == 2 &&
                value.startsWith(Function.SHOW) &&
                !value.startsWith(Function.SET_NEW_DAY) &&
                !value.equals(Button.COMPLAINT.getFullName()) &&
                !value.equals(Button.GENERAL_INFO.getFullName())){
            return true;
        }
        return false;
    }

    public static boolean isClose(String value){
        return (value.equals(Function.CLOSE));
    }

    public static boolean isHelp(String value){
        return value.equals(Function.HELP);
    }

}
