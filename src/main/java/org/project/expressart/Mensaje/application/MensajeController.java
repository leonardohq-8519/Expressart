package org.project.expressart.Mensaje.application;

import org.project.expressart.Mensaje.application.MensajeService;
import org.project.expressart.Mensaje.dto.MensajeRequestDTO;
import org.project.expressart.Mensaje.dto.MensajeResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MensajeController {

    private final MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping
    public ResponseEntity<List<MensajeResponseDTO>> getAll() {
        return ResponseEntity.ok(mensajeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mensajeService.findById(id));
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MensajeResponseDTO>> getByChat(@PathVariable Long chatId) {
        return ResponseEntity.ok(mensajeService.findByChatId(chatId));
    }

    @PostMapping
    public ResponseEntity<MensajeResponseDTO> create(@RequestBody MensajeRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.create(request));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<MensajeResponseDTO> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(mensajeService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mensajeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}