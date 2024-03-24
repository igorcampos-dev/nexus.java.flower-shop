package com.nexus.back.repository.operations;

import com.nexus.back.domain.dto.activities.ActivitiesResponseDTO;
import com.nexus.back.domain.entity.Activities;
import com.nexus.back.domain.entity.User;

import java.util.List;

public interface ActivitiesDatabaseOperations {
    List<ActivitiesResponseDTO> findByUser(String id, User user);
    void save(Activities activities);
}
