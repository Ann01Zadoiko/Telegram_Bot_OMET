package com.example.feature.notice;

import com.example.feature.transport.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByTransport(Transport transport);

    List<Notice> findByDate(LocalDate date);

}
