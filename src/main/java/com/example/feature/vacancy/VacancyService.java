package com.example.feature.vacancy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VacancyService implements IVacancyService {

    private final VacancyRepository repository;

    @Override
    public void save(Vacancy vacancy) {
        repository.save(vacancy);
    }

    @Override
    public Vacancy getBySpecification(String specification) {
        return repository.findBySpecification(specification);
    }

    @Override
    public List<Vacancy> getAll() {
        return repository.findAll();
    }
}
