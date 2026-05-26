package org.project.expressart.Orden.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.project.expressart.OpcionesComision.domain.OpcionesComision;
import org.project.expressart.OpcionesComision.infrastructure.OpcionesComisionRepository;
import org.project.expressart.Orden.dto.OrderRequestDTO;
import org.project.expressart.Orden.dto.OrderResponseDTO;
import org.project.expressart.Orden.infrastructure.OrdenRepository;
import org.project.expressart.Usuario.domain.Usuario;
import org.project.expressart.Usuario.infrastructure.UsuarioRepository;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdenServiceTest {

    @Mock
    private OrdenRepository orderRepository;

    @Mock
    private UsuarioRepository userRepository;

    @Mock
    private OpcionesComisionRepository commissionOptionsRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderService orderService;

    private Orden orden;
    private OrderResponseDTO responseDTO;
    private OrderRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(orderService, "modelMapper", modelMapper);
        orden = new Orden();
        orden.setId(1L);
        orden.setEstado(EstadoOrden.PENDIENTE);

        responseDTO = new OrderResponseDTO();
        responseDTO.setId(1L);

        requestDTO = new OrderRequestDTO();
        requestDTO.setArtistaId(2L);
        requestDTO.setClienteId(3L);
        requestDTO.setOpcionComisionId(1L);
        requestDTO.setPrecioFinal(BigDecimal.valueOf(150.00));
        requestDTO.setDescripcionTrabajo("Ilustración");
    }

    @Test
    void findAll_debeRetornarListaPaginada() {
        when(orderRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<OrderResponseDTO> resultado = orderService.findAll();

        assertThat(resultado).isNotEmpty();
        verify(orderRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void findById_debeRetornarOrden_cuandoExiste() throws Exception {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orden));
        when(modelMapper.map(orden, OrderResponseDTO.class)).thenReturn(responseDTO);

        OrderResponseDTO resultado = orderService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void create_debeGuardarOrdenExitosamente() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(new Usuario()));
        when(userRepository.findById(3L)).thenReturn(Optional.of(new Usuario()));
        when(commissionOptionsRepository.findById(1L)).thenReturn(Optional.of(new OpcionesComision()));
        when(orderRepository.save(any(Orden.class))).thenReturn(orden);
        when(modelMapper.map(any(Orden.class), eq(OrderResponseDTO.class))).thenReturn(responseDTO);

        OrderResponseDTO resultado = orderService.create(requestDTO);

        assertThat(resultado).isNotNull();
        verify(orderRepository, times(1)).save(any(Orden.class));
    }
}