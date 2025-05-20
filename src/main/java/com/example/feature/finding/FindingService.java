package com.example.feature.finding;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindingService implements IFindingService {
    private final FindingRepository repository;

    @Override
    public Finding findTopByOrderByIdDesc() {
        return repository.findTopByOrderByIdDesc();
    }

    @Override
    public void add(Finding finding) {
        repository.save(finding);
    }
}
