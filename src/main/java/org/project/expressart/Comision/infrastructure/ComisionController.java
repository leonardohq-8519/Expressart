package org.project.expressart.Comision.infrastructure;

import org.project.expressart.Comision.application.ComisionService;
import org.project.expressart.Comision.application.dto.ComisionRequestDTO;
import org.project.expressart.Comision.application.dto.ComisionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commissions")
public class ComisionController {

    private final ComisionService comisionService;

    public ComisionController(ComisionService comisionService) {
        this.comisionService = comisionService;
    }

    @GetMapping
    public ResponseEntity<List<ComisionResponseDTO>> getAll() {
        return ResponseEntity.ok(comisionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComisionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(comisionService.findById(id));
    }

    @GetMapping("/artist-profile/{perfilArtistaId}")
    public ResponseEntity<List<ComisionResponseDTO>> getByPerfilArtista(@PathVariable Long perfilArtistaId) {
        return ResponseEntity.ok(comisionService.findByPerfilArtistaId(perfilArtistaId));
    }

    @GetMapping("/artist-profile/{perfilArtistaId}/active")
    public ResponseEntity<List<ComisionResponseDTO>> getActiveByPerfilArtista(@PathVariable Long perfilArtistaId) {
        return ResponseEntity.ok(comisionService.findByPerfilArtistaIdAndEstaActiva(perfilArtistaId, true));
    }

    @GetMapping("/category/{categoriaId}")
    public ResponseEntity<List<ComisionResponseDTO>> getByCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(comisionService.findByCategoriaId(categoriaId));
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<List<ComisionResponseDTO>> getByTag(@PathVariable Long tagId) {
        return ResponseEntity.ok(comisionService.findByTagsId(tagId));
    }

    @PostMapping
    public ResponseEntity<ComisionResponseDTO> create(@RequestBody ComisionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comisionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComisionResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ComisionRequestDTO request) {
        return ResponseEntity.ok(comisionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comisionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}