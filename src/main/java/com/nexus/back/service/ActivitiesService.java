package com.nexus.back.service;

import com.nexus.back.domain.dto.activities.ActivitiesResponseDTO;
import com.nexus.back.domain.dto.flower.MessageDTO;

import java.util.List;

public interface ActivitiesService {
    void save(MessageDTO message);
    List<ActivitiesResponseDTO> findAllById(String id);
}
