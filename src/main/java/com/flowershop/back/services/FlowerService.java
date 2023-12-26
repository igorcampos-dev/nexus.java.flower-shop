package com.flowershop.back.services;

import com.flowershop.back.domain.flower.ResponseFlowerGet;
import org.springframework.web.multipart.MultipartFile;


public interface FlowerService {
    void updateFlower( String fileName, String id, MultipartFile file);
    void save(String filename, MultipartFile file);
    ResponseFlowerGet findByName(String filename);
    void deleteById(String id);

}
