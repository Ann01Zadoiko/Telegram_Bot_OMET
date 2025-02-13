package com.example.feature.museum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MuseumRepository extends JpaRepository<Museum, Long> {

    @Query(nativeQuery = true,
            value = "select count(m.count_of_people)\n" +
                    "from museums m\n" +
                    "where m.date=:date")
    Integer countOfPeopleByDay(LocalDate date);

    List<Museum> findByChatId(Long chatId);

    List<Museum> findByDate(LocalDate date);

    boolean existsByChatId(Long chatId);
}
