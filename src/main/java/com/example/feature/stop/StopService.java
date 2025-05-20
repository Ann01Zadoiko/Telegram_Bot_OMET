package com.example.feature.stop;

import com.example.feature.transport.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StopService implements IStopService{

    private final StopRepository stopRepository;

    @Override
    public void add(Stop stop) {
        stopRepository.save(stop);
    }

    @Override
    public List<Stop> getAll() {
        return stopRepository.findAll();
    }

    @Override
    public Stop getByTransport(Transport transport) {
        return stopRepository.findByTransport(transport);
    }

    public void update(Stop stop){
        stopRepository.save(stop);
    }

    public void delete(Transport transport){
        stopRepository.deleteByTransport(transport);
    }
}
