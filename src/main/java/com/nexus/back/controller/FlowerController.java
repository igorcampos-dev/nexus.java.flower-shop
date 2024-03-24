package com.nexus.back.controller;

import com.nexus.back.domain.Response;
import com.nexus.back.domain.dto.flower.ResponseFlowerGet;
import com.nexus.back.service.FlowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/shop")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Crud de flores", description = "(Controller que fará ações de CRUD sobre flores no banco de dados)")
public class FlowerController {

    private final FlowerService flowerService;

    @Operation(summary = "Salvar flores")
    @SneakyThrows
    @PostMapping(value = "/register-flower/{filename}", consumes = "multipart/form-data")
    public ResponseEntity<Response> postProduct(
            @Schema(example = "Tulipa", description = "nome da flor") @Valid @PathVariable String filename,
            @RequestParam("file") MultipartFile file
    ) {
        this.flowerService.save(filename, file);

        RESPONSE.setMessage("Flor cadastrada!");
        return ResponseEntity.status(HttpStatus.OK)
                             .body(RESPONSE);
    }

    @Operation(summary = "Atualizar informações de uma flor")
    @PutMapping(value = "/update-flower/{id}/{filename}", consumes = "multipart/form-data")
    public ResponseEntity<Response> updateFlower(
            @Schema(example = "1320ec46-8dc4-4874-a191-8f195703376c", description = "id da flor") @Valid @PathVariable String id,
            @RequestParam("file") MultipartFile file,
            @Schema(example = "Tuplica Azul", description = "novo nome da flor") @Valid @PathVariable String filename
    ) {
        this.flowerService.updateFlower(filename, id, file);

        RESPONSE.setMessage("Flor modificada!");
        return ResponseEntity.ok()
                             .body(RESPONSE);
    }

    @Operation(summary = "Pegar informação de uma flor com o nome")
    @GetMapping("/see-flowers/{filename}")
    public ResponseEntity<ResponseFlowerGet> getFlower(
            @Schema(example = "Tulipa", description = "nome da flor") @Valid @PathVariable String filename
    ) {
        ResponseFlowerGet flower = this.flowerService.findByName(filename);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(flower);
    }

    @Operation(summary = "Deletar flor com o id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteFlower(
            @Schema(example = "1320ec46-8dc4-4874-a191-8f195703376c", description = "id da flor") @Valid @PathVariable String id
    ) {
        this.flowerService.deleteById(id);

        RESPONSE.setMessage("Flor deletada com sucesso!");
        return ResponseEntity.status(HttpStatus.OK)
                             .body(RESPONSE);
    }

    private static final Response RESPONSE = new Response(null);
}
