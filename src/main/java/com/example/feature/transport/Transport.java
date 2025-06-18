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

    @Column(name = "link")
    private String link;

    @OneToOne(mappedBy = "transport", cascade = CascadeType.ALL)
    private Stop stop;

    @OneToMany(mappedBy = "transport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> notices;

    @Column(name = "interval_weekdays")
    private String intervalWeekdays;

    @Column(name = "interval_weekend")
    private String intervalWeekend;

    @Column(name = "time_start")
    private String timeStart;

    @Column(name = "time_end")
    private String timeEnd;

    @Column(name = "work")
    private boolean work;

    @Column(name = "text")
    private String text;

    @Override
    public String toString() {
        return "Transport{" +
                "text='" + text + '\'' +
                ", work=" + work +
                ", timeEnd='" + timeEnd + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", intervalWeekend='" + intervalWeekend + '\'' +
                ", intervalWeekdays='" + intervalWeekdays + '\'' +
                ", link='" + link + '\'' +
                ", stopsStartEnd='" + stopsStartEnd + '\'' +
                ", numberOfTrack='" + numberOfTrack + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                '}';
    }
}

