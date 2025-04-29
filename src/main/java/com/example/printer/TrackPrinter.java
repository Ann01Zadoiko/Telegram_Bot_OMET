package com.example.printer;

import com.example.feature.transport.Transport;

public class TrackPrinter {

    public static String print(Transport transport){
        if (transport.getInterval().length()>5){
            String[] s = transport.getInterval().split(" ");

            return transport.getType() + " " + transport.getNumberOfTrack() + "\n" +
                    transport.getStopsStartEnd() + "\nЧас роботи: " +
                    transport.getTime() + "\nІнтервал у будні дні: " + s[0] +
                    "\nІнтервад у вихідні: " + s[1];
        } else {
            return transport.getType() + " " + transport.getNumberOfTrack() + "\n" +
                    transport.getStopsStartEnd() + "\nЧас роботи: " +
                    transport.getTime() + "\nІнтервал у будні дні: " + transport.getInterval() +
                    "\nІнтервад у вихідні: " + transport.getInterval();
        }


    }
}
