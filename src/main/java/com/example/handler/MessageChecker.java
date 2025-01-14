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

    // /count (date), /setDate, /start, phone number, full name, count of people    complaint, full name, phone number
}
