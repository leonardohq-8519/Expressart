package org.project.expressart.NotifiacionCorreo.infrastructure;

import lombok.RequiredArgsConstructor;
import org.project.expressart.NotifiacionCorreo.application.dto.NotificacionCorreoCreateDTO;
import org.project.expressart.NotifiacionCorreo.application.dto.NotificacionCorreoEstadoDTO;
import org.project.expressart.NotifiacionCorreo.application.dto.NotificacionCorreoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email-notifications")
@RequiredArgsConstructor
public class NotificacionCorreoController {

    private final NotificacionCorreoService notificacionCorreoService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificacionCorreoResponseDTO>> getByUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(notificacionCorreoService.getByUser(userId));
    }

    @GetMapping("/failed")
    public ResponseEntity<List<NotificacionCorreoResponseDTO>> getFailed() {
        return ResponseEntity.ok(notificacionCorreoService.getFailed());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<NotificacionCorreoResponseDTO>> getPending() {
        return ResponseEntity.ok(notificacionCorreoService.getPending());
    }

    @PostMapping
    public ResponseEntity<NotificacionCorreoResponseDTO> create(
            @RequestBody NotificacionCorreoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionCorreoService.create(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody NotificacionCorreoEstadoDTO dto) {
        notificacionCorreoService.updateStatus(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/retry")
    public ResponseEntity<Void> retry(@PathVariable Long id) {
        notificacionCorreoService.retry(id);
        return ResponseEntity.noContent().build();
    }
}