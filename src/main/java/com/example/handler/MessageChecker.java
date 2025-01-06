package com.example.handler;

import com.example.museum.Museum;

public class MessageChecker {

    public static boolean isEnumValueStatus(String value) {
        for (Museum e : Museum.values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
