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
    public void setNewDayToExhibition(LocalDate date) {
        Museum museum = getById(1L);
        museum.setDate(date);
        save(museum);
    }

    @Override
    public List<Museum> getByChatId(Long chatId){
        return repository.findByChatId(chatId);
    }

    @Override
    public LocalDate showDate(){
        Museum museum = getById(1L);
        return museum.getDate();
    }

    @Override
    public List<Museum> getByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    @Override
    public boolean existsByChatId(Long chatId) {
        return repository.existsByChatId(chatId);
    }
}
