package com.example.printer;

import com.example.feature.notice.Notice;
import com.example.feature.notice.NoticeService;
import com.example.feature.transport.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticePrinter {

    private final NoticeService noticeService;

    public String print(LocalDate date){
        List<Notice> notices = noticeService.getByDate(date);
        notices.sort(Comparator.comparing(Notice::getTime));

        StringBuilder text = new StringBuilder("Термінові повідомлення за ");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM");
        text.append(dateTimeFormatter.format(LocalDate.now())).append(":\n");



        for (Notice notice: notices){
            String type = "";

            if (notice.getTransport().getType().equals("трамвай")){
                type = "трамвая";
            } else {
                type =
                        "тролейбуса";
            }

            text
                    .append("\n")
                    .append(notice.getTime().getHour() + ":" + notice.getTime().getMinute())
                    .append(" ")
                    .append(notice.getReason())
                    .append(" ")
                    .append(type + " " + notice.getTransport().getNumberOfTrack());
        }

        return String.valueOf(text);
    }
}
