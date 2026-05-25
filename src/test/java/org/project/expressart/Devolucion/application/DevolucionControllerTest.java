package org.project.expressart.Devolucion.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.expressart.Devolucion.domain.DevolutionService;
import org.project.expressart.Devolucion.domain.EstadoDevolucion;
import org.project.expressart.Devolucion.dto.DevolutionRequestDTO;
import org.project.expressart.Devolucion.dto.DevolutionResponseDTO;
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

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = DevolucionController.class,
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
class DevolucionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DevolutionService devolutionService;

    private DevolutionResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new DevolutionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setOrdenId(1L);
        responseDTO.setEstado(EstadoDevolucion.SOLICITADA);
        responseDTO.setMotivo("Producto defectuoso");
        responseDTO.setMontoReembolso(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Debería retornar 200 con lista de devoluciones al obtener todas")
    void shouldReturn200WithDevolutionListWhenGetAll() throws Exception {
        when(devolutionService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/refunds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].estado").value("SOLICITADA"));
    }

    @Test
    @DisplayName("Debería retornar 200 con la devolución cuando existe por ID")
    void shouldReturn200WhenDevolutionFoundById() throws Exception {
        when(devolutionService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/refunds/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 200 con la devolución cuando existe por orden")
    void shouldReturn200WhenDevolutionFoundByOrderId() throws Exception {
        when(devolutionService.findByOrderId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/refunds/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 200 con devoluciones por estado")
    void shouldReturn200WhenDevolutionsFoundByEstado() throws Exception {
        when(devolutionService.findByEstado(EstadoDevolucion.SOLICITADA)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/refunds/status/SOLICITADA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("SOLICITADA"));
    }

    @Test
    @DisplayName("Debería retornar 201 al crear una devolución válida")
    void shouldReturn201WhenDevolutionCreatedSuccessfully() throws Exception {
        DevolutionRequestDTO request = new DevolutionRequestDTO();
        request.setOrdenId(1L);
        request.setMotivo("Producto defectuoso");
        request.setMontoReembolso(new BigDecimal("100.00"));

        when(devolutionService.create(any(DevolutionRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/refunds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Debería retornar 200 al actualizar el estado de la devolución")
    void shouldReturn200WhenDevolutionStatusUpdated() throws Exception {
        when(devolutionService.updateStatus(1L, EstadoDevolucion.APROBADA)).thenReturn(responseDTO);

        mockMvc.perform(patch("/refunds/1/status")
                        .param("estado", "APROBADA"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debería retornar 204 al eliminar una devolución existente")
    void shouldReturn204WhenDevolutionDeletedSuccessfully() throws Exception {
        doNothing().when(devolutionService).delete(1L);

        mockMvc.perform(delete("/refunds/1"))
                .andExpect(status().isNoContent());
    }
}