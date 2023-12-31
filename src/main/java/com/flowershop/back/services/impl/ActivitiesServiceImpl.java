package com.flowershop.back.services.impl;

import com.flowershop.back.domain.activities.Activities;
import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.services.ActivitiesService;
import com.flowershop.back.services.repo.ActivitiesMethodsDbs;
import com.flowershop.back.services.repo.UserMethodsDbs;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivitiesServiceImpl implements ActivitiesService {

    private final ActivitiesMethodsDbs activitiesMethodsDbs;
    private final UserMethodsDbs userMethodsDbs;

    public ActivitiesServiceImpl(ActivitiesMethodsDbs activitiesMethodsDbs, UserMethodsDbs userMethodsDbs) {
        this.activitiesMethodsDbs = activitiesMethodsDbs;
        this.userMethodsDbs = userMethodsDbs;
    }

    @Override
    public void save(MessageDTO message) {
        User user = userMethodsDbs.findByHash(message.hash());

        Activities activities = Activities.builder()
                .user(user.getLogin())
                .remittent(message.email())
                .datetime(LocalDateTime.now())
                .build();

        activitiesMethodsDbs.save(activities);
    }

    @Override
    public List<ActivitiesResponseDTO> findAllById(String id) {
       User user = userMethodsDbs.findById(id);
      return this.activitiesMethodsDbs.findByUser(id, user);
    }
}
