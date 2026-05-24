package org.project.expressart.ImagenComision.infrastructure;

import lombok.RequiredArgsConstructor;
import org.project.expressart.ImagenComision.application.dto.ImagenComisionCreateDTO;
import org.project.expressart.ImagenComision.application.dto.ImagenComisionResponseDTO;
import org.project.expressart.ImagenComision.application.dto.ImagenComisionUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commission-images")
@RequiredArgsConstructor
public class ImagenComisionController {

    private final ImagenComisionService imagenComisionService;

    @GetMapping("/commission/{comisionId}")
    public ResponseEntity<List<ImagenComisionResponseDTO>> getByCommission(
            @PathVariable Long comisionId) {
        return ResponseEntity.ok(imagenComisionService.getByCommission(comisionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenComisionResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(imagenComisionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ImagenComisionResponseDTO> create(
            @RequestBody ImagenComisionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(imagenComisionService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ImagenComisionResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ImagenComisionUpdateDTO dto) {
        return ResponseEntity.ok(imagenComisionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        imagenComisionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/commission/{comisionId}")
    public ResponseEntity<Void> deleteByCommission(@PathVariable Long comisionId) {
        imagenComisionService.deleteByCommission(comisionId);
        return ResponseEntity.noContent().build();
    }
}