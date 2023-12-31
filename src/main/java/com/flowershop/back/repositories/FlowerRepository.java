package com.flowershop.back.repositories;

import com.flowershop.back.domain.flower.Flowers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FlowerRepository extends JpaRepository<Flowers, String> {
   Optional<Flowers> findByFilename(String fileName);

   Optional<Flowers> findByFile(String file);

}
