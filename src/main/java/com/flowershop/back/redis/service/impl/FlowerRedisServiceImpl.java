package com.flowershop.back.redis.service.impl;

import com.flowershop.back.redis.entity.Flower;
import com.flowershop.back.redis.repository.FlowerRedisRepository;
import com.flowershop.back.redis.service.FlowerRedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class FlowerRedisServiceImpl implements FlowerRedisService {
    
    private final FlowerRedisRepository  flowerRedisRepository;

    @Override
    public void save(Flower flower){
        flowerRedisRepository.save(flower);
    }

    @Override
    public List<Flower> findAll() {
        return (List<Flower>) flowerRedisRepository.findAll();
    }


}
