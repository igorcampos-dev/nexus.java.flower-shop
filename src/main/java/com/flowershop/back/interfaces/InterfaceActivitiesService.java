package com.flowershop.back.interfaces;

import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.domain.flower.MessageDTO;
import java.util.List;

public interface InterfaceActivitiesService {
    void save(MessageDTO message);
    List<ActivitiesResponseDTO> findAll();
}
