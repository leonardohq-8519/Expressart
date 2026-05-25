package org.project.expressart.Notificacion.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Notificacion.dto.NotificacionResponseDTO;
import org.project.expressart.Notificacion.domain.NotificationService;
import org.project.expressart.Notificacion.application.NotificationController;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;

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

    // Tus métodos @Test continúan aquí abajo...
}