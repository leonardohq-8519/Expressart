package org.project.expressart.Notificacion.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Notificacion.application.dto.MarcarLeidaDTO;
import org.project.expressart.Notificacion.application.dto.NotificacionResponseDTO;
import org.project.expressart.Notificacion.domain.NotificationService;
import org.project.expressart.Notificacion.infrastructure.NotificationController;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest {

    private MockMvc mockMvc;
    private NotificationService notificationService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        notificationService = mock(NotificationService.class);
        NotificationController controller = new NotificationController(notificationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturnStatus200AndList_WhenGetByUsuarioIsCalled() throws Exception {
        Long usuarioId = 1L;
        NotificacionResponseDTO dto = new NotificacionResponseDTO();

        when(notificationService.getByUsuario(usuarioId)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/notificaciones/usuario/{usuarioId}", usuarioId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).getByUsuario(usuarioId);
    }

    @Test
    void shouldReturnStatus200_WhenGetNoLeidasIsCalled() throws Exception {
        Long usuarioId = 1L;
        when(notificationService.getNoLeidas(usuarioId)).thenReturn(List.of());

        mockMvc.perform(get("/api/notificaciones/usuario/{usuarioId}/no-leidas", usuarioId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(notificationService, times(1)).getNoLeidas(usuarioId);
    }

    @Test
    void shouldReturnStatus204_WhenMarcarLeidaIsCalled() throws Exception {
        MarcarLeidaDTO marcarLeidaDTO = new MarcarLeidaDTO();
        marcarLeidaDTO.setNotificacionIds(List.of(1L, 2L));

        doNothing().when(notificationService).marcarLeida(any(MarcarLeidaDTO.class));

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch("/api/notificaciones/marcar-leida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(marcarLeidaDTO)))
                .andExpect(status().isNoContent());

        verify(notificationService, times(1)).marcarLeida(any(MarcarLeidaDTO.class));
    }
}