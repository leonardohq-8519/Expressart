package org.project.expressart.OpcionesComision.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.project.expressart.Comision.domain.Comision;
import org.project.expressart.Comision.infrastructure.ComisionRepository;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsRequestDTO;
import org.project.expressart.OpcionesComision.dto.CommissionOptionsResponseDTO;
import org.project.expressart.OpcionesComision.infrastructure.OpcionesComisionRepository;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpcionesComisionServiceTest {

    @Mock
    private OpcionesComisionRepository commissionOptionsRepository;

    @Mock
    private ComisionRepository commissionRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommissionOptionsService commissionOptionsService;

    private OpcionesComision opcionesComision;
    private CommissionOptionsResponseDTO responseDTO;
    private CommissionOptionsRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(commissionOptionsService, "modelMapper", modelMapper);
        opcionesComision = new OpcionesComision();
        opcionesComision.setId(1L);
        opcionesComision.setNombre("Básico");

        responseDTO = new CommissionOptionsResponseDTO();
        responseDTO.setId(1L);

        requestDTO = new CommissionOptionsRequestDTO();
        requestDTO.setComisionId(2L);
        requestDTO.setNombre("Básico");
        requestDTO.setPrecio(BigDecimal.valueOf(50.00));
    }

    @Test
    void findAll_debeRetornarListaPaginada() {
        when(commissionOptionsRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<CommissionOptionsResponseDTO> resultado = commissionOptionsService.findAll();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0)).isEqualTo(responseDTO);
        verify(commissionOptionsRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void findByComisionId_debeRetornarLista_cuandoExiste() throws Exception {
        when(commissionOptionsRepository.findByComisionId(2L)).thenReturn(List.of(opcionesComision));
        when(modelMapper.map(opcionesComision, CommissionOptionsResponseDTO.class)).thenReturn(responseDTO);

        List<CommissionOptionsResponseDTO> resultado = commissionOptionsService.findByComisionId(2L);

        assertThat(resultado).isNotEmpty();
        verify(commissionOptionsRepository, times(1)).findByComisionId(2L);
    }

    @Test
    void create_debeGuardarNuevaOpcion() {
        when(commissionRepository.findById(2L)).thenReturn(Optional.of(new Comision()));
        when(commissionOptionsRepository.save(any(OpcionesComision.class))).thenReturn(opcionesComision);
        when(modelMapper.map(any(OpcionesComision.class), eq(CommissionOptionsResponseDTO.class))).thenReturn(responseDTO);

        CommissionOptionsResponseDTO resultado = commissionOptionsService.create(requestDTO);

        assertThat(resultado).isNotNull();
        verify(commissionOptionsRepository, times(1)).save(any(OpcionesComision.class));
    }
}