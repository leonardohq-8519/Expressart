package org.project.expressart.Devolucion.infrastructure;

import org.project.expressart.Devolucion.application.DevolucionService;
import org.project.expressart.Devolucion.application.dto.DevolucionRequestDTO;
import org.project.expressart.Devolucion.application.dto.DevolucionResponseDTO;
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
    public ResponseEntity<List<DevolucionResponseDTO>> getAll() {
        return ResponseEntity.ok(devolucionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DevolucionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(devolucionService.findById(id));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<DevolucionResponseDTO> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(devolucionService.findByOrderId(orderId));
    }

    @GetMapping("/status/{estado}")
    public ResponseEntity<List<DevolucionResponseDTO>> getByEstado(@PathVariable EstadoDevolucion estado) {
        return ResponseEntity.ok(devolucionService.findByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<DevolucionResponseDTO> create(@RequestBody DevolucionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(devolucionService.create(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DevolucionResponseDTO> updateStatus(
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