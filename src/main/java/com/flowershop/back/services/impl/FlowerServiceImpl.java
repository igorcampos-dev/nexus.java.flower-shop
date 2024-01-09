package com.flowershop.back.services.impl;

import com.flowershop.back.domain.flower.FlowerGetDatabase;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.domain.flower.ResponseFlowerGet;
import com.flowershop.back.services.FlowerService;
import com.flowershop.back.repositories.operations.FlowerDatabaseOperations;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
import static com.flowershop.back.configuration.UtilsProject.replaceFilename;

@Service
@AllArgsConstructor
public class FlowerServiceImpl implements FlowerService {

    private final FlowerDatabaseOperations flowerDatabaseOperations;

    @SneakyThrows
    @Override
    @Transactional
    public void updateFlower(String filename, String id, MultipartFile file) {
        String newFilename = replaceFilename(filename);
        String bytes = Base64.getEncoder().encodeToString(file.getBytes());
        flowerDatabaseOperations.updateFlower(id, newFilename, bytes);
    }

    @SneakyThrows
    @Override
    public void save(String filename, MultipartFile file) {
        String newFilename = replaceFilename(filename);
        String bytes = Base64.getEncoder().encodeToString(file.getBytes());

        flowerDatabaseOperations.flowerExistsByFile(bytes);
        flowerDatabaseOperations.flowerExistsByFileName(newFilename);
        flowerDatabaseOperations.save(new Flowers(new FlowerGetDatabase(newFilename, bytes)));
    }

    @Override
    public ResponseFlowerGet findByName(String filename) {
    String newFilename = replaceFilename(filename);
    return flowerDatabaseOperations.findByFilename(newFilename);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Flowers flower = flowerDatabaseOperations.findByIdAndDeleteRedis(id);
        flowerDatabaseOperations.deleteById(flower.getId());
    }

}
