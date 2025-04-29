package com.example.feature.transport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {

    List<Transport> findByType(String type);

    Optional<Transport> findByTypeAndNumberOfTrack(String type, String numberOfTrack);

    @Query("select tr.numberOfTrack from Transport tr where tr.type=:type")
    List<String> findNumbersByType(String type);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Transport t WHERE t.type = :type AND t.numberOfTrack = :numberOfTrack")
    void deleteByTypeAndNumberOfTrack(String type, String numberOfTrack);
}
