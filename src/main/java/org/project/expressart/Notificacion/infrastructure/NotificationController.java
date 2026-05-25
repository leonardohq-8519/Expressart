package org.project.expressart.Notificacion.infrastructure;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.expressart.Notificacion.application.dto.MarcarLeidaDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionCountDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionCreateDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionResponseDTO;
import org.project.expressart.Notificacion.domain.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificacionService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionResponseDTO>> getByUsuario(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.getByUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<NotificacionResponseDTO>> getNoLeidas(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.getNoLeidas(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/count")
    public ResponseEntity<NotificacionCountDTO> countNoLeidas(
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.countNoLeidas(usuarioId));
    }

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crear(
            @Valid @RequestBody NotificacionCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionService.crear(dto));
    }

    @PatchMapping("/marcar-leida")
    public ResponseEntity<Void> marcarLeida(
            @Valid @RequestBody MarcarLeidaDTO dto) {
        notificacionService.marcarLeida(dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/usuario/{usuarioId}/marcar-todas-leidas")
    public ResponseEntity<Void> marcarTodasLeidas(
            @PathVariable Long usuarioId) {
        notificacionService.marcarTodasLeidas(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}