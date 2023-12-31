package com.flowershop.back.services.repo.impl;

import com.flowershop.back.domain.activities.Activities;
import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.repositories.ActivitiesRepository;
import com.flowershop.back.services.repo.ActivitiesMethodsDbs;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivitiesMethodsDbsImpl implements ActivitiesMethodsDbs {

    private final ActivitiesRepository activitiesRepository;

    public ActivitiesMethodsDbsImpl(ActivitiesRepository activitiesRepository) {
        this.activitiesRepository = activitiesRepository;
    }

    @Override
    public void save(Activities activities){
        activitiesRepository.save(activities);
    }

    @Override
    public List<ActivitiesResponseDTO> findByUser(String id, User user){
        return this.activitiesRepository.findByUser(user.getLogin()).stream().map(ActivitiesResponseDTO::new).toList();
    }
}
