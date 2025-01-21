package com.example.feature.museum;

import java.time.LocalDate;
import java.util.List;

public interface IMuseumService {

    void save(Museum museum);

    List<Museum> getAll();

    Museum getById(Long id);

    void setNewDayToExhibition(LocalDate date);

    List<Museum> getByChatId(Long chatId);

    LocalDate showDate();

    List<Museum> getByDate(LocalDate date);
}
