package org.project.expressart.RedSocialArtista.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.RedSocialArtista.application.dto.RedSocialArtistaCreateDTO;
import org.project.expressart.RedSocialArtista.application.dto.RedSocialArtistaResponseDTO;
import org.project.expressart.RedSocialArtista.application.dto.RedSocialArtistaUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artist-social-media")
@RequiredArgsConstructor
public class RedSocialArtistaController {

    private final RedSocialArtistaService redSocialArtistaService;

    @GetMapping("/artist/{perfilArtistaId}")
    public ResponseEntity<List<RedSocialArtistaResponseDTO>> getByArtist(
            @PathVariable Long perfilArtistaId) {
        return ResponseEntity.ok(redSocialArtistaService.getByArtist(perfilArtistaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RedSocialArtistaResponseDTO> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(redSocialArtistaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<RedSocialArtistaResponseDTO> create(
            @RequestBody RedSocialArtistaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(redSocialArtistaService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RedSocialArtistaResponseDTO> update(
            @PathVariable Long id,
            @RequestBody RedSocialArtistaUpdateDTO dto) {
        return ResponseEntity.ok(redSocialArtistaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        redSocialArtistaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/artist/{perfilArtistaId}")
    public ResponseEntity<Void> deleteByArtist(@PathVariable Long perfilArtistaId) {
        redSocialArtistaService.deleteByArtist(perfilArtistaId);
        return ResponseEntity.noContent().build();
    }
}