package com.example.feature.notice;

import com.example.feature.transport.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<GroupedNoticeDto> getGroupedNoticesByUser() {
        List<Notice> all = getAll();

        return all.stream()
                .collect(Collectors.groupingBy(Notice::getDate))
                .entrySet().stream()
                .map(entry -> new GroupedNoticeDto(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(notice -> new NoticeDto(notice.getReason()))
                                .collect(Collectors.toList())
                ))
                .sorted(Comparator.comparing(GroupedNoticeDto::getDate).reversed())
                .collect(Collectors.toList());
    }
}
