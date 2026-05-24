package org.project.expressart.Mensaje.application;

import org.project.expressart.Mensaje.domain.MessageService;
import org.project.expressart.Mensaje.dto.MessageRequestDTO;
import org.project.expressart.Mensaje.dto.MessageResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MensajeController {

    private final MessageService mensajeService;

    public MensajeController(MessageService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseDTO>> getAll() {
        return ResponseEntity.ok(mensajeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mensajeService.findById(id));
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageResponseDTO>> getByChat(@PathVariable Long chatId) {
        return ResponseEntity.ok(mensajeService.findByChatId(chatId));
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> create(@RequestBody MessageRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.create(request));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<MessageResponseDTO> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(mensajeService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mensajeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}