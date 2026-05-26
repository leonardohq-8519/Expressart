package org.project.expressart.Pago.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.project.expressart.Orden.domain.Orden;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;
import org.project.expressart.Pago.dto.PaymentRequestDTO;
import org.project.expressart.Pago.dto.PaymentResponseDTO;
import org.project.expressart.Pago.infrastructure.PagoRepository;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PagoServiceTest {

    @Mock
    private PagoRepository paymentRepository;

    @Mock
    private OrdenRepository orderRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PaymentService paymentService;

    private Pago pago;
    private PaymentResponseDTO responseDTO;
    private PaymentRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(paymentService, "modelMapper", modelMapper);
        pago = new Pago();
        pago.setId(1L);
        pago.setEstado(EstadoPago.PENDIENTE);

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
    void findAll_debeRetornarListaPaginada() {
        when(paymentRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<PaymentResponseDTO> resultado = paymentService.findAll();

        assertThat(resultado).isNotEmpty();
        verify(paymentRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void findById_debeRetornarPago_cuandoExiste() throws Exception {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(modelMapper.map(pago, PaymentResponseDTO.class)).thenReturn(responseDTO);

        PaymentResponseDTO resultado = paymentService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void create_debeGuardarPagoExitosamente() {
        Usuario cliente = new Usuario();
        cliente.setId(10L);
        cliente.setEmail("cliente@test.com");

        Orden orden = new Orden();
        orden.setId(1L);
        orden.setCliente(cliente);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orden));
        when(paymentRepository.save(any(Pago.class))).thenReturn(pago);
        when(modelMapper.map(any(Pago.class), eq(PaymentResponseDTO.class))).thenReturn(responseDTO);
        doNothing().when(eventPublisher).publishEvent(any());

        PaymentResponseDTO resultado = paymentService.create(requestDTO);

        assertThat(resultado).isNotNull();
        verify(paymentRepository, times(1)).save(any(Pago.class));
    }

    @Test
    void updateStatus_debeCambiarEstadoYGuardar() throws Exception {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(paymentRepository.save(any(Pago.class))).thenReturn(pago);
        when(modelMapper.map(pago, PaymentResponseDTO.class)).thenReturn(responseDTO);

        PaymentResponseDTO resultado = paymentService.updateStatus(1L, EstadoPago.COMPLETADO);

        assertThat(resultado).isNotNull();
        verify(paymentRepository, times(1)).save(pago);
    }
}