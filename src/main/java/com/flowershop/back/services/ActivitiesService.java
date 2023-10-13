package com.flowershop.back.services;

import com.flowershop.back.domain.activities.Activities;
import com.flowershop.back.domain.activities.ActivitiesResponseDTO;
import com.flowershop.back.domain.flower.MessageDTO;
import com.flowershop.back.domain.user.User;
import com.flowershop.back.exceptions.UserNotFoundException;
import com.flowershop.back.repositories.ActivitiesRepository;
import com.flowershop.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivitiesService {

    @Autowired
    ActivitiesRepository activitiesRepository;

    @Autowired
    UserRepository userRepository;



    public void save(MessageDTO message) {
        User user = userRepository.findByHash(message.hash())
                .orElseThrow(() -> new UserNotFoundException("Usuário não foi encontrado ao salvar a sua atividade"));

        Activities activities = Activities.builder()
                .user(user.getLogin())
                .remittent(message.email())
                .localDateTime(LocalDateTime.now())
                .build();
        activitiesRepository.save(activities);
    }



    public List<ActivitiesResponseDTO> findAll() {
      return  this.activitiesRepository.findAll().stream().map(ActivitiesResponseDTO::new).toList();
    }
}
