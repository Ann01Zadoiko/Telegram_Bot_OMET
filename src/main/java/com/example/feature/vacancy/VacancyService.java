package com.example.feature.vacancy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VacancyService implements IVacancyService {

    private final VacancyRepository repository;

    @Override
    public void save(Vacancy vacancy) {
        repository.save(vacancy);
    }

    @Override
    public void deleteAdd() {
        repository.deleteAll();
    }

    @Override
    public void saveAll(List<Vacancy> vacancies) {
        repository.saveAll(vacancies);
    }

    @Override
    public List<Vacancy> getBySpecification(String specification) {
        return repository.findBySpecification(specification);
    }

    public Vacancy getByLastId(){
        List<Vacancy> all = repository.findAll();
        Optional<Vacancy> byId = repository.findById((long) (all.size() - 1));
        return byId.get();
    }

    @Override
    public List<Vacancy> getAll() {
        return repository.findAll();
    }
}
