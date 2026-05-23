package org.project.expressart.TicketSoporte.application;

import org.project.expressart.TicketSoporte.application.TicketSoporteService;
import org.project.expressart.TicketSoporte.application.dto.TicketSoporteRequestDTO;
import org.project.expressart.TicketSoporte.application.dto.TicketSoporteResponseDTO;
import org.project.expressart.TicketSoporte.domain.CategoriaTicket;
import org.project.expressart.TicketSoporte.domain.EstadoTicket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/support-tickets")
public class TicketSoporteController {

    private final TicketSoporteService ticketSoporteService;

    public TicketSoporteController(TicketSoporteService ticketSoporteService) {
        this.ticketSoporteService = ticketSoporteService;
    }

    @GetMapping
    public ResponseEntity<List<TicketSoporteResponseDTO>> getAll() {
        return ResponseEntity.ok(ticketSoporteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketSoporteResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketSoporteService.findById(id));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<List<TicketSoporteResponseDTO>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ticketSoporteService.findByUsuarioId(usuarioId));
    }

    @GetMapping("/status/{estado}")
    public ResponseEntity<List<TicketSoporteResponseDTO>> getByEstado(@PathVariable EstadoTicket estado) {
        return ResponseEntity.ok(ticketSoporteService.findByEstado(estado));
    }

    @GetMapping("/status/{estado}/category/{categoria}")
    public ResponseEntity<List<TicketSoporteResponseDTO>> getByEstadoAndCategoria(
            @PathVariable EstadoTicket estado,
            @PathVariable CategoriaTicket categoria) {
        return ResponseEntity.ok(ticketSoporteService.findByEstadoAndCategoria(estado, categoria));
    }

    @PostMapping
    public ResponseEntity<TicketSoporteResponseDTO> create(@RequestBody TicketSoporteRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketSoporteService.create(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketSoporteResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam EstadoTicket estado) {
        return ResponseEntity.ok(ticketSoporteService.updateStatus(id, estado));
    }

    @PatchMapping("/{id}/response")
    public ResponseEntity<TicketSoporteResponseDTO> addResponse(
            @PathVariable Long id,
            @RequestParam String respuesta) {
        return ResponseEntity.ok(ticketSoporteService.addResponse(id, respuesta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketSoporteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}