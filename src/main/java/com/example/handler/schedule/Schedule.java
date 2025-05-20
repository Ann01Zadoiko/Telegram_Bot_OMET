package com.example.handler.schedule;

import com.example.feature.museum.MuseumService;
import com.example.handler.BotSenderService;
import com.example.handler.printer.MuseumPrinter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@Slf4j
@RequiredArgsConstructor
public class Schedule implements ISchedule {

    private final MuseumService museumService;
    private final BotSenderService sender;


    @Override
    @SneakyThrows
    @Scheduled(cron = "0 0 18 * * *")
    public void dailyRemember() {

        LocalDate lastDay = museumService.getLastDay();
        String answer = new MuseumPrinter(museumService).print(lastDay);

        sender.sendMessage(391736560L, answer);

        log.info("Notification about museum: {}", answer);
    }
}
