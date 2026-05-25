package org.project.expressart.NotificacionCorreo.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.NotificacionCorreo.domain.EmailNotificationService;
import org.project.expressart.NotificacionCorreo.domain.EstadoCorreo;
import org.project.expressart.NotificacionCorreo.domain.TipoCorreo;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoCreateDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoEstadoDTO;
import org.project.expressart.NotificacionCorreo.dtos.NotificacionCorreoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = NotificacionCorreoController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                SecurityFilterAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class
        },
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = org.project.expressart.config.JwtAuthFilter.class
        )
)
class NotificacionCorreoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmailNotificationService emailNotificationService;

    private NotificacionCorreoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new NotificacionCorreoResponseDTO(
                1L, "test@example.com", "Asunto test", TipoCorreo.NUEVA_ORDEN, EstadoCorreo.PENDIENTE, 0);
    }

    @Test
    @DisplayName("Debería retornar 200 con notificaciones por usuario")
    void shouldReturn200WithNotificacionesByUser() throws Exception {
        when(emailNotificationService.getByUser(1L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/email-notifications/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }

    @Test
    @DisplayName("Debería retornar 200 con notificaciones fallidas")
    void shouldReturn200WithFailedNotificaciones() throws Exception {
        NotificacionCorreoResponseDTO failedDTO = new NotificacionCorreoResponseDTO(
                2L, "fail@example.com", "Asunto fallido", TipoCorreo.PAGO_CONFIRMADO, EstadoCorreo.FALLIDO, 3);
        when(emailNotificationService.getFailed()).thenReturn(List.of(failedDTO));

        mockMvc.perform(get("/api/email-notifications/failed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("FALLIDO"));
    }

    @Test
    @DisplayName("Debería retornar 200 con notificaciones pendientes")
    void shouldReturn200WithPendingNotificaciones() throws Exception {
        when(emailNotificationService.getPending()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/email-notifications/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }

    @Test
    @DisplayName("Debería retornar 201 al crear una notificación correo")
    void shouldReturn201WhenNotificacionCorreoCreated() throws Exception {
        NotificacionCorreoCreateDTO request = new NotificacionCorreoCreateDTO(
                1L, "dest@example.com", "Asunto", "Cuerpo", TipoCorreo.NUEVA_ORDEN);

        when(emailNotificationService.create(any(NotificacionCorreoCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/email-notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 204 al actualizar el estado de una notificación")
    void shouldReturn204WhenStatusUpdated() throws Exception {
        NotificacionCorreoEstadoDTO estadoDTO = new NotificacionCorreoEstadoDTO(1L, EstadoCorreo.ENVIADO, null, null);
        doNothing().when(emailNotificationService).updateStatus(eq(1L), any(NotificacionCorreoEstadoDTO.class));

        mockMvc.perform(patch("/api/email-notifications/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estadoDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería retornar 204 al reintentar una notificación fallida")
    void shouldReturn204WhenNotificacionRetried() throws Exception {
        doNothing().when(emailNotificationService).retry(1L);

        mockMvc.perform(patch("/api/email-notifications/1/retry"))
                .andExpect(status().isNoContent());
    }
}