package com.nexus.back.redis.service;

import com.nexus.back.redis.entity.Flower;
import java.util.List;

public interface FlowerRedisService {

    void save(Flower flower);
    List<Flower> findAll();
}
