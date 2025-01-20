package com.example.handler;

import com.example.constance.Function;
import com.example.constance.info.GeneralInfo;
import com.example.constance.info.vacancy.Specification;
import com.example.constance.museum.MuseumEnum;
import com.example.handler.button.Button;

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

    public static boolean isEnumValueSpecification(String value) {
        for (Specification e : Specification.values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFullNameMuseum(String value){
        String[] s = value.split(" ");
        if (s.length == 1 && !value.startsWith("/start")  && !value.equals(Button.MUSEUM.getFullName())
        && !value.equals(Button.RENT.getFullName()) && !value.equals(Function.SET_NEW_DAY) && !value.equals(Function.CLOSE)
        && !value.equals(Function.SHOW) && !value.startsWith("0") && !value.startsWith("+380")
        && !value.equals(Function.HELP) && value.length() > 2){
            return true;
        }
        return false;
    }

    public static boolean isFullNameComplaint(String value){
        String[] s = value.split(" ");
        if (s.length == 2 && (!value.startsWith("/setDate")) &&
        !value.startsWith(Function.SHOW)  && !value.equals(Button.COMPLAINT.getFullName())
                && !value.equals(Button.GENERAL_INFO.getFullName())){
            return true;
        }
        return false;
    }

    public static boolean isPhoneNumberMuseum(String value){
        if (value.startsWith("0") && value.length() == 10){
            return true;
        }
        return false;
    }

    public static boolean isPhoneNumberComplaint(String value){
        if (value.startsWith("+380") && value.length() == 13){
            return true;
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


    public static boolean isComplaint(String value){
        String[] s = value.split(" ");
        if (s.length > 2){
            return true;
        }
        return false;
    }

    public static boolean isShow(String value){
        String[] s = value.split(" ");
        if (s.length == 2 && value.startsWith(Function.SHOW)){
            return true;
        }
        return false;
    }

    public static boolean isClose(String value){
        return (value.equals(Function.CLOSE)) ? true : false;
    }

    public static boolean isCountOfPeople(String value){
        if (value.length() < 3){
            return true;
        }
        return false;
    }

    public static boolean isHelp(String value){
        return value.equals(Function.HELP);
    }
}
