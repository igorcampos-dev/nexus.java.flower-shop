package com.flowershop.back.services.impl;

import com.flowershop.back.domain.flower.FlowerDTO;
import com.flowershop.back.domain.flower.FlowerUpdateDTO;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.exceptions.FlowerAlreadyExistsException;
import com.flowershop.back.exceptions.FlowerNotFoundException;
import com.flowershop.back.services.FlowerService;
import com.flowershop.back.repositories.FlowerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FlowerServiceImpl implements FlowerService {
    @Autowired
    FlowerRepository repository;

    @Override
    public void updateFlower(FlowerUpdateDTO flower) {

        repository.findByName(flower.name())
                .orElseThrow(() -> new FlowerNotFoundException("Flor não existe na base de dados"));

        repository.findByName(flower.newName()).ifPresent(existingNewFlower -> {
            throw new FlowerAlreadyExistsException("Nome da flor já existe na base de dados");
        });

        Flowers newFlower =  Flowers.builder()
                .image(flower.image())
                .name(flower.newName())
                .build();

        repository.save(newFlower);
    }

    @Override
    public void save(Flowers flowers) {
        repository.findByImage(flowers.getName())
                .ifPresent(flowerExists -> {
                    throw new FlowerAlreadyExistsException("Flor já existe, escolha outras informações!");
                });

        repository.save(flowers);
    }

    @Override
    public List<FlowerDTO> findAll() {
        return this.repository.findAll().stream().map(FlowerDTO::new).toList();
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        Flowers flower = repository.findByName(name)
                .orElseThrow(() -> new FlowerNotFoundException("Flor não encontrada!"));

        repository.deleteById(flower.getId());
    }
}
