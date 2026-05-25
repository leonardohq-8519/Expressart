package org.project.expressart.NotificacionCorreo.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoCreateDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoEstadoDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoResponseDTO;
import org.project.expressart.NotificacionCorreo.infrastructure.NotificacionCorreoRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.project.expressart.exceptions.InvalidStateTransitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {
    @Autowired(required = false)
    private final JavaMailSender mailSender;
    @Autowired
    private ModelMapper modelMapper;
    private final NotificacionCorreoRepository emailNotificationRepository;
    private final UsuarioRepository userRepository;

    public List<NotificacionCorreoResponseDTO> getByUser(Long userId) throws ResourceNotFoundException {
        List<NotificacionCorreo> emailNotif = emailNotificationRepository.findByUsuario_Id(userId);
        if (emailNotif.isEmpty()) {
            throw new ResourceNotFoundException("No email notifications found for user id: " + userId);
        }
        return emailNotif.stream()
                .map(emailNoti -> modelMapper.map(emailNoti, NotificacionCorreoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<NotificacionCorreoResponseDTO> getFailed() throws ResourceNotFoundException {
        List<NotificacionCorreo> emailNotif = emailNotificationRepository.findByEstado(EstadoCorreo.FALLIDO);

        if (emailNotif.isEmpty()) {
            throw new ResourceNotFoundException("No failed email notifications found");
        }

        return emailNotif.stream()
                .map(correo -> modelMapper.map(correo, NotificacionCorreoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<NotificacionCorreoResponseDTO> getPending() throws ResourceNotFoundException {
        List<NotificacionCorreo> emailNotif = emailNotificationRepository.findByEstado(EstadoCorreo.PENDIENTE);

        if (emailNotif.isEmpty()) {
            throw new ResourceNotFoundException("No pending email notifications found");
        }

        return emailNotif.stream()
                .map(correo -> modelMapper.map(correo, NotificacionCorreoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public NotificacionCorreoResponseDTO create(NotificacionCorreoCreateDTO dto) throws ResourceNotFoundException {
        NotificacionCorreo emailNotif = new NotificacionCorreo();
        Usuario user = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + dto.getUsuarioId()));
        emailNotif.setUsuario(user);
        emailNotif.setDestinatarioEmail(dto.getDestinatarioEmail());
        emailNotif.setAsunto(dto.getAsunto());
        emailNotif.setMensaje(dto.getMensaje());
        emailNotif.setTipo(dto.getTipo());
        emailNotificationRepository.save(emailNotif);
        return modelMapper.map(emailNotif, NotificacionCorreoResponseDTO.class);
    }

    @Transactional
    public void updateStatus(Long id, NotificacionCorreoEstadoDTO dto) throws ResourceNotFoundException {
        NotificacionCorreo emailNotif = emailNotificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Email notification not found with ID: " + id));
        emailNotif.setEstado(dto.getEstado());
        emailNotificationRepository.save(emailNotif);
    }

    @Transactional
    public void retry(Long id) throws ResourceNotFoundException {
        NotificacionCorreo emailNotif = emailNotificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Email notification not found with ID: " + id));

        if (emailNotif.getEstado() != EstadoCorreo.FALLIDO) {
            throw new InvalidStateTransitionException("Only failed email notifications can be retried. Current state: " + emailNotif.getEstado());
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailNotif.getDestinatarioEmail());
            message.setSubject(emailNotif.getAsunto());
            message.setFrom("notifications.expressart@gmail.com");
            message.setText(emailNotif.getMensaje());
            mailSender.send(message);
            emailNotif.setEstado(EstadoCorreo.ENVIADO);
        } catch (Exception e) {
            emailNotif.setIntentos(emailNotif.getIntentos() + 1);
            emailNotif.setEstado(EstadoCorreo.FALLIDO);
            emailNotificationRepository.save(emailNotif);
            throw new RuntimeException("Retry failed: " + e.getMessage());
        }
        emailNotificationRepository.save(emailNotif);
    }
}