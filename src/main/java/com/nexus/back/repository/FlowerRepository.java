package com.nexus.back.repository;

import com.nexus.back.domain.entity.Flowers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FlowerRepository extends JpaRepository<Flowers, String> {
   Optional<Flowers> findByFilename(String fileName);

   Optional<Flowers> findByFile(String file);

}
