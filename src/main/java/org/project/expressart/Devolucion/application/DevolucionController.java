package org.project.expressart.Devolucion.application;

import org.project.expressart.Devolucion.application.DevolucionService;
import org.project.expressart.Devolucion.dto.DevolutionRequestDTO;
import org.project.expressart.Devolucion.dto.DevolutionResponseDTO;
import org.project.expressart.Devolucion.domain.EstadoDevolucion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refunds")
public class DevolucionController {

    private final DevolucionService devolucionService;

    public DevolucionController(DevolucionService devolucionService) {
        this.devolucionService = devolucionService;
    }

    @GetMapping
    public ResponseEntity<List<DevolutionResponseDTO>> getAll() {
        return ResponseEntity.ok(devolucionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevolutionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(devolucionService.findById(id));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DevolutionResponseDTO> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(devolucionService.findByOrderId(orderId));
    }

    @GetMapping("/status/{estado}")
    public ResponseEntity<List<DevolutionResponseDTO>> getByEstado(@PathVariable EstadoDevolucion estado) {
        return ResponseEntity.ok(devolucionService.findByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<DevolutionResponseDTO> create(@RequestBody DevolutionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(devolucionService.create(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DevolutionResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam EstadoDevolucion estado) {
        return ResponseEntity.ok(devolucionService.updateStatus(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        devolucionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}