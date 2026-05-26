package org.project.expressart.Pago.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.expressart.Pago.domain.EstadoPago;
import org.project.expressart.Pago.domain.PaymentService;
import org.project.expressart.Pago.dto.PaymentRequestDTO;
import org.project.expressart.Pago.dto.PaymentResponseDTO;
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

@WebMvcTest(controllers = PagoController.class)
@WithMockUser
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PaymentResponseDTO responseDTO;
    private PaymentRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new PaymentResponseDTO();
        responseDTO.setId(1L);

        requestDTO = new PaymentRequestDTO();
        requestDTO.setOrdenId(1L);
        requestDTO.setMonto(BigDecimal.valueOf(100.00));
        requestDTO.setMontoArtista(BigDecimal.valueOf(90.00));
        requestDTO.setMontoComisionPlataforma(BigDecimal.valueOf(10.00));
        requestDTO.setStripePaymentIntentId("pi_12345");
    }

    @Test
    void getAll_debeRetornar200() throws Exception {
        when(paymentService.findAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getById_debeRetornar200_cuandoExiste() throws Exception {
        when(paymentService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getByOrder_debeRetornar200() throws Exception {
        when(paymentService.findByOrderId(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/payments/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getByStripeIntent_debeRetornar200() throws Exception {
        when(paymentService.findByStripePaymentIntentId("pi_12345")).thenReturn(responseDTO);

        mockMvc.perform(get("/payments/stripe/pi_12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void create_debeRetornar201() throws Exception {
        when(paymentService.create(any(PaymentRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateStatus_debeRetornar200() throws Exception {
        when(paymentService.updateStatus(eq(1L), eq(EstadoPago.COMPLETADO))).thenReturn(responseDTO);

        mockMvc.perform(patch("/payments/1/status")
                        .param("estado", "COMPLETADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void delete_debeRetornar204() throws Exception {
        doNothing().when(paymentService).delete(1L);

        mockMvc.perform(delete("/payments/1"))
                .andExpect(status().isNoContent());
    }
}