package com.example.feature.finding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FindingRepository extends JpaRepository<Finding, Long> {

    Finding findTopByOrderByIdDesc();
}
