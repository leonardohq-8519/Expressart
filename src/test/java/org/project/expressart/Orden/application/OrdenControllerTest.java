package org.project.expressart.Orden.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Orden.domain.EstadoOrden;
import org.project.expressart.Orden.domain.OrderService;
import org.project.expressart.Orden.dto.OrderRequestDTO;
import org.project.expressart.Orden.dto.OrderResponseDTO;
import org.project.expressart.CuentaOAuth.domain.OAuthSuccessHandler;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.project.expressart.config.JwtService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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

@WebMvcTest(controllers = OrdenController.class)
@WithMockUser
class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
    void getAll_debeRetornar200() throws Exception {
        when(orderService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getById_debeRetornar200_cuandoExiste() throws Exception {
        when(orderService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getByCliente_debeRetornar200() throws Exception {
        when(orderService.findByClienteId(3L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/orders/client/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getByArtista_debeRetornar200() throws Exception {
        when(orderService.findByArtistaId(2L)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/orders/artist/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getByClienteAndEstado_debeRetornar200() throws Exception {
        when(orderService.findByClienteIdAndEstado(3L, EstadoOrden.PENDIENTE)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/orders/client/3/status/PENDIENTE"))
                .andExpect(status().isOk());
    }

    @Test
    void getByArtistaAndEstado_debeRetornar200() throws Exception {
        when(orderService.findByArtistaIdAndEstado(2L, EstadoOrden.PENDIENTE)).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/orders/artist/2/status/PENDIENTE"))
                .andExpect(status().isOk());
    }

    @Test
    void create_debeRetornar201() throws Exception {
        when(orderService.create(any(OrderRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update_debeRetornar200() throws Exception {
        when(orderService.update(eq(1L), any(OrderRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void updateEstado_debeRetornar200() throws Exception {
        when(orderService.updateEstado(eq(1L), eq(EstadoOrden.EN_PROGRESO))).thenReturn(responseDTO);

        mockMvc.perform(patch("/orders/1/status")
                        .param("estado", "EN_PROGRESO"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_debeRetornar204() throws Exception {
        doNothing().when(orderService).delete(1L);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }
}