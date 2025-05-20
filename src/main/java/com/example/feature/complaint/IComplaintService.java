package com.example.feature.complaint;

import java.util.List;

public interface IComplaintService {

    void save(Complaint complaint);

    List<Complaint> getAll();

    List<Complaint> findByChatId(Long chatId);

    boolean existsByChatId(Long chatId);
}
