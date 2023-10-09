package com.flowershop.back.repositories;

import com.flowershop.back.domain.flower.Flowers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FlowerRepository extends JpaRepository<Flowers, String> {
    Flowers findByName(String name);


    Flowers findByImage(String flower);
}
