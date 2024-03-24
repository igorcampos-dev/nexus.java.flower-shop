package com.nexus.back.repository.operations;

import com.nexus.back.domain.dto.flower.ResponseFlowerGet;
import com.nexus.back.domain.entity.Flowers;

public interface FlowerDatabaseOperations {

    ResponseFlowerGet findByFilename(String filename);
    void save(Flowers flower);
    void updateFlower(String id, String filename, String bytes);
    void deleteById(String id);
    void flowerExistsByFile(String bytes);
    void flowerExistsByFileName(String filename);
    Flowers findById(String id);
    Flowers findByIdAndDeleteRedis(String id);
}
