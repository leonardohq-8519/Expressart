package org.project.expressart.Orden.infrastructure;

import org.project.expressart.Orden.application.OrdenService;
import org.project.expressart.Orden.application.dto.OrdenRequestDTO;
import org.project.expressart.Orden.application.dto.OrdenResponseDTO;
import org.project.expressart.Orden.domain.EstadoOrden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping
    public ResponseEntity<List<OrdenResponseDTO>> getAll() {
        return ResponseEntity.ok(ordenService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ordenService.findById(id));
    }

    @GetMapping("/client/{clienteId}")
    public ResponseEntity<List<OrdenResponseDTO>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ordenService.findByClienteId(clienteId));
    }

    @GetMapping("/artist/{artistaId}")
    public ResponseEntity<List<OrdenResponseDTO>> getByArtista(@PathVariable Long artistaId) {
        return ResponseEntity.ok(ordenService.findByArtistaId(artistaId));
    }

    @GetMapping("/client/{clienteId}/status/{estado}")
    public ResponseEntity<List<OrdenResponseDTO>> getByClienteAndEstado(
            @PathVariable Long clienteId,
            @PathVariable EstadoOrden estado) {
        return ResponseEntity.ok(ordenService.findByClienteIdAndEstado(clienteId, estado));
    }

    @GetMapping("/artist/{artistaId}/status/{estado}")
    public ResponseEntity<List<OrdenResponseDTO>> getByArtistaAndEstado(
            @PathVariable Long artistaId,
            @PathVariable EstadoOrden estado) {
        return ResponseEntity.ok(ordenService.findByArtistaIdAndEstado(artistaId, estado));
    }

    @PostMapping
    public ResponseEntity<OrdenResponseDTO> create(@RequestBody OrdenRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> update(
            @PathVariable Long id,
            @RequestBody OrdenRequestDTO request) {
        return ResponseEntity.ok(ordenService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrdenResponseDTO> updateEstado(
            @PathVariable Long id,
            @RequestParam EstadoOrden estado) {
        return ResponseEntity.ok(ordenService.updateEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ordenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}