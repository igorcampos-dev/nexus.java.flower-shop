package com.flowershop.back.controllers;

import com.flowershop.back.redis.repository.FlowerRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    FlowerRedisRepository  flowerRedisRepository;

    @GetMapping
    public String test(){
        return flowerRedisRepository.findAll().toString();
    }
}
