package com.example.feature.transport;

import com.example.feature.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TransportService implements ITransportService{

    private final TransportRepository repository;
    private final NoticeRepository noticeRepository;

    @Override
    public void add(Transport transport) {
        repository.save(transport);
    }

    @Override
    public List<Transport> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Transport> getByType(String type) {
        return repository.findByType(type);
    }

    @Override
    public Transport getByTypeAndNumber(String type, String number) {
        return repository.findByTypeAndNumberOfTrack(type, number).get();
    }

    @Override
    public List<String> getNumbersByType(String type) {
        return repository.findNumbersByType(type);
    }

    @Override
    public void updateField(Transport transport, String field, String value) {
        switch (field) {
            case "номер маршрута" -> transport.setNumberOfTrack(value);
            case "кіневі зупинки" -> transport.setStopsStartEnd(value);
            case "початок роботи" -> transport.setTimeStart(value);
            case "останнє відправлення" -> transport.setTimeEnd(value);
            case "посилання" -> transport.setLink(value);
            case "інтервал у будні дні" -> transport.setIntervalWeekdays(value);
            case "інтервал у вихідні" -> transport.setIntervalWeekend(value);
        }
        repository.save(transport);
    }

    public void update(Transport transport) {
        repository.save(transport); // или реализация вручную
    }

    @Transactional
    @Override
    public void deleteByTypeAndNumberOfTrack(String type, String numberOfTrack) {
        Transport transport = repository.findWithNoticesByTypeAndNumberOfTrack(type, numberOfTrack)
                .orElseThrow(() -> new RuntimeException("Transport not found"));

        noticeRepository.deleteAll(transport.getNotices());

        repository.delete(transport);
    }

}
