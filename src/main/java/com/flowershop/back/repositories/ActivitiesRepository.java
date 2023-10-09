package com.flowershop.back.repositories;

import com.flowershop.back.domain.activities.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, String> {

    List<Activities> findByUser(String user);
}
