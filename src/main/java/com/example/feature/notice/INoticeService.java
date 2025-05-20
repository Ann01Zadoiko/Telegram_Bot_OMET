package com.example.feature.notice;

import java.time.LocalDate;
import java.util.List;

public interface INoticeService {

    void save(Notice notice);

    List<Notice> getAll();

    List<Notice> getByDate(LocalDate date);

}
