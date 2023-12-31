package com.flowershop.back.redis.service;

import com.flowershop.back.redis.entity.Flower;
import java.util.List;

public interface FlowerRedisService {

    void save(Flower flower);
    List<Flower> findAll();
}
