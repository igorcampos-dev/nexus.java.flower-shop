package com.flowershop.back.redis.repository;

import com.flowershop.back.redis.entity.Flower;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface FlowerRedisRepository extends CrudRepository<Flower, String> {
    Optional<Flower> findByFilename(String filename);
}
