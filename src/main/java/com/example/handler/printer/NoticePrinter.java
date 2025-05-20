package com.example.handler.printer;

import com.example.constance.notice.NoticeEnum;
import com.example.feature.notice.Notice;
import com.example.feature.notice.NoticeService;
import com.example.handler.command.notice.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticePrinter {

    private final NoticeService noticeService;

    public String print(){
        List<Notice> notices = noticeService.getByDate(LocalDate.now());
        notices.sort(Comparator.comparing(Notice::getTime));

        StringBuilder text = new StringBuilder("Термінові повідомлення за ");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM");
        text.append(dateTimeFormatter.format(LocalDate.now())).append(":\n");


        for (Notice notice: notices){

            text.append("\n").append(notice.getReason());
        }

        log.info("Print notice:\n{}", text);

        return String.valueOf(text);
    }
}
