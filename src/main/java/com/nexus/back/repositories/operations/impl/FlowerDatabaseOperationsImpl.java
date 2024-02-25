package com.nexus.back.repositories.operations.impl;

import com.nexus.back.domain.dto.flower.ResponseFlowerGet;
import com.nexus.back.domain.entity.Flowers;
import com.nexus.back.exceptions.FlowerAlreadyExistsException;
import com.nexus.back.exceptions.FlowerNotFoundException;
import com.nexus.back.redis.entity.Flower;
import com.nexus.back.redis.repository.FlowerRedisRepository;
import com.nexus.back.repositories.FlowerRepository;
import com.nexus.back.repositories.operations.FlowerDatabaseOperations;
import com.nexus.utils.Conditional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class FlowerDatabaseOperationsImpl implements FlowerDatabaseOperations {

    private final FlowerRepository flowerRepository;
    private final FlowerRedisRepository flowerRedisRepository;
    private final Conditional conditional;
    private static final FlowerNotFoundException FLOWER_NOT_FOUND_EXCEPTION = new FlowerNotFoundException("Flor não encontrada");
    private static final FlowerAlreadyExistsException FLOWER_ALREADY_EXISTS_EXCEPTION = new FlowerAlreadyExistsException("Flor com a imagem já existente!");

    @Override
    public ResponseFlowerGet findByFilename(String filename) {
        Optional<Flower> flower = flowerRedisRepository.findByFilename(filename);

        if (flower.isPresent()){
            return new ResponseFlowerGet(flower.get().getId(), flower.get().getFilename(), flower.get().getFile());
        }

        Optional<Flowers> flowerDb = flowerRepository.findByFilename(filename);

        if (flowerDb.isPresent()){
            flowerRedisRepository.save(new Flower(flowerDb.get().getId(), flowerDb.get().getFilename(), flowerDb.get().getFile()));
            return new ResponseFlowerGet(flowerDb.get().getId(), flowerDb.get().getFilename(), flowerDb.get().getFile());
        }

        else { throw FLOWER_NOT_FOUND_EXCEPTION;}
    }

    @Override
    public void updateFlower(String id, String filename, String bytes) {
        Flowers flower = findByIdAndDeleteRedis(id);


        conditional
                .when(flower.getFilename().equals(filename) && !flower.getFile().equals(bytes),
                        () -> {
                            flowerExistsByFile(bytes);
                            flower.setFile(bytes);
                            save(flower);
                        })
                .otherwise(flower.getFilename().equals(filename) && !flower.getFile().equals(bytes),
                        () -> {
                            flowerExistsByFile(bytes);
                            flower.setFile(bytes);
                            save(flower);
                        })

                .otherwise(!flower.getFilename().equals(filename) && flower.getFile().equals(bytes),
                        () -> {
                            flowerExistsByFileName(filename);
                            flower.setFilename(filename);
                        })

                .otherwise(!flower.getFilename().equals(filename) && !flower.getFile().equals(bytes),
                        () -> {
                            flowerExistsByFile(bytes);
                            flowerExistsByFileName(filename);
                            flower.setFilename(filename);
                            flower.setFile(bytes);
                            save(flower);
                        })
                .execute();

    }

    @Override
    public void flowerExistsByFile(String bytes) {
        flowerRepository.findByFile(bytes).ifPresent((s) -> {
            throw FLOWER_ALREADY_EXISTS_EXCEPTION;
        });
    }
    
    @Override
    public void flowerExistsByFileName(String filename) {
        flowerRepository.findByFilename(filename).ifPresent((s) -> {
            throw FLOWER_ALREADY_EXISTS_EXCEPTION;
        });
    }

    @Override
    public Flowers findById(String id) {
        return flowerRepository.findById(id).orElseThrow(() -> FLOWER_NOT_FOUND_EXCEPTION);
    }

    @Override
    public void deleteById(String id) {
        flowerRepository.deleteById(id);
    }

    @Override
    public Flowers findByIdAndDeleteRedis(String id) {
        return flowerRepository.findById(id)
                .map(flower -> {
                    flowerRedisRepository.deleteById(flower.getFilename());
                    return flower;
                })
                .orElseThrow(() -> FLOWER_NOT_FOUND_EXCEPTION);
    }

    @Override
    public void save(Flowers flower) {
        flowerRepository.save(flower);
    }
}
