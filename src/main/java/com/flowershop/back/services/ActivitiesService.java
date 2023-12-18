package com.flowershop.back.services;

import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.domain.flower.MessageDTO;
import java.util.List;

public interface ActivitiesService {
    void save(MessageDTO message);
    List<ActivitiesResponseDTO> findAllById(String id);
}
