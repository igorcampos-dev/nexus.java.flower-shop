package com.nexus.back.redis.repository;

import com.nexus.back.redis.entity.Flower;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface FlowerRedisRepository extends CrudRepository<Flower, String> {
    Optional<Flower> findByFilename(String filename);
}
