package com.example.handler.printer;

import com.example.constance.transport.TransportEnum;
import com.example.feature.transport.Transport;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrackPrinter {

    private TrackPrinter(){}

    public static String print(Transport transport){
        log.info("Print track({} {})", transport.getType(), transport.getNumberOfTrack());

        return transport.getType() + " " + transport.getNumberOfTrack() + "\n\n" +
                transport.getStopsStartEnd() + "\n\n" +
                TransportEnum.TIME_START.getName() + "\n" + transport.getTimeStart() + "\n\n" +
                TransportEnum.TIME_END.getName() + "\n" + transport.getTimeEnd() + "\n\n" +
                TransportEnum.INTERVAL_WEEKDAYS.getName() + transport.getIntervalWeekdays() + " хв.\n" +
                TransportEnum.INTERVAL_WEEKEND.getName() + transport.getIntervalWeekend() + " хв.";
    }
}
