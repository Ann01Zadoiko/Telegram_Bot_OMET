package com.example.feature.complaint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService implements IComplaintService{

    private final ComplaintRepository repository;

    @Override
    public void save(Complaint complaint) {
        repository.save(complaint);
    }

    @Override
    public Complaint getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Complaint> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Complaint> findByChatId(Long chatId) {
        return repository.findByChatId(chatId);
    }

    @Override
    public boolean existsByChatId(Long chatId) {
        return repository.existsByChatId(chatId);
    }
}
