package org.project.expressart.Notificacion.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
<<<<<<<< HEAD:src/main/java/org/project/expressart/Notificacion/application/NotificacionController.java
import org.project.expressart.Notificacion.domain.NotificationService;
import org.project.expressart.Notificacion.dto.MarcarLeidaDTO;
import org.project.expressart.Notificacion.dto.NotificacionCountDTO;
import org.project.expressart.Notificacion.dto.NotificacionCreateDTO;
import org.project.expressart.Notificacion.dto.NotificacionResponseDTO;
import org.project.expressart.exceptions.ResourceNotFoundException;
========
import org.project.expressart.Notificacion.application.dto.MarcarLeidaDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionCountDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionCreateDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionResponseDTO;
import org.project.expressart.Notificacion.domain.NotificationService;
>>>>>>>> origin/testing:src/main/java/org/project/expressart/Notificacion/infrastructure/NotificationController.java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificationController {

<<<<<<<< HEAD:src/main/java/org/project/expressart/Notificacion/application/NotificacionController.java
    private final NotificationService notificationService;
========
    private final NotificationService notificacionService;
>>>>>>>> origin/testing:src/main/java/org/project/expressart/Notificacion/infrastructure/NotificationController.java

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionResponseDTO>> getByUsuario(
            @PathVariable Long usuarioId) throws ResourceNotFoundException{
        return ResponseEntity.ok(notificationService.getByUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<NotificacionResponseDTO>> getNoLeidas(
            @PathVariable Long usuarioId) throws ResourceNotFoundException{
        return ResponseEntity.ok(notificationService.getNoLeidas(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/count")
    public ResponseEntity<NotificacionCountDTO> countNoLeidas(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificationService.countNoLeidas(usuarioId));
    }

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crear(
            @Valid @RequestBody NotificacionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.crear(dto));
    }

    @PatchMapping("/marcar-leida")
    public ResponseEntity<Void> marcarLeida(
            @Valid @RequestBody MarcarLeidaDTO dto)throws ResourceNotFoundException {
        notificationService.marcarLeida(dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/usuario/{usuarioId}/marcar-todas-leidas")
    public ResponseEntity<Void> marcarTodasLeidas(
            @PathVariable Long usuarioId) throws ResourceNotFoundException{
        notificationService.marcarTodasLeidas(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificationService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}