package com.example.handler.schedule;

import com.example.bot.TelegramBot;
import com.example.config.BotConfig;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.handler.BotHandler;
import com.example.registration.UserStateManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class Schedule implements ISchedule {

    private final BotConfig config;
    private final MuseumService museumService;
    private final UserStateManager stateManager;
    private final BotHandler botHandler;
    
    @Override
    @SneakyThrows
    @Scheduled(cron = "0 0 18 * * *")
    public void dailyRemember() {

        LocalDate lastDay = museumService.getLastDay();

        String answer = lastDay + "\n";
        int number = 1;
        int count = 0;

        List<Museum> byDate = museumService.getByDate(lastDay);
        for (Museum museum: byDate){
            if (museum.getId() != 1L){
                answer += (number++) + ". " + museum.getFullName() + "    " + museum.getPhoneNumber() + "    " + museum.getCountOfPeople() + "\n";
                count += museum.getCountOfPeople();
            }
        }
        answer += "Всього: " + count;

        new TelegramBot(config, stateManager, botHandler).sendMessage(391736560, answer);

    }
}
