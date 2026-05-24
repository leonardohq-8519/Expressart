package org.project.expressart.NotificacionCorreo.domain;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoCreateDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoEstadoDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoResponseDTO;
import org.project.expressart.NotificacionCorreo.infrastructure.NotificacionCorreoRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final NotificacionCorreoRepository emailNotificationRepository;
    @Autowired
    private final UsuarioRepository userRepository;
    public List<NotificacionCorreoResponseDTO> getByUser (Long userId) throws ResourceNotFoundException {
        List<NotificacionCorreo> emailNotif = emailNotificationRepository.findByUserId(userId);
        if (emailNotif.isEmpty()) {
            throw new ResourceNotFoundException("No email notifications found for user id: " + userId);
        }
        return emailNotif.stream()
                .map(ticket -> modelMapper.map(emailNotif, NotificacionCorreoResponseDTO.class))
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

    public NotificacionCorreoResponseDTO create (NotificacionCorreoCreateDTO dto){
        NotificacionCorreo emailNotif = new NotificacionCorreo();
        Usuario user = userRepository.findById(dto.getUsuarioId()).orElseThrow(()-> new EntityNotFoundException("User not found"));
        emailNotif.setUsuario(user);
        emailNotif.setDestinatarioEmail(dto.getDestinatarioEmail());
        emailNotif.setAsunto(dto.getAsunto());
        emailNotif.setTipo(dto.getTipo());
        emailNotificationRepository.save(emailNotif);
        return modelMapper.map(emailNotif, NotificacionCorreoResponseDTO.class);
    }

    public void updateStatus(Long id, NotificacionCorreoEstadoDTO dto) throws ResourceNotFoundException {
        NotificacionCorreo emailNotif = emailNotificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Email notification not found with ID: " + id));
        emailNotif.setEstado(dto.getEstado());
        emailNotificationRepository.save(emailNotif);
    }

    public void retry(Long id){

    }
