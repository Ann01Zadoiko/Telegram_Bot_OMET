package com.example.feature.museum;

import java.time.LocalDate;
import java.util.List;

public interface IMuseumService {

    void save(Museum museum);

    List<Museum> getAll();

    Museum getById(Long id);

    Integer countOfPeopleByDay(LocalDate date);

    void setNewDayToExhibition(LocalDate date);

    Museum getByChatId(Long chatId);

    LocalDate showDate();
}
