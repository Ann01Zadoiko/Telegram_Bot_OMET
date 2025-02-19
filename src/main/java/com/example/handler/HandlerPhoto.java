package com.example.handler;

import com.example.bot.TelegramBot;
import com.example.constance.complaint.Complain;
import com.example.feature.complaint.Complaint;
import com.example.feature.complaint.ComplaintService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HandlerPhoto {

    private final ComplaintService complaintService;

    @SneakyThrows
    public void handlerOfPhoto(Update update, TelegramBot bot){
        List<Complaint> byChatId = complaintService.findByChatId(update.getMessage().getChatId());
        Complaint complaint = byChatId.get(byChatId.size() - 1);

        bot.processPhotoAndSendEmail(update,
                complaint.getFullName() + "\n" + complaint.getPhoneNumber() + "\n" + complaint.getText());

        bot.sendMessage(update.getMessage().getChatId(), Complain.STEP_7.getText());
    }

}
