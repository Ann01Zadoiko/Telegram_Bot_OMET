package com.example.feature.stop;

import com.example.feature.transport.Transport;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stops")
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stops_across")
    private String stopAcross;

    @Column(name = "stops_right_back")
    private String stopRightBack;

    @OneToOne
    @JoinColumn(name = "id_transport")
    private Transport transport;

    @Override
    public String toString() {
        return "Stop{" +
                "id=" + id +
                ", stopAcross='" + stopAcross + '\'' +
                ", stopRightBack='" + stopRightBack + '\'' +
                ", transport=" + transport +
                '}';
    }
}
