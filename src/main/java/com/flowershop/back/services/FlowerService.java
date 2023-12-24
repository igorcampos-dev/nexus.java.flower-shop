package com.flowershop.back.services;

import com.flowershop.back.domain.flower.FlowerGetDatabase;
import org.springframework.web.multipart.MultipartFile;


public interface FlowerService {
    void updateFlower( String fileName, String id, MultipartFile file);
    void save(String fileName, MultipartFile file);
    FlowerGetDatabase findByName(String filename);
    void deleteById(String id);

}
