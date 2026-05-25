package org.project.expressart.Notificacion.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Notificacion.application.dto.NotificacionResponseDTO;
import org.project.expressart.Notificacion.domain.NotificationService; // Asegura esta importación limpia
import org.project.expressart.Notificacion.infrastructure.NotificationController;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;

class NotificationControllerTest {

    private MockMvc mockMvc;
    private NotificationService notificationService; // Tipo de servicio real
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // CORREGIDO: Se mockea el servicio de dominio real, no la clase de pruebas
        notificationService = mock(NotificationService.class);
        NotificationController controller = new NotificationController(notificationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    // Tus métodos @Test continúan aquí abajo...
}