package com.example.feature.notice;

import com.example.feature.transport.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService implements INoticeService {

    private final NoticeRepository repository;

    @Override
    public void save(Notice notice) {
        repository.save(notice);
    }

    @Override
    public List<Notice> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Notice> getByTransport(Transport transport) {
        return repository.findByTransport(transport);
    }

    @Override
    public List<Notice> getByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Notice> getByDateAndTransport(LocalDate date, Transport transport){
        return repository.findByDateAndTransport(date, transport);
    }

    public List<LocalDate> getDates(){
        return repository.findDistinctDays();
    }
}
