package com.example.constance.transport;

public enum TransportEnum {
    STOPS_START_END("Кінцеві зупинки: "),
    TIME_START("Початок работи: "),
    TIME_END("Останнє відправлення: "),
    INTERVAL_WEEKDAYS("Інтервал у будні дні: "),
    INTERVAL_WEEKEND("Інтервал у вихідні: ");

    private final String name;

    TransportEnum(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
