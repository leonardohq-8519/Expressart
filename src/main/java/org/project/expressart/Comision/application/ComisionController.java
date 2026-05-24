package org.project.expressart.Comision.application;

import org.project.expressart.Comision.domain.CommissionService;
import org.project.expressart.Comision.dto.CommissionRequestDTO;
import org.project.expressart.Comision.dto.CommissionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commissions")
public class ComisionController {

    private final CommissionService commissionService;

    public ComisionController(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @GetMapping
    public ResponseEntity<List<CommissionResponseDTO>> getAll() {
        return ResponseEntity.ok(commissionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommissionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commissionService.findById(id));
    }

    @GetMapping("/artist-profile/{perfilArtistaId}")
    public ResponseEntity<List<CommissionResponseDTO>> getByPerfilArtista(@PathVariable Long perfilArtistaId) {
        return ResponseEntity.ok(commissionService.findByPerfilArtistaId(perfilArtistaId));
    }

    @GetMapping("/artist-profile/{perfilArtistaId}/active")
    public ResponseEntity<List<CommissionResponseDTO>> getActiveByPerfilArtista(@PathVariable Long perfilArtistaId) {
        return ResponseEntity.ok(commissionService.findByPerfilArtistaIdAndEstaActiva(perfilArtistaId, true));
    }

    @GetMapping("/category/{categoriaId}")
    public ResponseEntity<List<CommissionResponseDTO>> getByCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(commissionService.findByCategoriaId(categoriaId));
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<List<CommissionResponseDTO>> getByTag(@PathVariable Long tagId) {
        return ResponseEntity.ok(commissionService.findByTagsId(tagId));
    }

    @PostMapping
    public ResponseEntity<CommissionResponseDTO> create(@RequestBody CommissionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commissionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommissionResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CommissionRequestDTO request) {
        return ResponseEntity.ok(commissionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commissionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}