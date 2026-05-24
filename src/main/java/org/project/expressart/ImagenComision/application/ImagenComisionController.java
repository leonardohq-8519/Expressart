package org.project.expressart.ImagenComision.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.ImagenComision.domain.CommissionPictureService;
import org.project.expressart.ImagenComision.dto.ImagenComisionCreateDTO;
import org.project.expressart.ImagenComision.dto.ImagenComisionResponseDTO;
import org.project.expressart.ImagenComision.dto.ImagenComisionUpdateDTO;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commission-images")
@RequiredArgsConstructor
public class ImagenComisionController {

    private final CommissionPictureService commissionPictureService;

    @GetMapping("/commission/{comisionId}")
    public ResponseEntity<List<ImagenComisionResponseDTO>> getByCommission(
            @PathVariable Long comisionId) throws ResourceNotFoundException{
        return ResponseEntity.ok(commissionPictureService.getByCommission(comisionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenComisionResponseDTO> getById(
            @PathVariable Long id) throws ResourceNotFoundException{
        return ResponseEntity.ok(commissionPictureService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ImagenComisionResponseDTO> create(
            @RequestBody ImagenComisionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commissionPictureService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ImagenComisionResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ImagenComisionUpdateDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(commissionPictureService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commissionPictureService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/commission/{comisionId}")
    public ResponseEntity<Void> deleteByCommission(@PathVariable Long comisionId) {
        commissionPictureService.deleteByCommission(comisionId);
        return ResponseEntity.noContent().build();
    }
}