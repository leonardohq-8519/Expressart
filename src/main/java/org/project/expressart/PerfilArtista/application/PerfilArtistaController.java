package org.project.expressart.PerfilArtista.application;

import org.project.expressart.PerfilArtista.application.PerfilArtistaService;
import org.project.expressart.PerfilArtista.dto.PerfilArtistaRequestDTO;
import org.project.expressart.PerfilArtista.dto.PerfilArtistaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist-profiles")
public class PerfilArtistaController {

    private final PerfilArtistaService perfilArtistaService;

    public PerfilArtistaController(PerfilArtistaService perfilArtistaService) {
        this.perfilArtistaService = perfilArtistaService;
    }

    @GetMapping
    public ResponseEntity<List<PerfilArtistaResponseDTO>> getAll() {
        return ResponseEntity.ok(perfilArtistaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilArtistaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(perfilArtistaService.findById(id));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<PerfilArtistaResponseDTO> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(perfilArtistaService.findByUsuarioId(usuarioId));
    }

    @PostMapping("/user/{usuarioId}")
    public ResponseEntity<PerfilArtistaResponseDTO> create(
            @PathVariable Long usuarioId,
            @RequestBody PerfilArtistaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilArtistaService.create(usuarioId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilArtistaResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PerfilArtistaRequestDTO request) {
        return ResponseEntity.ok(perfilArtistaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        perfilArtistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}