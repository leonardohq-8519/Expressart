package org.project.expressart.Orden.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Orden.domain.EstadoOrden;
import org.project.expressart.Orden.domain.OrderService;
import org.project.expressart.Orden.dto.OrderRequestDTO;
import org.project.expressart.Orden.dto.OrderResponseDTO;
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
        controllers = OrdenController.class,
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
class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderResponseDTO responseDTO;
    private OrderRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new OrderResponseDTO();
        responseDTO.setId(1L);

        requestDTO = new OrderRequestDTO();
        requestDTO.setArtistaId(2L);
        requestDTO.setClienteId(3L);
        requestDTO.setOpcionComisionId(1L);
        requestDTO.setPrecioFinal(BigDecimal.valueOf(150.00));
        requestDTO.setDescripcionTrabajo("Ilustración personalizada");
    }

    @Test
    void shouldReturn200_WhenGetAllIsCalled() throws Exception {
        when(orderService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturn200_WhenGetByIdFindsExistingOrder() throws Exception {
        when(orderService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn200_WhenGetByClienteIsCalled() throws Exception {
        when(orderService.findByClienteId(3L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/orders/client/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturn200_WhenGetByArtistaIsCalled() throws Exception {
        when(orderService.findByArtistaId(2L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/orders/artist/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void shouldReturn200_WhenGetByClienteAndEstadoIsCalled() throws Exception {
        when(orderService.findByClienteIdAndEstado(3L, EstadoOrden.PENDIENTE)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/orders/client/3/status/PENDIENTE"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200_WhenGetByArtistaAndEstadoIsCalled() throws Exception {
        when(orderService.findByArtistaIdAndEstado(2L, EstadoOrden.PENDIENTE)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/orders/artist/2/status/PENDIENTE"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn201_WhenOrderIsCreatedSuccessfully() throws Exception {
        when(orderService.create(any(OrderRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn200_WhenOrderIsUpdatedSuccessfully() throws Exception {
        when(orderService.update(eq(1L), any(OrderRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200_WhenOrderStateIsUpdatedSuccessfully() throws Exception {
        when(orderService.updateEstado(eq(1L), eq(EstadoOrden.EN_PROGRESO))).thenReturn(responseDTO);

        mockMvc.perform(patch("/orders/1/status")
                        .param("estado", "EN_PROGRESO"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn204_WhenOrderIsDeletedSuccessfully() throws Exception {
        doNothing().when(orderService).delete(1L);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }
}