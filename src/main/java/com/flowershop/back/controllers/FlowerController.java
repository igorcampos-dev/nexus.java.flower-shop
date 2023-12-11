package com.flowershop.back.controllers;

import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.flower.FlowerGetDatabase;
import com.flowershop.back.services.FlowerService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/flower-shop")
public class FlowerController {
    @Autowired
    FlowerService flowerService;

    @SneakyThrows
    @PostMapping("/register-flower/{fileName}")
    public ResponseEntity<ReturnResponseBody> postProduct(@Valid @PathVariable String fileName, @RequestParam("file") MultipartFile file){
        this.flowerService.save(fileName, file);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Flor cadastrada!"));
    }

    @PutMapping("/update-flower/{id}/{fileName}")
    public ResponseEntity<ReturnResponseBody> updateFlower(@Valid @PathVariable String id, @RequestParam("file") MultipartFile file, @Valid @PathVariable String fileName) {
        this.flowerService.updateFlower(fileName, id, file);
        return ResponseEntity.ok().body(new ReturnResponseBody("Flor modificada!"));
    }

    @GetMapping("/see-flowers/{fileName}")
    public ResponseEntity<List<FlowerGetDatabase>> getFlower(@Valid @PathVariable String fileName){
        List<FlowerGetDatabase> productList = this.flowerService.findByName(fileName);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ReturnResponseBody> deleteFlower(@Valid @PathVariable String id){
        this.flowerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Flor deletada com sucesso!"));
    }

}
