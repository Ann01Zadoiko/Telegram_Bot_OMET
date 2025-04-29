package com.example.handler.schedule;

import com.example.bot.TelegramBot;
import com.example.config.BotConfig;
import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import com.example.handler.BotHandler;
import com.example.handler.BotSenderService;
import com.example.handler.command.complaint.ComplaintBotHandler;
import com.example.handler.command.notice.NoticeBotHandler;
import com.example.handler.command.stops.StopsBotHandler;
import com.example.handler.command.tracks.TracksBotHandler;
import com.example.handler.command.vacancy.VacancyBotHandler;
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
    private final TracksBotHandler tracksBotHandler;
    private final StopsBotHandler stopsBotHandler;
    private final BotSenderService botSenderService;
    private final VacancyBotHandler vacancyBotHandler;
    private final ComplaintBotHandler complaintBotHandler;
    private final NoticeBotHandler noticeBotHandler;

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

        new TelegramBot(config,
                stateManager,
                botHandler,
                tracksBotHandler,
                stopsBotHandler,
                botSenderService,
                vacancyBotHandler,
                complaintBotHandler,
                noticeBotHandler).
                sendMessage(391736560, answer);


    }
}
