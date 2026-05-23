package org.project.expressart.Portafolio.infrastructure;

import org.project.expressart.Portafolio.application.PortafolioService;
import org.project.expressart.Portafolio.application.dto.PortafolioRequestDTO;
import org.project.expressart.Portafolio.application.dto.PortafolioResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
public class PortafolioController {

    private final PortafolioService portafolioService;

    public PortafolioController(PortafolioService portafolioService) {
        this.portafolioService = portafolioService;
    }

    @GetMapping
    public ResponseEntity<List<PortafolioResponseDTO>> getAll() {
        return ResponseEntity.ok(portafolioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortafolioResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(portafolioService.findById(id));
    }

    @GetMapping("/artist-profile/{perfilArtistaId}")
    public ResponseEntity<List<PortafolioResponseDTO>> getByPerfilArtista(@PathVariable Long perfilArtistaId) {
        return ResponseEntity.ok(portafolioService.findByPerfilArtistaId(perfilArtistaId));
    }

    @GetMapping("/artist-profile/{perfilArtistaId}/public")
    public ResponseEntity<List<PortafolioResponseDTO>> getPublicByPerfilArtista(@PathVariable Long perfilArtistaId) {
        return ResponseEntity.ok(portafolioService.findByPerfilArtistaIdAndEsPublico(perfilArtistaId, true));
    }

    @PostMapping
    public ResponseEntity<PortafolioResponseDTO> create(@RequestBody PortafolioRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(portafolioService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortafolioResponseDTO> update(
            @PathVariable Long id,
            @RequestBody PortafolioRequestDTO request) {
        return ResponseEntity.ok(portafolioService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        portafolioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}