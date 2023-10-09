package com.flowershop.back.controllers;

import com.flowershop.back.configuration.ResponseMessage;
import com.flowershop.back.domain.flower.FlowerDTO;
import com.flowershop.back.domain.flower.FlowerUpdateDTO;
import com.flowershop.back.domain.flower.Flowers;
import com.flowershop.back.domain.flower.FlowerResponseDTO;
import com.flowershop.back.repositories.FlowerRepository;
import com.flowershop.back.services.FlowerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flower-shop")
public class FlowerController {
    @Autowired
    FlowerRepository repository;

    @Autowired
    FlowerService service;


    @PostMapping("/register-flower")
    public ResponseEntity<ResponseMessage> postProduct(@RequestBody @Valid FlowerDTO body){
        if (repository.findByName(body.name()) != null) return ResponseEntity.badRequest().body(new ResponseMessage("Flor já existe,não pode ser cadastrada!"));
        Flowers newProduct = new Flowers(body);
        this.repository.save(newProduct);
        return ResponseEntity.ok().body(new ResponseMessage("Flor cadastrada!"));
    }

    @PutMapping("/update-flower")
    public ResponseEntity<ResponseMessage>  updateFlower(@RequestBody @Valid FlowerUpdateDTO flower){
        if (repository.findByName(flower.name()) == null) return ResponseEntity.badRequest().body(new ResponseMessage("Flor não existe ou credenciais incorretas!"));
        if(repository.findByName(flower.newName()) != null) return ResponseEntity.badRequest().body(new ResponseMessage("Já existe uma flor com este nome,escolha outro nome!"));
        service.updateFlower(flower);
        return ResponseEntity.ok().body(new ResponseMessage("flor modificada!"));
    }

    @GetMapping("/see-flowers")
    public ResponseEntity<List<FlowerResponseDTO>> getAllProducts(){
        List<FlowerResponseDTO> productList = this.repository.findAll().stream().map(FlowerResponseDTO::new).toList();
        return ResponseEntity.ok(productList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage> deleteFlower(@RequestParam("name") String name){
        Flowers flowers = repository.findByName(name);
       if (flowers != null){
           repository.deleteById(flowers.getId());
           return ResponseEntity.ok().body(new ResponseMessage("Produto deletado com sucesso!"));
       }
        return ResponseEntity.badRequest().body(new ResponseMessage("Produto não deletado,nome invalido!!"));
    }
}
