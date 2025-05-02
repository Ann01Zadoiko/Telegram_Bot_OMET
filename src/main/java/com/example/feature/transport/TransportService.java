package com.example.feature.transport;


import com.example.feature.notice.Notice;
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
    public Optional<Transport> getByTypeAndNumber(String type, String number) {
        return repository.findByTypeAndNumberOfTrack(type, number);
    }

    @Override
    public List<String> getNumbersByType(String type) {
        return repository.findNumbersByType(type);
    }

    @Override
    public void updateField(Transport transport, String field, String value) {
        switch (field) {
            case "номер маршрута" -> transport.setNumberOfTrack(value);
            case "конечные остановки" -> transport.setStopsStartEnd(value);
            case "время работы" -> transport.setTime(value);
            case "ссылка" -> transport.setLink(value);
            case "интервал" -> transport.setInterval(value);
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

        noticeRepository.deleteAll(transport.getNotices()); // теперь точно загружены

        repository.delete(transport);
    }

}
