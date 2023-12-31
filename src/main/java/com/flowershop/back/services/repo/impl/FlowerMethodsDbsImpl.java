package com.flowershop.back.services.repo.impl;

import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.domain.flower.ResponseFlowerGet;
import com.flowershop.back.exceptions.FlowerAlreadyExistsException;
import com.flowershop.back.exceptions.FlowerNotFoundException;
import com.flowershop.back.redis.entity.Flower;
import com.flowershop.back.redis.repository.FlowerRedisRepository;
import com.flowershop.back.repositories.FlowerRepository;
import com.flowershop.back.services.repo.FlowerMethodsDbs;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FlowerMethodsDbsImpl implements FlowerMethodsDbs {

    private final FlowerRepository flowerRepository;
    private final FlowerRedisRepository flowerRedisRepository;

    public FlowerMethodsDbsImpl(FlowerRepository flowerRepository, FlowerRedisRepository flowerRedisRepository) {
        this.flowerRepository = flowerRepository;
        this.flowerRedisRepository = flowerRedisRepository;
    }

    @Override
    public void save(Flowers flower) {
        flowerRepository.save(flower);
    }

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

        else {
            throw new FlowerNotFoundException("Flor não existe");
        }

    }
    
    @Override
    public Flowers findById(String id) {
        return flowerRepository.findById(id).orElseThrow(() -> new FlowerNotFoundException("Flor com este id não encontrada"));
    }

    @Override
    public Flowers findByIdAndDeleteRedis(String id) {
        return flowerRepository.findById(id)
                .map(flower -> {
                    flowerRedisRepository.deleteById(flower.getFilename());
                    return flower;
                })
                .orElseThrow(() -> new FlowerNotFoundException("Flor com este id não encontrada"));
    }


    @Override
    public void deleteById(String id) {
        flowerRepository.deleteById(id);
    }

    @Override
    public void updateFlower(String id, String filename, String bytes) {
        Flowers flower = findByIdAndDeleteRedis(id);

        if (flower.getFilename().equals(filename) && !flower.getFile().equals(bytes)) {
            flowerExistsByFile(bytes);
            flower.setFile(bytes);
            save(flower);
        }

        if (!flower.getFilename().equals(filename) && flower.getFile().equals(bytes)) {
            flowerExistsByFileName(filename);
            flower.setFilename(filename);
        }

        if (!flower.getFilename().equals(filename) && !flower.getFile().equals(bytes)) {
            flowerExistsByFile(bytes);
            flowerExistsByFileName(filename);
            flower.setFilename(filename);
            flower.setFile(bytes);
            save(flower);
        }
    }

    @Override
    public void flowerExistsByFile(String bytes) {
        flowerRepository.findByFile(bytes).ifPresent((s) -> {
            throw new FlowerAlreadyExistsException("Flor com a imagem já existente!");
        });
    }
    
    @Override
    public void flowerExistsByFileName(String filename) {
        flowerRepository.findByFilename(filename).ifPresent((s) -> {
            throw new FlowerAlreadyExistsException("Flor com o nome já existente!");
        });
    }
}
