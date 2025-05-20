package com.example.feature.finding;


public interface IFindingService {

    Finding findTopByOrderByIdDesc();

    void add(Finding finding);

}
