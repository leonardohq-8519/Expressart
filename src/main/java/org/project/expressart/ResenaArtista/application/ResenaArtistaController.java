package org.project.expressart.ResenaArtista.application;

import org.project.expressart.ResenaArtista.domain.ArtistReviewService;
import org.project.expressart.ResenaArtista.dto.ArtistReviewRequestDTO;
import org.project.expressart.ResenaArtista.dto.ArtistReviewResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist-reviews")
public class ResenaArtistaController {

    private final ArtistReviewService artistReviewService;

    public ResenaArtistaController(ArtistReviewService artistReviewService) {
        this.artistReviewService = artistReviewService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistReviewResponseDTO>> getAll() {
        return ResponseEntity.ok(artistReviewService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistReviewResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(artistReviewService.findById(id));
    }

    @GetMapping("/artist/{artistaId}")
    public ResponseEntity<List<ArtistReviewResponseDTO>> getByArtista(@PathVariable Long artistaId) {
        return ResponseEntity.ok(artistReviewService.findByArtistaId(artistaId));
    }

    @GetMapping("/client/{clienteId}")
    public ResponseEntity<List<ArtistReviewResponseDTO>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(artistReviewService.findByClienteId(clienteId));
    }

    @PostMapping
    public ResponseEntity<ArtistReviewResponseDTO> create(@RequestBody ArtistReviewRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artistReviewService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistReviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}