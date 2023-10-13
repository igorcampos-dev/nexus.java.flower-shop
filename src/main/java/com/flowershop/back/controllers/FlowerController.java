package com.flowershop.back.controllers;

import com.flowershop.back.configuration.ResponseMessage;
import com.flowershop.back.domain.flower.FlowerDTO;
import com.flowershop.back.domain.flower.FlowerResponseDTO;
import com.flowershop.back.domain.flower.FlowerUpdateDTO;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.services.FlowerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/flower-shop")
public class FlowerController {
    @Autowired
    FlowerService flowerService;


    @PostMapping("/register-flower")
    public ResponseEntity<ResponseMessage> postProduct(@RequestBody @Valid FlowerDTO flower){
        Flowers flowers = new Flowers(flower);

        this.flowerService.save(flowers);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Flor cadastrada!"));
    }

    @PutMapping("/update-flower")
    public ResponseEntity<ResponseMessage>  updateFlower(@RequestBody @Valid FlowerUpdateDTO flower){

        this.flowerService.updateFlower(flower);

       return ResponseEntity.ok().body(new ResponseMessage("flor modificada!"));
    }

    @GetMapping("/see-flowers")
    public ResponseEntity<List<FlowerResponseDTO>> getAllProducts(){

        List<FlowerResponseDTO> productList = this.flowerService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteFlower(@RequestParam("name") String name){

           this.flowerService.deleteByName(name);

           return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Flor deletada com sucesso!"));
       }

}
