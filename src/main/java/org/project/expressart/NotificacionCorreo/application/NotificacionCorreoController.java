package org.project.expressart.NotificacionCorreo.application;

import lombok.RequiredArgsConstructor;
import org.project.expressart.NotificacionCorreo.domain.EmailNotificationService;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoCreateDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoEstadoDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoResponseDTO;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email-notifications")
@RequiredArgsConstructor
public class NotificacionCorreoController {

    private final EmailNotificationService emailNotificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificacionCorreoResponseDTO>> getByUser(
            @PathVariable Long userId) throws ResourceNotFoundException{
        return ResponseEntity.ok(emailNotificationService.getByUser(userId));
    }

    @GetMapping("/failed")
    public ResponseEntity<List<NotificacionCorreoResponseDTO>> getFailed() throws ResourceNotFoundException{
        return ResponseEntity.ok(emailNotificationService.getFailed());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<NotificacionCorreoResponseDTO>> getPending()throws ResourceNotFoundException {
        return ResponseEntity.ok(emailNotificationService.getPending());
    }

    @PostMapping
    public ResponseEntity<NotificacionCorreoResponseDTO> create(
            @RequestBody NotificacionCorreoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(emailNotificationService.create(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody NotificacionCorreoEstadoDTO dto)throws ResourceNotFoundException {
        emailNotificationService.updateStatus(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/retry")
    public ResponseEntity<Void> retry(@PathVariable Long id) {
        emailNotificationService.retry(id);
        return ResponseEntity.noContent().build();
    }
}