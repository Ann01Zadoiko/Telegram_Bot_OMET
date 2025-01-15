package com.example.handler;

import com.example.constance.info.GeneralInfo;
import com.example.constance.info.vacancy.Specification;
import com.example.constance.museum.MuseumEnum;

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
        if (s.length == 1 && !value.startsWith("/start")){
            return true;
        }
        return false;
    }

    public static boolean isFullNameComplaint(String value){
        String[] s = value.split(" ");
        if (s.length == 2 && (!value.startsWith("/count")) && (!value.startsWith("/setDate"))){
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

    public static boolean isCountOfPeople(String value){
        String[] s = value.split(" ");
        if (value.startsWith("/count") && s.length == 2){
            return true;
        }
        return false;
    }

    public static boolean isSetDate(String value){
        String[] s = value.split(" ");
        if (value.startsWith("/setDate") && s.length == 2){
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

}
