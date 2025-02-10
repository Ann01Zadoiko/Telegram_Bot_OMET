package com.example.feature.vacancy;

import java.util.List;

public interface IVacancyService {

    void save(Vacancy vacancy);

    void deleteAdd();

    void saveAll(List<Vacancy> vacancies);

    List<Vacancy> getBySpecification(String specification);
}
