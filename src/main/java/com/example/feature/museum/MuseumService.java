package com.example.feature.museum;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MuseumService implements IMuseumService{

    private final MuseumRepository repository;

    @Override
    public void save(Museum museum) {
        repository.save(museum);
    }

    @Override
    public List<Museum> getAll() {
        return repository.findAll();
    }

    @Override
    public Museum getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Integer countOfPeopleByDay(LocalDate date) {
        return repository.countOfPeopleByDay(date);
    }

    @Override
    public void setNewDayToExhibition(LocalDate date) {
        Museum museum = getById(1L);
        museum.setDate(date);
        save(museum);
    }
}
