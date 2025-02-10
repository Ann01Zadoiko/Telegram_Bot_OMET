package com.example.constance;

public interface Function {

    String WELCOME = "Вас вітає бот кп омет! У цьому боті Ви можете: подивитися різну інформація, " +
            "пов'язану з електротранспортом міста Одеса, записатися до музею, орендувати будь-який " +
            "транспорт та залишити свій відгук або скаргу. Приємного користування!";
    String HELP_TEXT = "/show [date] (/show 12.01.2025) – показує всіх, хто записався на екскурсії на обраний день\n" +
            "/setDate [date] (/setDate 12.01.2005) – відкриття записі на екскурсії та встановлення дати\n" +
            "/close – закриття записі на екскурсію\n";

    String START = "/start";
    String SET_NEW_DAY = "/setDate";
    String SHOW = "/show";
    String CLOSE = "/close";
    String HELP = "/help";
    String VACANCY = "/vacancy";
}
