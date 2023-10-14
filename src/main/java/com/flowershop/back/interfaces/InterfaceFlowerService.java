package com.flowershop.back.interfaces;

import com.flowershop.back.domain.flower.FlowerResponseDTO;
import com.flowershop.back.domain.flower.FlowerUpdateDTO;
import com.flowershop.back.domain.flower.Flowers;
import java.util.List;

public interface InterfaceFlowerService {
    void updateFlower( FlowerUpdateDTO flower);
    void save(Flowers flowers);
    List<FlowerResponseDTO> findAll();
    void deleteByName(String name);
}
