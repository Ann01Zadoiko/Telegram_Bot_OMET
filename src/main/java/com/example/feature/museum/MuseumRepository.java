package com.example.feature.museum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MuseumRepository extends JpaRepository<Museum, Long> {

    List<Museum> findByChatId(Long chatId);

    List<Museum> findByDate(LocalDate date);

    boolean existsByChatId(Long chatId);

    @Query("SELECT DISTINCT m.date FROM Museum m")
    List<LocalDate> findAllDates();

}
