package com.example.feature.vacancy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query("from Vacancy v where v.specification = :specification")
    Vacancy findBySpecification(String specification);
}
