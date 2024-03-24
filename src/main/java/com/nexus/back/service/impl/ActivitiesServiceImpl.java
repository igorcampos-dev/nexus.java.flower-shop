package com.nexus.back.service.impl;

import com.nexus.back.domain.dto.activities.ActivitiesResponseDTO;
import com.nexus.back.domain.dto.flower.MessageDTO;
import com.nexus.back.domain.entity.Activities;
import com.nexus.back.domain.entity.User;
import com.nexus.back.repository.operations.ActivitiesDatabaseOperations;
import com.nexus.back.repository.operations.UserDatabaseOperations;
import com.nexus.back.service.ActivitiesService;
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

        activitiesDatabaseOperations.save(Activities.builder()
                                                    .user(user.getLogin())
                                                    .remittent(message.email())
                                                    .datetime(LocalDateTime.now())
                                                    .build());
    }

    @Override
    public List<ActivitiesResponseDTO> findAllById(String id) {
       User user = userDatabaseOperations.findById(id);
      return this.activitiesDatabaseOperations.findByUser(id, user);
    }
}
