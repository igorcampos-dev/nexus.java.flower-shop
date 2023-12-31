package com.flowershop.back.controllers;

import com.flowershop.back.domain.ReturnResponseBody;
import com.flowershop.back.domain.flower.ResponseFlowerGet;
import com.flowershop.back.services.FlowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/flower-shop")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Crud de flores", description = "(Controller que fará ações de CRUD sobre flores no banco de dados)")
public class FlowerController {

    private final FlowerService flowerService;

    public FlowerController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @Operation(summary = "Salvar flores")
    @SneakyThrows
    @PostMapping(value = "/register-flower/{filename}", consumes = "multipart/form-data")
    public ResponseEntity<ReturnResponseBody> postProduct(
            @Schema(example = "Tulipa", description = "nome da flor") @Valid @PathVariable String filename,
            @RequestParam("file") MultipartFile file
    ) {
        this.flowerService.save(filename, file);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Flor cadastrada!"));
    }

    @Operation(summary = "Atualizar informações de uma flor")
    @PutMapping(value = "/update-flower/{id}/{filename}", consumes = "multipart/form-data")
    public ResponseEntity<ReturnResponseBody> updateFlower(
            @Schema(example = "1320ec46-8dc4-4874-a191-8f195703376c", description = "id da flor") @Valid @PathVariable String id,
            @RequestParam("file") MultipartFile file,
            @Schema(example = "Tuplica Azul", description = "novo nome da flor") @Valid @PathVariable String filename
    ) {
        this.flowerService.updateFlower(filename, id, file);
        return ResponseEntity.ok().body(new ReturnResponseBody("Flor modificada!"));
    }

    @Operation(summary = "Pegar informação de uma flor com o nome")
    @GetMapping("/see-flowers/{filename}")
    public ResponseEntity<ResponseFlowerGet> getFlower(
            @Schema(example = "Tulipa", description = "nome da flor") @Valid @PathVariable String filename
    ) {
        ResponseFlowerGet flower = this.flowerService.findByName(filename);
        return ResponseEntity.status(HttpStatus.OK).body(flower);
    }

    @Operation(summary = "Deletar flor com o id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ReturnResponseBody> deleteFlower(
            @Schema(example = "1320ec46-8dc4-4874-a191-8f195703376c", description = "id da flor") @Valid @PathVariable String id
    ) {
        this.flowerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ReturnResponseBody("Flor deletada com sucesso!"));
    }
}
