package org.project.expressart.ImagenComision.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.ImagenComision.domain.CommissionPictureService;
import org.project.expressart.ImagenComision.dto.ImagenComisionResponseDTO;
import org.project.expressart.ImagenComision.dto.ImagenComisionUpdateDTO;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @PathVariable Long commId, @PathVariable Long imageId) throws ResourceNotFoundException{
        return ResponseEntity.ok(commissionPictureService.getById(commId, imageId));
    }

    @PostMapping
    public ResponseEntity<ImagenComisionResponseDTO> create(
            @PathVariable Long commId, @RequestPart MultipartFile file) throws ResourceNotFoundException{
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commissionPictureService.create(commId, file));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ImagenComisionResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ImagenComisionUpdateDTO dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(commissionPictureService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long commissionId, @PathVariable Long imageId) throws ResourceNotFoundException{
        commissionPictureService.delete(commissionId, imageId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/commission/{comisionId}")
    public ResponseEntity<Void> deleteByCommission(@PathVariable Long comisionId) throws ResourceNotFoundException {
        commissionPictureService.deleteByCommission(comisionId);
        return ResponseEntity.noContent().build();
    }
}