package com.example.handler;

import com.example.handler.registration.complaint.ComplaintBotRegistration;
import com.example.handler.command.finding.FindingBotHandler;
import com.example.handler.command.museum.MuseumBotHandler;
import com.example.handler.command.notice.NoticeBotHandler;
import com.example.handler.command.stops.StopsBotHandler;
import com.example.handler.command.tracks.TracksBotHandler;
import com.example.handler.command.vacancy.VacancyBotHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class CommandHandler {

    private final TracksBotHandler tracksBotHandler;
    private final StopsBotHandler stopsBotHandler;
    private final VacancyBotHandler vacancyBotHandler;
    private final ComplaintBotRegistration complaintBotHandler;
    private final NoticeBotHandler noticeBotHandler;
    private final FindingBotHandler findingBotHandler;
    private final MuseumBotHandler museumBotHandler;

    public void handlerOfCommands(Update update){
        if (update.hasMessage() && update.getMessage().hasText()) {
            tracksBotHandler.handleUpdate(update);
            stopsBotHandler.handleUpdate(update);
            vacancyBotHandler.handleUpdate(update);
            noticeBotHandler.handleUpdate(update);
            findingBotHandler.handleUpdate(update);
            museumBotHandler.handleUpdate(update);
        }

        if (update.hasMessage()){
            complaintBotHandler.handleUpdate(update);
        }

        if (update.hasCallbackQuery()) {
            tracksBotHandler.handleUpdate(update);
            stopsBotHandler.handleUpdate(update);
            vacancyBotHandler.handleUpdate(update);
            noticeBotHandler.handleUpdate(update);
            complaintBotHandler.handleUpdate(update);
            findingBotHandler.handleUpdate(update);
            museumBotHandler.handleUpdate(update);
        }
    }
}
