package org.project.expressart.ResenaCliente.application;

import org.project.expressart.ResenaCliente.domain.ResenaClienteService;
import org.project.expressart.ResenaCliente.dto.ResenaClienteRequestDTO;
import org.project.expressart.ResenaCliente.dto.ResenaClienteResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-reviews")
public class ResenaClienteController {

    private final ResenaClienteService resenaClienteService;

    public ResenaClienteController(ResenaClienteService resenaClienteService) {
        this.resenaClienteService = resenaClienteService;
    }

    @GetMapping
    public ResponseEntity<List<ResenaClienteResponseDTO>> getAll() {
        return ResponseEntity.ok(resenaClienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenaClienteResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resenaClienteService.findById(id));
    }

    @GetMapping("/client/{clienteId}")
    public ResponseEntity<List<ResenaClienteResponseDTO>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(resenaClienteService.findByClienteId(clienteId));
    }

    @GetMapping("/artist/{artistaId}")
    public ResponseEntity<List<ResenaClienteResponseDTO>> getByArtista(@PathVariable Long artistaId) {
        return ResponseEntity.ok(resenaClienteService.findByArtistaId(artistaId));
    }

    @PostMapping
    public ResponseEntity<ResenaClienteResponseDTO> create(@RequestBody ResenaClienteRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaClienteService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resenaClienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}