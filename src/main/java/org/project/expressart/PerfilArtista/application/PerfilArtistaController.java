package org.project.expressart.PerfilArtista.application;

import org.project.expressart.PerfilArtista.domain.ArtistProfileService;
import org.project.expressart.PerfilArtista.dto.ArtistProfileRequestDTO;
import org.project.expressart.PerfilArtista.dto.ArtistProfileResponseDTO;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist-profiles")
public class PerfilArtistaController {

    private final ArtistProfileService artistProfileService;

    public PerfilArtistaController(ArtistProfileService artistProfileService) {
        this.artistProfileService = artistProfileService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistProfileResponseDTO>> getAll() {
        return ResponseEntity.ok(artistProfileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistProfileResponseDTO> getById(@PathVariable Long id)throws ResourceNotFoundException {
        return ResponseEntity.ok(artistProfileService.findById(id));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<ArtistProfileResponseDTO> getByUsuario(@PathVariable Long usuarioId) throws ResourceNotFoundException {
        return ResponseEntity.ok(artistProfileService.findByUsuarioId(usuarioId));
    }

    @PostMapping("/user/{usuarioId}")
    public ResponseEntity<ArtistProfileResponseDTO> create(
            @PathVariable Long usuarioId,
            @RequestBody ArtistProfileRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artistProfileService.create(usuarioId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistProfileResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ArtistProfileRequestDTO request) throws ResourceNotFoundException{
        return ResponseEntity.ok(artistProfileService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}