package com.example.feature.notice;

import com.example.feature.transport.Transport;

import java.time.LocalDate;
import java.util.List;

public interface INoticeService {

    void save(Notice notice);

    List<Notice> getAll();

    List<Notice> getByTransport(Transport transport);

    List<Notice> getByDate(LocalDate date);

    void delete(Long id);
}
