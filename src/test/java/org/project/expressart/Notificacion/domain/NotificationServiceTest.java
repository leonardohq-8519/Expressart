package org.project.expressart.Notificacion.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.Notificacion.application.dto.MarcarLeidaDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionCountDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionCreateDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionResponseDTO;
import org.project.expressart.Notificacion.domain.Notificacion;
import org.project.expressart.Notificacion.infrastructure.NotificacionRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private NotificacionRepository notificationRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private NotificationService notificationService; // El servicio real bajo testeo

    private Usuario usuarioPrueba;
    private Notificacion notificacionPrueba;
    private NotificacionResponseDTO responseDTOPrueba;

    @BeforeEach
    void setUp() {
        usuarioPrueba = new Usuario();

        notificacionPrueba = new Notificacion();
        notificacionPrueba.setUsuario(usuarioPrueba);
        notificacionPrueba.setLeida(false);

        responseDTOPrueba = new NotificacionResponseDTO();
    }

    @Test
    void getByUsuario_DebeRetornarListaDeNotificaciones() {
        Long usuarioId = 1L;
        when(notificationRepository.findByUsuarioUsuarioId(usuarioId))
                .thenReturn(List.of(notificacionPrueba));
        when(modelMapper.map(any(Notificacion.class), eq(NotificacionResponseDTO.class)))
                .thenReturn(responseDTOPrueba);

        List<NotificacionResponseDTO> resultado = notificationService.getByUsuario(usuarioId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(notificationRepository, times(1)).findByUsuarioUsuarioId(usuarioId);
    }

    @Test
    void getNoLeidas_DebeRetornarSoloNotificacionesNoLeidas() {

        Long usuarioId = 1L;
        when(notificationRepository.findByUsuarioUsuarioIdAndLeida(usuarioId, false))
                .thenReturn(List.of(notificacionPrueba));
        when(modelMapper.map(any(Notificacion.class), eq(NotificacionResponseDTO.class)))
                .thenReturn(responseDTOPrueba);

        List<NotificacionResponseDTO> resultado = notificationService.getNoLeidas(usuarioId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(notificationRepository, times(1)).findByUsuarioUsuarioIdAndLeida(usuarioId, false);
    }

    @Test
    void countNoLeidas_DebeRetornarElConteoCorrecto() {

        Long usuarioId = 1L;
        when(notificationRepository.countByUsuarioUsuarioIdAndLeida(usuarioId, false)).thenReturn(5L);

        NotificacionCountDTO resultado = notificationService.countNoLeidas(usuarioId);

        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getUsuarioId());
        assertEquals(5L, resultado.getNoLeidas());
    }

    @Test
    void crear_UsuarioExiste_DebeGuardarYRetornarNotificacion() {

        NotificacionCreateDTO createDTO = new NotificacionCreateDTO();
        createDTO.setUsuarioId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioPrueba));
        when(modelMapper.map(createDTO, Notificacion.class)).thenReturn(notificacionPrueba);
        when(notificationRepository.save(any(Notificacion.class))).thenReturn(notificacionPrueba);
        when(modelMapper.map(notificacionPrueba, NotificacionResponseDTO.class)).thenReturn(responseDTOPrueba);

        NotificacionResponseDTO resultado = notificationService.crear(createDTO);

        assertNotNull(resultado);
        verify(notificationRepository, times(1)).save(any(Notificacion.class));
    }

    @Test
    void crear_UsuarioNoExiste_DebeLanzarExcepcion() {

        NotificacionCreateDTO createDTO = new NotificacionCreateDTO();
        createDTO.setUsuarioId(99L);
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> notificationService.crear(createDTO));
        verify(notificationRepository, never()).save(any(Notificacion.class));
    }

    @Test
    void marcarLeida_DebeCambiarEstadoALeida() {
        MarcarLeidaDTO marcarLeidaDTO = new MarcarLeidaDTO();
        marcarLeidaDTO.setNotificacionIds(List.of(1L));

        when(notificationRepository.findAllById(marcarLeidaDTO.getNotificacionIds()))
                .thenReturn(List.of(notificacionPrueba));

        notificationService.marcarLeida(marcarLeidaDTO);

        assertTrue(notificacionPrueba.getLeida());
        assertNotNull(notificacionPrueba.getFechaLectura());
    }

    @Test
    void eliminar_IdExiste_DebeEjecutarBorrado() {

        Long id = 1L;
        when(notificationRepository.existsById(id)).thenReturn(true);


        notificationService.eliminar(id);


        verify(notificationRepository, times(1)).deleteById(id);
    }
}