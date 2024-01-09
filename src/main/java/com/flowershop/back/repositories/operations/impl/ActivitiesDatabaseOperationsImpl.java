package com.flowershop.back.repositories.operations.impl;

import com.flowershop.back.domain.activities.Activities;
import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.repositories.ActivitiesRepository;
import com.flowershop.back.repositories.operations.ActivitiesDatabaseOperations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ActivitiesDatabaseOperationsImpl implements ActivitiesDatabaseOperations {

    private final ActivitiesRepository activitiesRepository;

    @Override
    public void save(Activities activities){
        activitiesRepository.save(activities);
    }

    @Override
    public List<ActivitiesResponseDTO> findByUser(String id, User user){
        return this.activitiesRepository.findByUser(user.getLogin()).stream().map(ActivitiesResponseDTO::new).toList();
    }
}
