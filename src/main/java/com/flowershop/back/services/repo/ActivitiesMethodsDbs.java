package com.flowershop.back.services.repo;

import com.flowershop.back.domain.activities.Activities;
import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.domain.user.User;
import java.util.List;

public interface ActivitiesMethodsDbs {
    List<ActivitiesResponseDTO> findByUser(String id, User user);
    void save(Activities activities);
}
