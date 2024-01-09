package com.flowershop.back.services.impl;

import com.flowershop.back.domain.activities.Activities;
import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.services.ActivitiesService;
import com.flowershop.back.repositories.operations.ActivitiesDatabaseOperations;
import com.flowershop.back.repositories.operations.UserDatabaseOperations;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ActivitiesServiceImpl implements ActivitiesService {

    private final ActivitiesDatabaseOperations activitiesDatabaseOperations;
    private final UserDatabaseOperations userDatabaseOperations;

    @Override
    public void save(MessageDTO message) {
        User user = userDatabaseOperations.findByHash(message.hash());

        Activities activities = Activities.builder()
                .user(user.getLogin())
                .remittent(message.email())
                .datetime(LocalDateTime.now())
                .build();

        activitiesDatabaseOperations.save(activities);
    }

    @Override
    public List<ActivitiesResponseDTO> findAllById(String id) {
       User user = userDatabaseOperations.findById(id);
      return this.activitiesDatabaseOperations.findByUser(id, user);
    }
}
