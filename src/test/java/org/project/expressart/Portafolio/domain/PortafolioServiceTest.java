package org.project.expressart.Portafolio.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.project.expressart.Portafolio.dto.PortafolioRequestDTO;
import org.project.expressart.Portafolio.dto.PortafolioResponseDTO;
import org.project.expressart.Portafolio.infrastructure.PortafolioRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortafolioServiceTest {

    @Mock
    private PortafolioRepository portafolioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PortafolioService portafolioService;

    private Portafolio portafolio;
    private PortafolioResponseDTO responseDTO;
    private PortafolioRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        portafolio = new Portafolio();
        portafolio.setId(1L);
        portafolio.setTitulo("Portafolio Inicial");

        responseDTO = new PortafolioResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitulo("Portafolio Inicial");

        requestDTO = new PortafolioRequestDTO();
        requestDTO.setTitulo("Portafolio Inicial");
        requestDTO.setDescripcion("Descripción test");
    }

    @Test
    void findAll_debeRetornarListaPaginada() {
        when(portafolioRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(responseDTO));

        List<PortafolioResponseDTO> resultado = portafolioService.findAll();

        assertThat(resultado).isNotEmpty();
        verify(portafolioRepository, times(1)).findAllBy(any(Pageable.class));
    }

    @Test
    void findById_debeRetornarPortafolio_cuandoExiste() throws Exception {
        when(portafolioRepository.findById(1L)).thenReturn(Optional.of(portafolio));
        when(modelMapper.map(portafolio, PortafolioResponseDTO.class)).thenReturn(responseDTO);

        PortafolioResponseDTO resultado = portafolioService.findById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void create_debeGuardarCorrectamente() {
        when(portafolioRepository.save(any(Portafolio.class))).thenReturn(portafolio);
        when(modelMapper.map(any(Portafolio.class), eq(PortafolioResponseDTO.class))).thenReturn(responseDTO);

        PortafolioResponseDTO resultado = portafolioService.create(requestDTO);

        assertThat(resultado).isNotNull();
        verify(portafolioRepository, times(1)).save(any(Portafolio.class));
    }
}