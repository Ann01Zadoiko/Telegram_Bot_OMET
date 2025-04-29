package com.example.feature.notice;

import com.example.feature.transport.Transport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notices")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reason")
    private String reason;

    @Column(name = "relevance")
    private boolean relevance;

    @ManyToOne
    @JoinColumn(name = "id_transport")
    private Transport transport;
}
