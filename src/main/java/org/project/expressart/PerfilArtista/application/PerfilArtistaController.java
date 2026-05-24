package org.project.expressart.PerfilArtista.application;

import org.project.expressart.PerfilArtista.domain.ArtistProfileService;
import org.project.expressart.PerfilArtista.dto.ArtistProfileRequestDTO;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist-profiles")
public class PerfilArtistaController {

    private final ArtistProfileService perfilArtistaService;

    public PerfilArtistaController(ArtistProfileService perfilArtistaService) {
        this.perfilArtistaService = perfilArtistaService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistProfileResponseDTO>> getAll() {
        return ResponseEntity.ok(perfilArtistaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistProfileResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(perfilArtistaService.findById(id));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<ArtistProfileResponseDTO> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(perfilArtistaService.findByUsuarioId(usuarioId));
    }

    @PostMapping("/user/{usuarioId}")
    public ResponseEntity<ArtistProfileResponseDTO> create(
            @PathVariable Long usuarioId,
            @RequestBody ArtistProfileRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilArtistaService.create(usuarioId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistProfileResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ArtistProfileRequestDTO request) {
        return ResponseEntity.ok(perfilArtistaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        perfilArtistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}