package com.flowershop.back.services.repo;

import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.domain.flower.ResponseFlowerGet;

public interface FlowerMethodsDbs {

    ResponseFlowerGet findByFilename(String filename);
    void save(Flowers flower);
    void updateFlower(String id, String filename, String bytes);
    void deleteById(String id);
    void flowerExistsByFile(String bytes);
    void flowerExistsByFileName(String filename);
    Flowers findById(String id);
    Flowers findByIdAndDeleteRedis(String id);
}
