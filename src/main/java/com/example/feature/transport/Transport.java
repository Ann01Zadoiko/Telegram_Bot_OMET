package com.example.feature.transport;


import com.example.feature.notice.Notice;
import com.example.feature.stop.Stop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "transports")
@Entity
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private  String type;

    @Column(name = "number_of_track")
    private String numberOfTrack;

    @Column(name = "stops_start_end")
    private String stopsStartEnd;

    @Column(name = "time_start_end")
    private String time;

    @Column(name = "link")
    private String link;

    @Column(name = "interval_time")
    private String interval;

    @OneToOne(mappedBy = "transport", cascade = CascadeType.ALL)
    private Stop stop;

    @OneToMany(mappedBy = "transport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> notices;

    @Override
    public String toString() {
        return "Transport{" +
                "interval='" + interval + '\'' +
                ", link='" + link + '\'' +
                ", time='" + time + '\'' +
                ", stopsStartEnd='" + stopsStartEnd + '\'' +
                ", numberOfTrack='" + numberOfTrack + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                '}';
    }
}
