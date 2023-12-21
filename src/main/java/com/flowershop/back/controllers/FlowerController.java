package com.flowershop.back.controllers;

import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.flower.FlowerGetDatabase;
import com.flowershop.back.services.FlowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Crud de flores", description = "(Controller que fará ações de CRUD sobre flores no banco de dados)")
public class FlowerController {
    @Autowired
    FlowerService flowerService;



    @Operation(summary = "Salvar flores")
    @SneakyThrows
    @PostMapping("/register-flower/{fileName}")
    public ResponseEntity<ReturnResponseBody> postProduct(@Schema(example = "Tulipa", description = "nome da flor") @Valid @PathVariable String fileName,
                                                          @RequestParam("file") MultipartFile file){
        this.flowerService.save(fileName, file);

        return ResponseEntity.status(HttpStatus.OK).body( new ReturnResponseBody("Flor cadastrada!"));
    }

    @Operation(summary = "Atualizar informações de uma flor")
    @PutMapping("/update-flower/{id}/{fileName}")
    public ResponseEntity<ReturnResponseBody> updateFlower(@Schema(example = "1320ec46-8dc4-4874-a191-8f195703376c", description = "id da flor") @Valid @PathVariable String id,
                                                           @RequestParam("file") MultipartFile file,
                                                           @Schema(example = "Tuplica%20Azul", description = "nome da flor")@Valid @PathVariable String fileName) {
        this.flowerService.updateFlower(fileName, id, file);
        return ResponseEntity.ok().body(new ReturnResponseBody("Flor modificada!"));
    }

    @Operation(summary = "Pegar informação de uma flor com o nome")
    @GetMapping("/see-flowers/{fileName}")
    public ResponseEntity<List<FlowerGetDatabase>> getFlower(@Schema(example = "Tulipa", description = "nome da flor")@Valid @PathVariable String fileName){
        List<FlowerGetDatabase> productList = this.flowerService.findByName(fileName);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @Operation(summary = "Deletar flor com o id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ReturnResponseBody> deleteFlower(@Schema(example = "1320ec46-8dc4-4874-a191-8f195703376c", description = "id da flor")@Valid @PathVariable String id){
        this.flowerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Flor deletada com sucesso!"));
    }

}
