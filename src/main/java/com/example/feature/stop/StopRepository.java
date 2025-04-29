package com.example.feature.stop;

import com.example.feature.transport.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

    Stop findByTransport(Transport transport);

    @Transactional
    @Query("DELETE FROM Stop s WHERE s.transport = :transport")
    void deleteByTransport(Transport transport);
}