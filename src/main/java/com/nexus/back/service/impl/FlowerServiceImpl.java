package com.nexus.back.service.impl;

import com.nexus.back.domain.dto.flower.FlowerGetDatabase;
import com.nexus.back.domain.dto.flower.ResponseFlowerGet;
import com.nexus.back.domain.entity.Flowers;
import com.nexus.back.repository.operations.FlowerDatabaseOperations;
import com.nexus.back.service.FlowerService;
import com.nexus.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.nexus.utils.Utils.replaceUrlEncodedSpaces;

@Service
@AllArgsConstructor
public class FlowerServiceImpl implements FlowerService {

    private final FlowerDatabaseOperations flowerDatabaseOperations;

    private final Utils utils;

    @SneakyThrows
    @Override
    @Transactional
    public void updateFlower(String filename, String id, MultipartFile file) {
        String bytes = utils.toBase64(file.getBytes());
        flowerDatabaseOperations.updateFlower(id, replaceUrlEncodedSpaces(filename), bytes);
    }

    @SneakyThrows
    @Override
    public void save(String filename, MultipartFile file) {
        String newFilename = replaceUrlEncodedSpaces(filename);
        String bytes = utils.toBase64(file.getBytes());

        flowerDatabaseOperations.flowerExistsByFile(bytes);
        flowerDatabaseOperations.flowerExistsByFileName(newFilename);
        flowerDatabaseOperations.save(new Flowers(new FlowerGetDatabase(newFilename, bytes)));
    }

    @Override
    public ResponseFlowerGet findByName(String filename) {
    return flowerDatabaseOperations.findByFilename(replaceUrlEncodedSpaces(filename));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Flowers flower = flowerDatabaseOperations.findByIdAndDeleteRedis(id);
        flowerDatabaseOperations.deleteById(flower.getId());
    }

}
