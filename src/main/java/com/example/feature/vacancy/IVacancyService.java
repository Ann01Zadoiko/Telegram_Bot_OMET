package com.example.feature.vacancy;

import java.util.List;

public interface IVacancyService {

    void save(Vacancy vacancy);

    Vacancy getBySpecification(String specification);

    List<Vacancy> getAll();
}
