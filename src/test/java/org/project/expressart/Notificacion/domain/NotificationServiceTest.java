package org.project.expressart.Notificacion.domain;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Notificacion.application.dto.MarcarLeidaDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionCountDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionCreateDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionResponseDTO;
import org.project.expressart.Notificacion.infrastructure.NotificacionRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceTest {

    private final ModelMapper modelMapper;
    private final NotificacionRepository notificationRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<NotificacionResponseDTO> getByUsuario(Long usuarioId) {
        return notificationRepository.findByUsuarioUsuarioId(usuarioId).stream()
                .map(notificacion -> modelMapper.map(notificacion, NotificacionResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotificacionResponseDTO> getNoLeidas(Long usuarioId) {
        return notificationRepository.findByUsuarioUsuarioIdAndLeida(usuarioId, false).stream()
                .map(notificacion -> modelMapper.map(notificacion, NotificacionResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NotificacionCountDTO countNoLeidas(Long usuarioId) {
        long count = notificationRepository.countByUsuarioUsuarioIdAndLeida(usuarioId, false);
        NotificacionCountDTO dto = new NotificacionCountDTO();
        dto.setUsuarioId(usuarioId);
        dto.setNoLeidas(count);
        return dto;
    }

    @Transactional
    public NotificacionResponseDTO crear(NotificacionCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Usuario no encontrado"));

        Notificacion notificacion = modelMapper.map(dto, Notificacion.class);
        notificacion.setUsuario(usuario);
        notificacion.setLeida(false);

        Notificacion guardada = notificationRepository.save(notificacion);
        return modelMapper.map(guardada, NotificacionResponseDTO.class);
    }

    @Transactional
    public void marcarLeida(MarcarLeidaDTO dto) {
        List<Notificacion> notificaciones = notificationRepository.findAllById(dto.getNotificacionIds());
        if (!notificaciones.isEmpty()) {
            notificaciones.forEach(notificacion -> {
                notificacion.setLeida(true);
                notificacion.setFechaLectura(ZonedDateTime.now());
            });
            notificationRepository.saveAll(notificaciones);
        }
    }

    @Transactional
    public void marcarTodasLeidas(Long usuarioId) {
        List<Notificacion> noLeidas = notificationRepository.findByUsuarioUsuarioIdAndLeida(usuarioId, false);
        if (!noLeidas.isEmpty()) {
            noLeidas.forEach(notificacion -> {
                notificacion.setLeida(true);
                notificacion.setFechaLectura(ZonedDateTime.now());
            });
            notificationRepository.saveAll(noLeidas);
        }
    }

    @Transactional
    public void eliminar(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
        }
    }
}