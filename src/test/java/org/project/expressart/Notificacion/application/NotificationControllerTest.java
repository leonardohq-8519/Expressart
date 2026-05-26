package org.project.expressart.Notificacion.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.CuentaOAuth.domain.OAuthSuccessHandler;
import org.project.expressart.Notificacion.domain.NotificationService;
import org.project.expressart.Notificacion.domain.TipoNotificacion;
import org.project.expressart.Notificacion.dto.MarcarLeidaDTO;
import org.project.expressart.Notificacion.dto.NotificacionCountDTO;
import org.project.expressart.Notificacion.dto.NotificacionCreateDTO;
import org.project.expressart.Notificacion.dto.NotificacionResponseDTO;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NotificationController.class)
@WithMockUser
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private NotificationService notificationService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private NotificacionResponseDTO responseDTO;
    private NotificacionCreateDTO createDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new NotificacionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitulo("Nueva orden");
        responseDTO.setMensaje("Tienes una nueva orden");
        responseDTO.setLeida(false);
        responseDTO.setTipo(TipoNotificacion.values()[0]);

        createDTO = new NotificacionCreateDTO();
        createDTO.setUsuarioId(1L);
        createDTO.setTipo(TipoNotificacion.values()[0]);
        createDTO.setTitulo("Nueva orden");
        createDTO.setMensaje("Tienes una nueva orden");
    }

    @Test
    void shouldReturnNotificationsWhenGetByUsuario() throws Exception {
        when(notificationService.getByUsuario(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/notificaciones/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Nueva orden"));
    }

    @Test
    void shouldReturnUnreadNotificationsWhenGetNoLeidas() throws Exception {
        when(notificationService.getNoLeidas(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/notificaciones/usuario/1/no-leidas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].leida").value(false));
    }

    @Test
    void shouldReturnCountWhenCountNoLeidas() throws Exception {
        NotificacionCountDTO countDTO = new NotificacionCountDTO(1L, 3L);
        when(notificationService.countNoLeidas(1L)).thenReturn(countDTO);

        mockMvc.perform(get("/api/notificaciones/usuario/1/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.noLeidas").value(3));
    }

    @Test
    void shouldReturn201WhenCreateWithValidData() throws Exception {
        when(notificationService.crear(any(NotificacionCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn204WhenMarcarTodasLeidasIsSuccessful() throws Exception {
        doNothing().when(notificationService).marcarTodasLeidas(1L);

        mockMvc.perform(patch("/api/notificaciones/usuario/1/marcar-todas-leidas"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn204WhenDeleteIsSuccessful() throws Exception {
        doNothing().when(notificationService).eliminar(1L);

        mockMvc.perform(delete("/api/notificaciones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn204WhenMarcarLeidaWithValidIds() throws Exception {
        MarcarLeidaDTO marcarLeidaDTO = new MarcarLeidaDTO(List.of(1L, 2L));
        doNothing().when(notificationService).marcarLeida(any(MarcarLeidaDTO.class));

        mockMvc.perform(patch("/api/notificaciones/marcar-leida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(marcarLeidaDTO)))
                .andExpect(status().isNoContent());
    }
}
