package com.nexus.back.repositories.operations.impl;

import com.nexus.back.domain.dto.activities.ActivitiesResponseDTO;
import com.nexus.back.domain.entity.Activities;
import com.nexus.back.domain.entity.User;
import com.nexus.back.repositories.ActivitiesRepository;
import com.nexus.back.repositories.operations.ActivitiesDatabaseOperations;
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
