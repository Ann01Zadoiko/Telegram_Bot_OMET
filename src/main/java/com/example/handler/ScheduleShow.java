package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.User;
import com.example.feature.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ScheduleShow {

    private final UserService userService;
    private final TelegramBot telegramBot;
    private final MuseumService museumService;

    @SneakyThrows
    @Scheduled(cron = "0 0 18 * * *")
    public void dailyRemember() {

        User user = userService.getByChatId(391736560L);

        String answer = "";
        int number = 1;
        int count = 0;

        List<Museum> byDate = museumService.getByDate(museumService.showDate());
        for (Museum museum: byDate){
            if (museum.getId() != 1L){
                answer += (number++) + ". " + museum.getFullName() + "    " + museum.getPhoneNumber() + "    " + museum.getCountOfPeople() + "\n";
                count += museum.getCountOfPeople();
            }
        }

        answer += "Всього: " + count;

        telegramBot.sendMessage(user.getChatId(), answer);
    }

}
