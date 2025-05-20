package com.example.feature.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {

    List<Transport> findByType(String type);

    Optional<Transport> findByTypeAndNumberOfTrack(String type, String numberOfTrack);

    @Query("select tr.numberOfTrack from Transport tr where tr.type=:type")
    List<String> findNumbersByType(String type);

    @Query("SELECT t FROM Transport t LEFT JOIN FETCH t.notices WHERE t.type = :type AND t.numberOfTrack = :numberOfTrack")
    Optional<Transport> findWithNoticesByTypeAndNumberOfTrack(String type, String numberOfTrack);

}
