package org.project.expressart.NotificacionCorreo.domain;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoCreateDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoEstadoDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoResponseDTO;
import org.project.expressart.NotificacionCorreo.infrastructure.NotificacionCorreoRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.exception.ResourceNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailNotificationServiceTest {

    @Mock
    private NotificacionCorreoRepository emailNotificationRepository;

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmailNotificationService emailNotificationService;

    private NotificacionCorreo notificacionCorreo;
    private NotificacionCorreoResponseDTO responseDTO;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("test@example.com");

        notificacionCorreo = new NotificacionCorreo();
        notificacionCorreo.setId(1L);
        notificacionCorreo.setUsuario(usuario);
        notificacionCorreo.setDestinatarioEmail("dest@example.com");
        notificacionCorreo.setAsunto("Asunto test");
        notificacionCorreo.setTipo(TipoCorreo.NUEVA_ORDEN);
        notificacionCorreo.setEstado(EstadoCorreo.PENDIENTE);
        notificacionCorreo.setIntentos(0);

        responseDTO = new NotificacionCorreoResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setDestinatarioEmail("dest@example.com");
        responseDTO.setEstado(EstadoCorreo.PENDIENTE);
    }

    @Test
    @DisplayName("Debería retornar notificaciones por usuario cuando existen")
    void shouldReturnNotificacionesByUserWhenExist() throws ResourceNotFoundException {
        when(emailNotificationRepository.findByUsuarioId(1L)).thenReturn(List.of(notificacionCorreo));
        when(modelMapper.map(any(NotificacionCorreo.class), eq(NotificacionCorreoResponseDTO.class))).thenReturn(responseDTO);

        List<NotificacionCorreoResponseDTO> result = emailNotificationService.getByUser(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(emailNotificationRepository, times(1)).findByUsuarioId(1L);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando no hay notificaciones para el usuario")
    void shouldThrowExceptionWhenNoNotificacionesByUser() {
        when(emailNotificationRepository.findByUsuarioId(99L)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> emailNotificationService.getByUser(99L));
    }

    @Test
    @DisplayName("Debería retornar notificaciones fallidas cuando existen")
    void shouldReturnFailedNotificacionesWhenExist() throws ResourceNotFoundException {
        notificacionCorreo.setEstado(EstadoCorreo.FALLIDO);
        responseDTO.setEstado(EstadoCorreo.FALLIDO);
        when(emailNotificationRepository.findByEstado(EstadoCorreo.FALLIDO)).thenReturn(List.of(notificacionCorreo));
        when(modelMapper.map(any(NotificacionCorreo.class), eq(NotificacionCorreoResponseDTO.class))).thenReturn(responseDTO);

        List<NotificacionCorreoResponseDTO> result = emailNotificationService.getFailed();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando no hay notificaciones fallidas")
    void shouldThrowExceptionWhenNoFailedNotificaciones() {
        when(emailNotificationRepository.findByEstado(EstadoCorreo.FALLIDO)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> emailNotificationService.getFailed());
    }

    @Test
    @DisplayName("Debería retornar notificaciones pendientes cuando existen")
    void shouldReturnPendingNotificacionesWhenExist() throws ResourceNotFoundException {
        when(emailNotificationRepository.findByEstado(EstadoCorreo.PENDIENTE)).thenReturn(List.of(notificacionCorreo));
        when(modelMapper.map(any(NotificacionCorreo.class), eq(NotificacionCorreoResponseDTO.class))).thenReturn(responseDTO);

        List<NotificacionCorreoResponseDTO> result = emailNotificationService.getPending();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debería crear notificación correo cuando el usuario existe")
    void shouldCreateNotificacionCorreoWhenUserExists() {
        NotificacionCorreoCreateDTO dto = new NotificacionCorreoCreateDTO(
                1L, "dest@example.com", "Asunto", "Cuerpo", TipoCorreo.NUEVA_ORDEN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(emailNotificationRepository.save(any(NotificacionCorreo.class))).thenReturn(notificacionCorreo);
        when(modelMapper.map(any(NotificacionCorreo.class), eq(NotificacionCorreoResponseDTO.class))).thenReturn(responseDTO);

        NotificacionCorreoResponseDTO result = emailNotificationService.create(dto);

        assertNotNull(result);
        verify(emailNotificationRepository, times(1)).save(any(NotificacionCorreo.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción al crear notificación cuando el usuario no existe")
    void shouldThrowExceptionWhenCreatingNotificacionWithNonExistentUser() {
        NotificacionCorreoCreateDTO dto = new NotificacionCorreoCreateDTO(
                99L, "dest@example.com", "Asunto", "Cuerpo", TipoCorreo.NUEVA_ORDEN);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> emailNotificationService.create(dto));
        verify(emailNotificationRepository, never()).save(any(NotificacionCorreo.class));
    }

    @Test
    @DisplayName("Debería actualizar estado de notificación cuando existe")
    void shouldUpdateStatusWhenNotificacionExists() throws ResourceNotFoundException {
        NotificacionCorreoEstadoDTO estadoDTO = new NotificacionCorreoEstadoDTO(1L, EstadoCorreo.ENVIADO, null, null);
        when(emailNotificationRepository.findById(1L)).thenReturn(Optional.of(notificacionCorreo));
        when(emailNotificationRepository.save(any(NotificacionCorreo.class))).thenReturn(notificacionCorreo);

        assertDoesNotThrow(() -> emailNotificationService.updateStatus(1L, estadoDTO));
        verify(emailNotificationRepository, times(1)).save(any(NotificacionCorreo.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción al actualizar estado cuando la notificación no existe")
    void shouldThrowExceptionWhenUpdatingStatusOfNonExistentNotificacion() {
        NotificacionCorreoEstadoDTO estadoDTO = new NotificacionCorreoEstadoDTO(99L, EstadoCorreo.ENVIADO, null, null);
        when(emailNotificationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> emailNotificationService.updateStatus(99L, estadoDTO));
    }

    @Test
    @DisplayName("Debería reintentar notificación fallida y establecer estado PENDIENTE")
    void shouldRetryFailedNotificacionWhenExists() throws ResourceNotFoundException {
        notificacionCorreo.setEstado(EstadoCorreo.FALLIDO);
        when(emailNotificationRepository.findById(1L)).thenReturn(Optional.of(notificacionCorreo));
        when(emailNotificationRepository.save(any(NotificacionCorreo.class))).thenReturn(notificacionCorreo);

        assertDoesNotThrow(() -> emailNotificationService.retry(1L));
        assertEquals(EstadoCorreo.PENDIENTE, notificacionCorreo.getEstado());
        verify(emailNotificationRepository, times(1)).save(any(NotificacionCorreo.class));
    }

    @Test
    @DisplayName("Debería lanzar excepción al reintentar cuando la notificación no existe")
    void shouldThrowExceptionWhenRetryingNonExistentNotificacion() {
        when(emailNotificationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> emailNotificationService.retry(99L));
    }
}