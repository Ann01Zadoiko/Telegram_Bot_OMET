package com.example.handler.printer;

import com.example.feature.museum.Museum;
import com.example.feature.museum.MuseumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MuseumPrinter {
    private final MuseumService museumService;

    public String print( LocalDate date){
        String answer = "";
        int number = 1;
        int count = 0;

        List<Museum> byDate = museumService.getByDate(date);
        for (Museum museum: byDate){
            if (museum.getId() != 1L){
                answer += (number++) + ". " + museum.getFullName() + "    " + museum.getPhoneNumber() + "    " + museum.getCountOfPeople() + "\n";
                count += museum.getCountOfPeople();
            }
        }
        answer += "Всього: " + count;

        return answer;
    }
}
