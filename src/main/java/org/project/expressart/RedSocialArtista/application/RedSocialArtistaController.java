package org.project.expressart.RedSocialArtista.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.RedSocialArtista.application.dto.RedSocialArtistaCreateDTO;
import org.project.expressart.RedSocialArtista.application.dto.RedSocialArtistaResponseDTO;
import org.project.expressart.RedSocialArtista.application.dto.RedSocialArtistaUpdateDTO;
import org.project.expressart.RedSocialArtista.domain.ArtistSocialMediaService;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artist-social-media")
@RequiredArgsConstructor
public class RedSocialArtistaController {

    private final ArtistSocialMediaService artistSocialMediaService;

    @GetMapping("/artist/{perfilArtistaId}")
    public ResponseEntity<List<RedSocialArtistaResponseDTO>> getByArtist(
            @PathVariable Long perfilArtistaId) throws ResourceNotFoundException {
        return ResponseEntity.ok(artistSocialMediaService.getByArtist(perfilArtistaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RedSocialArtistaResponseDTO> getById(
            @PathVariable Long id)throws ResourceNotFoundException {
        return ResponseEntity.ok(artistSocialMediaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<RedSocialArtistaResponseDTO> create(
            @RequestBody RedSocialArtistaCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(artistSocialMediaService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RedSocialArtistaResponseDTO> update(
            @PathVariable Long id,
            @RequestBody RedSocialArtistaUpdateDTO dto) throws ResourceNotFoundException{
        return ResponseEntity.ok(artistSocialMediaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistSocialMediaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/artist/{perfilArtistaId}")
    public ResponseEntity<Void> deleteByArtist(@PathVariable Long perfilArtistaId) {
        artistSocialMediaService.deleteByArtist(perfilArtistaId);
        return ResponseEntity.noContent().build();
    }
}