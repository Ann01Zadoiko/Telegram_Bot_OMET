package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.config.BotConfig;
import com.example.feature.complaint.ComplaintService;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.feature.user.User;
import com.example.feature.user.UserService;
import com.example.feature.vacancy.VacancyService;
import com.example.registration.ComplaintRegistration;
import com.example.registration.MuseumRegistration;
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
public class Schedule {

    private final BotConfig config;
    private final MuseumService museumService;
    private final UserService userService;
    private final ComplaintService complaintService;
    private final UserStateManager stateManager;
    private final MuseumRegistration museumRegistration;
    private final ComplaintRegistration complaintRegistration;
    private final VacancyService vacancyService;
    

    @SneakyThrows
    @Scheduled(cron = "0 0 18 * * *")
    public void dailyRemember() {

        LocalDate lastDay = museumService.getLastDay();

        String answer = "";
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

        new TelegramBot(config, museumService, userService, complaintService,
                stateManager, museumRegistration, complaintRegistration, vacancyService).sendMessage(391736560, answer);

    }
}
