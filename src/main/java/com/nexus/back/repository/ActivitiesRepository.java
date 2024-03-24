package com.nexus.back.repository;

import com.nexus.back.domain.entity.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, String> {
    Optional<List<Activities>> findByUser(String user);
}
