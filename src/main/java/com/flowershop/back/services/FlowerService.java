package com.flowershop.back.services;

import com.flowershop.back.domain.flower.FlowerUpdateDTO;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.repositories.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowerService {

    @Autowired
    FlowerRepository repository;


    public Flowers updateFlower(FlowerUpdateDTO flower) {

        if (flowerIsValid(flower)) {
        Flowers flowers = repository.findByName(flower.name());
        flowers.setImage(flower.image());
        flowers.setName(flower.newName());

          return repository.save(flowers);
        }
        return null ;
    }

    private boolean flowerIsValid(FlowerUpdateDTO flower) {
        if (flower.name() != null && flower.image() != null){
            return true;
        }
        return false;
    }

}
