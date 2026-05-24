package org.project.expressart.ResenaCliente.application;

import org.project.expressart.ResenaCliente.domain.ClientReviewService;
import org.project.expressart.ResenaCliente.dto.ClientReviewRequestDTO;
import org.project.expressart.ResenaCliente.dto.ClientReviewResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-reviews")
public class ResenaClienteController {

    private final ClientReviewService resenaClienteService;

    public ResenaClienteController(ClientReviewService resenaClienteService) {
        this.resenaClienteService = resenaClienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClientReviewResponseDTO>> getAll() {
        return ResponseEntity.ok(resenaClienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientReviewResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resenaClienteService.findById(id));
    }

    @GetMapping("/client/{clienteId}")
    public ResponseEntity<List<ClientReviewResponseDTO>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(resenaClienteService.findByClienteId(clienteId));
    }

    @GetMapping("/artist/{artistaId}")
    public ResponseEntity<List<ClientReviewResponseDTO>> getByArtista(@PathVariable Long artistaId) {
        return ResponseEntity.ok(resenaClienteService.findByArtistaId(artistaId));
    }

    @PostMapping
    public ResponseEntity<ClientReviewResponseDTO> create(@RequestBody ClientReviewRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaClienteService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resenaClienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}