package com.example.feature.stop;

import com.example.feature.transport.Transport;

import java.util.List;

public interface IStopService {

    void add(Stop stop);

    List<Stop> getAll();

    Stop getByTransport(Transport transport);

    void update(Stop stop);

    void delete(Transport transport);
}
