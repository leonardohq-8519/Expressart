package org.project.expressart.Notificacion.domain;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.Notificacion.dto.MarcarLeidaDTO;
import org.project.expressart.Notificacion.dto.NotificacionCountDTO;
import org.project.expressart.Notificacion.dto.NotificacionCreateDTO;
import org.project.expressart.Notificacion.dto.NotificacionResponseDTO;
import org.project.expressart.Notificacion.infrastructure.NotificacionRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final NotificacionRepository notificationRepository;
    @Autowired
    private final UsuarioRepository userRepository;

    public List<NotificacionResponseDTO> getByUsuario (Long usuarioId) throws ResourceNotFoundException {
        List<Notificacion> notification = notificationRepository.findByUsuarioId(usuarioId);
        if (notification.isEmpty()) {
            throw new ResourceNotFoundException("No notifications found for post id: " + usuarioId);
        }
        return notification.stream()
                .map(ticket -> modelMapper.map(notification, NotificacionResponseDTO.class))
                .collect(Collectors.toList());
    }
    public List<NotificacionResponseDTO> getNoLeidas(Long usuarioId) throws ResourceNotFoundException {
        List<Notificacion> notifications = notificationRepository.findByUsuarioId(usuarioId);

        if (notifications.isEmpty()) {
            throw new ResourceNotFoundException("No notifications found for user id: " + usuarioId);
        }
        List<NotificacionResponseDTO> unreadDTOs = notifications.stream()
                .filter(notif -> !notif.getLeida())
                .map(notif -> modelMapper.map(notif, NotificacionResponseDTO.class))
                .collect(Collectors.toList());
        if (unreadDTOs.isEmpty()) {
            throw new ResourceNotFoundException("No unread notifications found for user id: " + usuarioId);
        }
        return unreadDTOs;
    }
    public NotificacionCountDTO countNoLeidas (Long usuarioId){

        List<Notificacion> notifications = notificationRepository.findByUsuarioId(usuarioId);

        if (notifications.isEmpty()) {
            NotificacionCountDTO notifNoLeidas = new NotificacionCountDTO();
            notifNoLeidas.setNoLeidas(0L);
            return notifNoLeidas;
        }

        long totalNoLeidas = notifications.stream()
                .filter(n -> !n.getLeida())
                .count();

        NotificacionCountDTO notifNoLeidas = new NotificacionCountDTO();
        notifNoLeidas.setNoLeidas(totalNoLeidas);
        return notifNoLeidas;


    }
    public NotificacionResponseDTO crear (NotificacionCreateDTO dto){
        Notificacion notification = new Notificacion();
        Usuario user = userRepository.findById(dto.getUsuarioId()).orElseThrow(()-> new EntityNotFoundException("User not found"));
        notification.setUsuario(user);
        notification.setTipo(dto.getTipo());
        notification.setTitulo(dto.getTitulo());
        notification.setMensaje(dto.getMensaje());
        notification.setUrlDestino(dto.getUrlDestino());
        notificationRepository.save(notification);
        return modelMapper.map(notification, NotificacionResponseDTO.class);
    }

    @Transactional
    public void marcarLeida(MarcarLeidaDTO dto) throws ResourceNotFoundException {
        if (dto.getNotificacionIds() == null || dto.getNotificacionIds().isEmpty()) {
            throw new IllegalArgumentException("List of notification IDs cannot be empty");
        }
        List<Notificacion> notifications = notificationRepository.findAllById(dto.getNotificacionIds());
        if (notifications.size() != dto.getNotificacionIds().size()) {
            throw new ResourceNotFoundException("Some notification IDs were not found");
        }
        notifications.forEach(n -> n.setLeida(true));
        notificationRepository.saveAll(notifications);
    }
    public void marcarTodasLeidas(Long usuarioId) throws ResourceNotFoundException {
        List<Notificacion> notifications = notificationRepository.findByUsuarioId(usuarioId);
        if (notifications.isEmpty()) {
            throw new ResourceNotFoundException("No notifications found for user id: " + usuarioId);
        }
        notifications.stream()
                .filter(n -> !n.getLeida())
                .forEach(n -> n.setLeida(true));
        notificationRepository.saveAll(notifications);
    }

    public void eliminar(Long id){
        if (notificationRepository.existsById(id))
            notificationRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Notification with ID " + id + " doesn't exist");
    }
}
