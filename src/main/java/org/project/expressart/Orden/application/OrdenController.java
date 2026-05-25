package org.project.expressart.Orden.application;

import org.project.expressart.Orden.domain.OrderService;
import org.project.expressart.Orden.dto.OrderRequestDTO;
import org.project.expressart.Orden.dto.OrderResponseDTO;
import org.project.expressart.Orden.domain.EstadoOrden;
import org.project.expressart.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdenController {

    private final OrderService orderService;

    public OrdenController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id)throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/client/{clienteId}")
    public ResponseEntity<List<OrderResponseDTO>> getByCliente(@PathVariable Long clienteId) throws ResourceNotFoundException{
        return ResponseEntity.ok(orderService.findByClienteId(clienteId));
    }

    @GetMapping("/artist/{artistaId}")
    public ResponseEntity<List<OrderResponseDTO>> getByArtista(@PathVariable Long artistaId)throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.findByArtistaId(artistaId));
    }

    @GetMapping("/client/{clienteId}/status/{estado}")
    public ResponseEntity<List<OrderResponseDTO>> getByClienteAndEstado(
            @PathVariable Long clienteId,
            @PathVariable EstadoOrden estado) throws ResourceNotFoundException{
        return ResponseEntity.ok(orderService.findByClienteIdAndEstado(clienteId, estado));
    }

    @GetMapping("/artist/{artistaId}/status/{estado}")
    public ResponseEntity<List<OrderResponseDTO>> getByArtistaAndEstado(
            @PathVariable Long artistaId,
            @PathVariable EstadoOrden estado) throws ResourceNotFoundException{
        return ResponseEntity.ok(orderService.findByArtistaIdAndEstado(artistaId, estado));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(
            @PathVariable Long id,
            @RequestBody OrderRequestDTO request)throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateEstado(
            @PathVariable Long id,
            @RequestParam EstadoOrden estado)throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.updateEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}