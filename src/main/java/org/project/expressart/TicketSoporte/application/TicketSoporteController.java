package org.project.expressart.TicketSoporte.application;

import org.project.expressart.TicketSoporte.domain.SupportTicketService;
import org.project.expressart.TicketSoporte.dto.SupportTicketRequestDTO;
import org.project.expressart.TicketSoporte.dto.SupportTicketResponseDTO;
import org.project.expressart.TicketSoporte.domain.CategoriaTicket;
import org.project.expressart.TicketSoporte.domain.EstadoTicket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/support-tickets")
public class TicketSoporteController {

    private final SupportTicketService supportTicketService;

    public TicketSoporteController(SupportTicketService supportTicketService) {
        this.supportTicketService = supportTicketService;
    }

    @GetMapping
    public ResponseEntity<List<SupportTicketResponseDTO>> getAll() {
        return ResponseEntity.ok(supportTicketService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportTicketResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(supportTicketService.findById(id));
    }

    @GetMapping("/user/{usuarioId}")
    public ResponseEntity<List<SupportTicketResponseDTO>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(supportTicketService.findByUsuarioId(usuarioId));
    }

    @GetMapping("/status/{estado}")
    public ResponseEntity<List<SupportTicketResponseDTO>> getByEstado(@PathVariable EstadoTicket estado) {
        return ResponseEntity.ok(supportTicketService.findByEstado(estado));
    }

    @GetMapping("/status/{estado}/category/{categoria}")
    public ResponseEntity<List<SupportTicketResponseDTO>> getByEstadoAndCategoria(
            @PathVariable EstadoTicket estado,
            @PathVariable CategoriaTicket categoria) {
        return ResponseEntity.ok(supportTicketService.findByEstadoAndCategoria(estado, categoria));
    }

    @PostMapping
    public ResponseEntity<SupportTicketResponseDTO> create(@RequestBody SupportTicketRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supportTicketService.create(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SupportTicketResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam EstadoTicket estado) {
        return ResponseEntity.ok(supportTicketService.updateStatus(id, estado));
    }

    @PatchMapping("/{id}/response")
    public ResponseEntity<SupportTicketResponseDTO> addResponse(
            @PathVariable Long id,
            @RequestParam String respuesta) {
        return ResponseEntity.ok(supportTicketService.addResponse(id, respuesta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        supportTicketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}