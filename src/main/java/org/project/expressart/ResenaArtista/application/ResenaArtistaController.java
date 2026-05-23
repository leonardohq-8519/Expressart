package org.project.expressart.ResenaArtista.application;

import org.project.expressart.ResenaArtista.domain.ResenaArtistaService;
import org.project.expressart.ResenaArtista.dto.ResenaArtistaRequestDTO;
import org.project.expressart.ResenaArtista.dto.ResenaArtistaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist-reviews")
public class ResenaArtistaController {

    private final ResenaArtistaService resenaArtistaService;

    public ResenaArtistaController(ResenaArtistaService resenaArtistaService) {
        this.resenaArtistaService = resenaArtistaService;
    }

    @GetMapping
    public ResponseEntity<List<ResenaArtistaResponseDTO>> getAll() {
        return ResponseEntity.ok(resenaArtistaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenaArtistaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resenaArtistaService.findById(id));
    }

    @GetMapping("/artist/{artistaId}")
    public ResponseEntity<List<ResenaArtistaResponseDTO>> getByArtista(@PathVariable Long artistaId) {
        return ResponseEntity.ok(resenaArtistaService.findByArtistaId(artistaId));
    }

    @GetMapping("/client/{clienteId}")
    public ResponseEntity<List<ResenaArtistaResponseDTO>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(resenaArtistaService.findByClienteId(clienteId));
    }

    @PostMapping
    public ResponseEntity<ResenaArtistaResponseDTO> create(@RequestBody ResenaArtistaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaArtistaService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resenaArtistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}