package com.flowershop.back.repositories.operations;

import com.flowershop.back.domain.activities.Activities;
import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.domain.user.User;
import java.util.List;

public interface ActivitiesDatabaseOperations {
    List<ActivitiesResponseDTO> findByUser(String id, User user);
    void save(Activities activities);
}
