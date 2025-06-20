package com.example.feature.transport;

import java.util.List;
import java.util.Optional;

public interface ITransportService {

    void add(Transport transport);

    List<Transport> getAll();

    List<Transport> getByType(String type);

    Transport getByTypeAndNumber(String type, String number);

    List<String> getNumbersByType(String type);

    void deleteByTypeAndNumberOfTrack(String type, String numberOfTrack);


}
