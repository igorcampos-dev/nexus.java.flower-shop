package com.flowershop.back.repositories;

import com.flowershop.back.domain.activities.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, String> {
}
